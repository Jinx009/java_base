package main.entry.webapp.data.project.front;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProOrder;
import service.basicFunctions.project.ProOrderService;
import utils.Resp;

@RequestMapping(value = "/f/weapp")
@Controller
public class FrontWeappController {

	private static final Logger log = LoggerFactory.getLogger(FrontWeappController.class);
	
	@Autowired
	private ProOrderService proOrderService;
	

	@RequestMapping(path = "/notice")
	@ResponseBody
	public Resp<?> notice(HttpServletRequest request, HttpServletResponse response) {
		Resp<?> resp = new Resp<>(false);
		String inputLine = "";
		String notityXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
			String out_trade_no = doXMLParse(notityXml).get("out_trade_no");
			String result_code = doXMLParse(notityXml).get("result_code");
			String return_code = doXMLParse(notityXml).get("return_code");
			if(return_code.equals("SUCCESS")&&result_code.equals("SUCCESS")) {
				ProOrder proOrder = proOrderService.getByMsg(out_trade_no);
				if(proOrder!=null&&proOrder.getStatus()!=1) {
					proOrder.setStatus(1);
					proOrderService.update(proOrder);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.warn("异步回调XML信息：{}", notityXml);
		return resp;
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
	
	public static void main(String[] args) {
		String xml = "<xml><appid><![CDATA[wxbc5ec9a82883abb6]]></appid><bank_type><![CDATA[OTHERS]]></bank_type><cash_fee><![CDATA[130000]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[N]]></is_subscribe><mch_id><![CDATA[1595301021]]></mch_id><nonce_str><![CDATA[3JIYuyn2lgKSnaNwpxggn3FYDr0asV4i]]></nonce_str><openid><![CDATA[ocz85475fRvB8eNhC6MC2vL0ml4I]]></openid><out_trade_no><![CDATA[1601300052093]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[AF9CC9A8F902979CE2058DE522A119AA]]></sign><time_end><![CDATA[20200928213418]]></time_end><total_fee>130000</total_fee><trade_type><![CDATA[JSAPI]]></trade_type><transaction_id><![CDATA[4200000695202009287165914792]]></transaction_id></xml>";
	
		try {
			System.out.println(doXMLParse(xml).get("out_trade_no"));
			System.out.println(doXMLParse(xml).get("result_code"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
