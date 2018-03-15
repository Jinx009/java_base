package main.entry.webapp.data.gateway;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;
import database.models.project.ProGatewayArea;
import database.models.project.ProGatewayLocation;
import database.models.project.model.ProParkViewModel;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGatewayAreaService;
import service.basicFunctions.project.ProGatewayLocationService;
import utils.BaseConstant;
import utils.HttpUtils;
import utils.Resp;
import utils.RespData;

@Controller
@RequestMapping(value = "/rest/area")
public class GatewayAreaDataController extends BaseController{
	
	private static final Logger log = LoggerFactory.getLogger(GatewayAreaDataController.class);

	@Autowired
	private ProGatewayAreaService proGatewayAreaService;
	@Autowired
	private ProGatewayLocationService proGatewayLocationService;
	
	@RequestMapping(path = "/all")
	@ResponseBody
	public Resp<?> getArea(@RequestBody Map<String,Object> data){
		Resp<?> resp = new Resp<>(false);
		try {
			Integer locationId = getInt(data, BaseConstant.LOCATION_ID_NAME);
			String token = getString(data, BaseConstant.TOKEN);
			if(StringUtil.isBlank(token)||locationId==null){
				resp.setMsg(RespData.PARAMS_ERROR);
				return resp;
			}
			ProGatewayLocation proGatewayLocation = proGatewayLocationService.getByLocationId(locationId);
			if(!checkToken(token,getToken(proGatewayLocation.getAppId()))){
				resp.setMsg(RespData.TOKEN_CHECK_ERROR);
				return resp;
			}
			List<ProGatewayArea> areas = proGatewayAreaService.getByLocationId(locationId);
			if(areas!=null&&!areas.isEmpty()){
				for(ProGatewayArea area : areas){
					Integer av = 0;
					String url = "http://wx.zhanway.com/gtw/rest/parking/detail?token="+token+"&areaId="+area.getAreaId();
					List<ProParkViewModel> list = JSONArray.parseArray(JSONObject.parseObject(HttpUtils.get(url)).getString(BaseConstant.PARAMS), ProParkViewModel.class);
					for(ProParkViewModel proParkViewModel : list){
						if(proParkViewModel.getAvailable()==0){
							av++;
						}
					}
					area.setAvailable(av);
					area.setTotal(list.size());
				}
			}
			return new Resp<>(areas);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	@RequestMapping(path = "/get")
	@ResponseBody
	public Resp<?> get(@RequestBody Map<String,Object> data){
		Resp<?> resp = new Resp<>(false);
		try {
			Integer areaId = getInt(data, BaseConstant.AREA_ID_NAME);
			String token = getString(data, BaseConstant.TOKEN);
			if(StringUtil.isBlank(token)||areaId==null){
				resp.setMsg(RespData.PARAMS_ERROR);
				return resp;
			}
			ProGatewayArea area = proGatewayAreaService.getByAreaId(areaId);
			Integer av = 0;
			String url = "http://wx.zhanway.com/gtw/rest/parking/detail?token="+token+"&areaId="+areaId;
			List<ProParkViewModel> list = JSONArray.parseArray(JSONObject.parseObject(HttpUtils.get(url)).getString(BaseConstant.PARAMS), ProParkViewModel.class);
			for(ProParkViewModel proParkViewModel : list){
				if(proParkViewModel.getAvailable()==0){
					av++;
				}
			}
			area.setAvailable(av);
			area.setTotal(list.size());
			return new Resp<>(area);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
