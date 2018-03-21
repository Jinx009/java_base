package main.entry.webapp.data.gateway;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;
import database.models.project.ProGatewayArea;
import database.models.project.ProGatewayLocation;
import database.models.project.ProOrder;
import database.models.project.model.ProGetPayModel;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGatewayAreaService;
import service.basicFunctions.project.ProGatewayLocationService;
import utils.BaseConstant;
import utils.HttpUtils;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/rest/order")
public class GatewayOrderDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GatewayOrderDataController.class);
	
	@Autowired
	private ProGatewayAreaService proGatewayAreaService;
	@Autowired
	private ProGatewayLocationService proGatewayLocationService;
	
	@RequestMapping(path = "/getOrderByProductId")
	@ResponseBody
	public Resp<?> getOrderByProductId(@RequestBody Map<String, Object> data){
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getString(data, BaseConstant.TOKEN);
			String productId = getString(data, "productId");
			log.warn("token:{},productId:{}",token,productId);
			if(StringUtil.isBlank(token)||StringUtil.isBlank(productId)){
				resp.setMsg(RespData.PARAMS_ERROR);
				return resp;
			}
			String url = "http://120.92.101.137:8083/product?&productId="+productId;
			List<ProOrder> orders = JSONArray.parseArray(JSONObject.parseObject(HttpUtils.getMofang(getMofangSessionId(), url)).getJSONObject(BaseConstant.DATA).getString("products"),ProOrder.class);
			if(orders  ==null||orders.isEmpty()){
				resp.setMsg(RespData.ORDER_NOT_EXITS);
				return resp;
			}
			ProOrder proOrder = orders.get(0);
			return new Resp<>(proOrder);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		
		return resp;
	}
	
	
	@RequestMapping(path = "/getLastOrder")
	@ResponseBody
	public Resp<?> getLastOrder(@RequestBody Map<String, Object> data){
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getString(data, BaseConstant.TOKEN);
			String plateNumber = getString(data, "plateNumber");
			String parkNumber = getString(data, "parkNumber");
			Integer areaId = getInt(data, BaseConstant.AREA_ID_NAME);
			log.warn("token:{},parkNo:{},areaId:{}",token,plateNumber,areaId);
			if(StringUtil.isBlank(token)||areaId==null){
				resp.setMsg(RespData.PARAMS_ERROR);
				return resp;
			}
			ProGatewayArea proGatewayArea = proGatewayAreaService.getByAreaId(areaId);
			if(proGatewayArea==null){
				resp.setMsg(RespData.AREA_ERROR);
				return resp;
			}
			ProGatewayLocation proGatewayLocation = proGatewayLocationService.getByLocationId(proGatewayArea.getLocationId());
			if(!checkToken(token,getToken(proGatewayLocation.getAppId()))){
				resp.setMsg(RespData.TOKEN_CHECK_ERROR);
				return resp;
			}
			String url = "http://120.92.101.137:8083/product?&storeOrganId="+proGatewayArea.getStoreId();
			if(StringUtil.isNotBlank(parkNumber)){
				String params = "http://120.92.101.137:8083/park_place?&storeOrganId="+proGatewayArea.getStoreId()+"&code="+parkNumber;
				String parkPlaceId = JSONObject.parseObject(HttpUtils.getMofang(getMofangSessionId(),params)).getJSONObject("data").getJSONArray("parkPlaces").getJSONObject(0).getString("parkPlaceId");
				url+= "&parkPlaceId="+parkPlaceId;
			}
			if(StringUtil.isNotBlank(plateNumber)){
				url+= "&plateNumber="+plateNumber;
			}
			List<ProOrder> orders = JSONArray.parseArray(JSONObject.parseObject(HttpUtils.getMofang(getMofangSessionId(), url)).getJSONObject(BaseConstant.DATA).getString("products"),ProOrder.class);
			if(orders  ==null||orders.isEmpty()){
				resp.setMsg(RespData.ORDER_NOT_EXITS);
				return resp;
			}
			ProOrder proOrder = orders.get(0);
			if(!proOrder.getStatus().equals("IN_PARK")){
				resp.setMsg(RespData.NOT_IN_PARK);
				return resp;
			}
			return new Resp<>(proOrder);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		
		return resp;
	}
	
	
	@RequestMapping(path = "/getOrderByPlateNumber")
	@ResponseBody
	public Resp<?> getOrder(@RequestBody Map<String, Object> data){
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getString(data, BaseConstant.TOKEN);
			String plateNumber = getString(data, "plateNumber");
			String pageNum = getString(data, "pageNum");
			String status = getString(data, "status");
			log.warn("token:{},plateNumber:{},pageNum:{},status:{}",token,plateNumber,pageNum,status);
			if(StringUtil.isBlank(token)||StringUtil.isBlank(plateNumber)){
				resp.setMsg(RespData.PARAMS_ERROR);
				return resp;
			}
			String url = "http://120.92.101.137:8083/product?plateNumber="+plateNumber+"&pageNum="+pageNum;
			if(StringUtil.isNotBlank(status)){
				url+= "&status="+status;
			}
			JSONObject jsonObject = JSONObject.parseObject(HttpUtils.getMofang(getMofangSessionId(), url)).getJSONObject(BaseConstant.DATA);
			return new Resp<>(jsonObject);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		
		return resp;
	}
	
	@RequestMapping(path = "/getPayInfo")
	@ResponseBody
	public Resp<?> getPay(@RequestBody Map<String, Object> data){
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getString(data, BaseConstant.TOKEN);
			String productId = getString(data, "productId");
			String companyId = getString(data, "companyId");
			String storeId = getString(data, "storeId");
			String parkPlaceId = getString(data, "parkPlaceId");
			log.warn("token:{},orderId:{},appId:{}",token,productId);
			if(StringUtil.isBlank(token)||StringUtil.isBlank(productId)){
				resp.setMsg(RespData.PARAMS_ERROR);
				return resp;
			}
			String params = "http://120.92.101.137:8083/park_place?companyOrganId="+companyId+"&storeOrganId="+storeId+"&parkPlaceId="+parkPlaceId;
			String mac = JSONObject.parseObject(HttpUtils.getMofang(getMofangSessionId(),params)).getJSONObject("data").getJSONArray("parkPlaces").getJSONObject(0).getString("magneticStripeId");
			Double money = sendNotice(mac, companyId, storeId,productId);
			String url = "http://120.92.101.137:8081/order";
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("amount", "1");
			map.put("appId", "PARK");
			map.put("storeOrganId", storeId);
			List<ProGetPayModel> list = new ArrayList<ProGetPayModel>();
			list.add(new ProGetPayModel(1,productId));
			map.put("products", list);
			String jsonObject = JSONObject.parseObject(HttpUtils.postMofangJson(getMofangSessionId(),url, JSONObject.toJSONString(map))).getJSONObject("data").getString("orderNo");
			Map<String, Object> res = new HashMap<String,Object>();
			res.put("order", jsonObject);
			res.put("amount", "1");
			return new Resp<>(res);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		
		return resp;
	}

	
	@RequestMapping(path = "/pay")
	@ResponseBody
	public Resp<?> pay(@RequestBody Map<String, Object> data){
		Resp<?> resp = new Resp<>(false);
		try {
			String token = getString(data, BaseConstant.TOKEN);
			String orderNo = getString(data, "orderNo");
			String amount = getString(data, "amount");
			log.warn("token:{},orderId:{},appId:{}",token,orderNo);
			if(StringUtil.isBlank(token)||StringUtil.isBlank(orderNo)){
				resp.setMsg(RespData.PARAMS_ERROR);
				return resp;
			}
			String url = "http://120.92.101.137:8081/payment/pay";
			Map<String, Object> map = new HashMap<String,Object>();
			map.put("amount", amount);
			map.put("type", "bar_code");
			map.put("orderNo", orderNo);
			map.put("authCode", "");
			map.put("action", "RECORD");
			JSONObject jsonObject = JSONObject.parseObject(HttpUtils.postMofangJson(getMofangSessionId(), url, JSONObject.toJSONString(map))).getJSONObject("data");
			return new Resp<>(jsonObject);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		
		return resp;
	}
	
	

    private Double sendNotice(String mac,String companyId,String storeId,String productId){
        try {
            Map<String,String> map = new HashMap<String, String>();
            map.put("magneticStripleId",mac);
            map.put("status","EMPTY");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            map.put("occurrenceTimeString",sdf.format(new Date()));
            map.put("companyOrganId",companyId);
            map.put("storeOrganId",storeId);
            map.put("eventId",mac+"_"+new Date().getTime());
            String jsonStr = JSON.toJSONString(map);
            HttpUtils.postMofangJson(getMofangSessionId(), "http://120.92.101.137:8083/magnetic_striple_event",jsonStr);
        	String url = "http://120.92.101.137:8083/product?productId="+productId+"&storeOrganId="+storeId;
			List<ProOrder> orders = JSONArray.parseArray(JSONObject.parseObject(HttpUtils.getMofang(getMofangSessionId(), url)).getJSONObject(BaseConstant.DATA).getString("products"),ProOrder.class);
			ProOrder proOrder = orders.get(0);
			return proOrder.getPrice();
        }catch (Exception e){
            log.error("error:{}",e);
        }
        return null;
    }


}
