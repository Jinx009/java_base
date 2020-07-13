package main.entry.webapp.data.project.front;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.helper.MD5Util;
import database.models.project.ProGoods;
import database.models.project.ProOrder;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGoodsService;
import service.basicFunctions.project.ProOrderService;
import utils.HttpUtils;
import utils.RandomUtils;
import utils.Resp;

@RequestMapping(value = "/f/pro_order")
@Controller
public class FrontProOrderDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(FrontProOrderDataController.class);

	@Autowired
	private ProOrderService proOrderService;
	@Autowired
	private ProGoodsService ProGoodsService;

	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(String openid, Integer p) {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proOrderService.getByOpenid(openid, p));
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/m")
	@ResponseBody
	public Resp<?> m(String date) {
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(proOrderService.getByM(date));
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/update")
	@ResponseBody
	public Resp<?> list(Integer orderId,Integer status,String msg ) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("orderId:{},status:{}",orderId,status);
			ProOrder proOrder = proOrderService.findById(orderId);
			proOrder.setStatus(status);
			proOrder.setMsg(msg);
			proOrder.setStatus(status);
			proOrderService.update(proOrder);
			ProGoods good = ProGoodsService.findByTimeDateAbc(proOrder.getTime(), proOrder.getType(), proOrder.getDate());
			if(status==1) {
				good.setType(1);
				ProGoodsService.update(good);
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/goPay")
	@ResponseBody
	public Resp<?> goPay(Integer orderId) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("orderId:{}",orderId);
			ProOrder proOrder = proOrderService.findById(orderId);
			String random = RandomUtils.getRandomNumbersAndString(32);
			String params = "appid=wxbc5ec9a82883abb6&body=" + proOrder.getDate() + " " + proOrder.getTime() + " "
					+ proOrder.getName() + "&mch_id=1595301021&" + "nonce_str=" + random
					+ "&notify_url=https://football.amize.cn/f/weapp/notice&openid=" + proOrder.getOpenid()
					+ "&out_trade_no=" + proOrder.getCreateTime().getTime()
					+ "&spbill_create_ip=111.231.107.149&total_fee="+proOrder.getPrice().intValue()*100+"&trade_type=JSAPI";
			String stringSign = params + "&key=fX5FEHQjFAmSUe01kke3xogAPKl5GaD8";
			String sign = MD5Util.MD5(stringSign);

			String xml = "<xml>" + "<appid>wxbc5ec9a82883abb6</appid>" + "<body>" + proOrder.getDate() + " "
					+ proOrder.getTime() + " " + proOrder.getName() + "</body>" + "<mch_id>1595301021</mch_id>"
					+ "<nonce_str>" + random + "</nonce_str>"
					+ "<notify_url>https://football.amize.cn/f/weapp/notice</notify_url>" + "<openid>"
					+ proOrder.getOpenid() + "</openid>" + "<out_trade_no>" + proOrder.getCreateTime().getTime()
					+ "</out_trade_no>" + "<spbill_create_ip>111.231.107.149</spbill_create_ip>"
					+ "<total_fee>"+proOrder.getPrice().intValue()*100+"</total_fee><trade_type>JSAPI</trade_type><sign>" + sign + "</sign>"
					+ "</xml>";
			String res = HttpUtils.postXml("https://api.mch.weixin.qq.com/pay/unifiedorder", xml);
			String prepay_id = doXMLParse(res).get("prepay_id");
			proOrder.setPrepayId(prepay_id);
			proOrderService.update(proOrder);
			String randomStr = RandomUtils.getRandomNumbersAndString(32);
			Date date = new Date();
			String paySign = MD5Util.MD5("appId=wxbc5ec9a82883abb6&nonceStr="+randomStr+"&package=prepay_id="+prepay_id+"&signType=MD5&timeStamp="+date.getTime()/1000+"&key=fX5FEHQjFAmSUe01kke3xogAPKl5GaD8");
			proOrder.setPrepayId(prepay_id);
			proOrderService.update(proOrder);
			Map<String, Object> map = new HashMap<>();
			map.put("prepay_id",prepay_id );
			map.put("appid", "wxbc5ec9a82883abb6");
			map.put("nonceStr", randomStr);
			map.put("signType", "MD5");
			map.put("package", "prepay_id="+prepay_id);
			map.put("timeStamp", String.valueOf( date.getTime()/1000));
			map.put("paySign", paySign);
			map.put("orderId", proOrder.getId());
			return new Resp<>(map);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	// https://api.mch.weixin.qq.com/pay/unifiedorder
	@RequestMapping(path = "/save")
	@ResponseBody
	public Resp<?> list(Integer id, String openid, String mobilePhone, String userName) {
		Resp<?> resp = new Resp<>(false);
		try {
			log.warn("goodsId:{},openId:{},mobilePhone:{}",id,openid,mobilePhone);
			ProGoods proGoods = ProGoodsService.findById(id);
			ProOrder proOrder = new ProOrder();
			proOrder.setCreateTime(new Date());
			proOrder.setDate(proGoods.getDate());
			proOrder.setMobilePhone(mobilePhone);
			proOrder.setName(proGoods.getName());
			proOrder.setOpenid(openid);
			proOrder.setUserName(new String(userName .getBytes("iso8859-1"),"utf-8"));
			proOrder.setPrice(proGoods.getPrice());
			proOrder.setStatus(0);
			proOrder.setType(proGoods.getAbc());
			proOrder.setTime(proGoods.getTime());
			proOrder.setFromSite(1);
			proOrder = proOrderService.saveOrder(proOrder);
			proGoods.setType(1);
			ProGoodsService.update(proGoods);
			String random = RandomUtils.getRandomNumbersAndString(32);
			String params = "appid=wxbc5ec9a82883abb6&body=" + proOrder.getDate() + " " + proOrder.getTime() + " "
					+ proOrder.getName() + "&mch_id=1595301021&" + "nonce_str=" + random
					+ "&notify_url=https://football.amize.cn/f/weapp/notice&openid=" + proOrder.getOpenid()
					+ "&out_trade_no=" + proOrder.getCreateTime().getTime()
					+ "&spbill_create_ip=111.231.107.149&total_fee="+proOrder.getPrice().intValue()*100+"&trade_type=JSAPI";
			String stringSign = params + "&key=fX5FEHQjFAmSUe01kke3xogAPKl5GaD8";
			String sign = MD5Util.MD5(stringSign);

			String xml = "<xml>" + "<appid>wxbc5ec9a82883abb6</appid>" + "<body>" + proOrder.getDate() + " "
					+ proOrder.getTime() + " " + proOrder.getName() + "</body>" + "<mch_id>1595301021</mch_id>"
					+ "<nonce_str>" + random + "</nonce_str>"
					+ "<notify_url>https://football.amize.cn/f/weapp/notice</notify_url>" + "<openid>"
					+ proOrder.getOpenid() + "</openid>" + "<out_trade_no>" + proOrder.getCreateTime().getTime()
					+ "</out_trade_no>" + "<spbill_create_ip>111.231.107.149</spbill_create_ip>"
					+ "<total_fee>"+proOrder.getPrice().intValue()*100+"</total_fee><trade_type>JSAPI</trade_type><sign>" + sign + "</sign>"
					+ "</xml>";
			String res = HttpUtils.postXml("https://api.mch.weixin.qq.com/pay/unifiedorder", xml);
			String prepay_id = doXMLParse(res).get("prepay_id");
			String randomStr = RandomUtils.getRandomNumbersAndString(32);
			Date date = new Date();
			String paySign = MD5Util.MD5("appId=wxbc5ec9a82883abb6&nonceStr="+randomStr+"&package=prepay_id="+prepay_id+"&signType=MD5&timeStamp="+date.getTime()/1000+"&key=fX5FEHQjFAmSUe01kke3xogAPKl5GaD8");
			proOrder.setPrepayId(prepay_id);
			proOrderService.update(proOrder);
			Map<String, Object> map = new HashMap<>();
			map.put("prepay_id",prepay_id );
			map.put("appid", "wxbc5ec9a82883abb6");
			map.put("nonceStr", randomStr);
			map.put("signType", "MD5");
			map.put("package", "prepay_id="+prepay_id);
			map.put("timeStamp",String.valueOf( date.getTime()/1000));
			map.put("paySign", paySign);
			map.put("orderId", proOrder.getId());
			return new Resp<>(map);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	public static void main(String[] args) {
		ProOrder proOrder = new ProOrder();
		proOrder.setDate("2020-05-14");
		proOrder.setTime("8:30~10:30");
		proOrder.setName("8人制1场");
		proOrder.setCreateTime(new Date());
		proOrder.setOpenid("ocz85402eyEQWsb_nzoziqT56Z04");
		String random = RandomUtils.getRandomNumbersAndString(32);
		String params = "appid=wxbc5ec9a82883abb6&body=" + proOrder.getDate() + " " + proOrder.getTime() + " "
				+ proOrder.getName() + "&mch_id=1595301021&" + "nonce_str=" + random
				+ "&notify_url=https://football.amize.cn/f/weapp/notice&openid=" + proOrder.getOpenid()
				+ "&out_trade_no=" + proOrder.getCreateTime().getTime()
				+ "&spbill_create_ip=111.231.107.149&total_fee=1&trade_type=JSAPI";
		String stringSign = params + "&key=fX5FEHQjFAmSUe01kke3xogAPKl5GaD8";
		String sign = MD5Util.MD5(stringSign);

		String xml = "<xml>" + "<appid>wxbc5ec9a82883abb6</appid>" + "<body>" + proOrder.getDate() + " "
				+ proOrder.getTime() + " " + proOrder.getName() + "</body>" + "<mch_id>1595301021</mch_id>"
				+ "<nonce_str>" + random + "</nonce_str>"
				+ "<notify_url>https://football.amize.cn/f/weapp/notice</notify_url>" + "<openid>"
				+ proOrder.getOpenid() + "</openid>" + "<out_trade_no>" + proOrder.getCreateTime().getTime()
				+ "</out_trade_no>" + "<spbill_create_ip>111.231.107.149</spbill_create_ip>"
				+ "<total_fee>1</total_fee>" + "<trade_type>JSAPI</trade_type>" + "<sign>" + sign + "</sign>"
				+ "</xml>";
		String res = HttpUtils.postXml("https://api.mch.weixin.qq.com/pay/unifiedorder", xml);

		try {
			System.out.println(doXMLParse(res).get("prepay_id"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("rawtypes")
	public static Map<String, String> doXMLParse(String strxml) throws Exception {
		strxml = strxml.replaceFirst("encoding=\".*\"", "encoding=\"UTF-8\"");
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		Map<String, String> m = new HashMap<String, String>();
		InputStream in = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(in);
		Element root = doc.getRootElement();
		List list = root.getChildren();
		Iterator it = list.iterator();
		while (it.hasNext()) {
			Element e = (Element) it.next();
			String k = e.getName();
			String v = "";
			List children = e.getChildren();
			if (children.isEmpty()) {
				v = e.getTextNormalize();
			} else {
				v = getChildrenText(children);
			}
			m.put(k, v);
		}
		in.close();
		return m;
	}

	@SuppressWarnings("rawtypes")
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();

	}

}
