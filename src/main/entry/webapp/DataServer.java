package main.entry.webapp;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.home.HomeUser;
import service.basicFunctions.BaseService;
import utils.enums.DataCode;
import utils.model.Resp;
import utils.model.RespData;

@Controller
public class DataServer extends BaseController implements ApplicationContextAware {

	private static final Logger logger = LoggerFactory.getLogger(DataServer.class);

	private ApplicationContext ctx;
	

	@RequestMapping(value = "/d/{type}/{operator}/{version}", produces = "application/json")
	@ResponseBody
	public Resp<?> executeInterface(@PathVariable(value = "type") String type,
									@PathVariable(value = "operator") String operator,
									@PathVariable(value = "version") String version,
									HttpServletRequest request) {
		DataCode dataCode = DataCode.getByCode(type+"_"+operator + "_" + version);
		Resp<?> resp = new Resp<>(false);
		if (dataCode == null) {
			resp = new Resp<>(RespData.NOT_FIND_CODE, RespData.NOT_FIND_MSG, null);
			return resp;
		}else if(!RespData.POST.equals(request.getMethod().toUpperCase())){
			resp = new Resp<>(RespData.NOT_VILIDATE_METHOD_CODE, RespData.NOT_VILIDATE_METHOD_MSG, null);
			return resp;
		}else {
			try {
				if(dataCode.isNeedLogin()){
					HomeUser homeUser = getSessionHomeUser(request);
					if(homeUser==null){
						resp = new Resp<>(RespData.NOT_LOGIN_CODE, RespData.NOT_LOGIN_MSG, null);
						return resp;
					}
				}
				String jsonData = getRequestPostStr(request);
				logger.warn("[executeInterface]data:{},bean:{}", jsonData, dataCode.getServerBean());
				BaseService baseService = (BaseService) ctx.getBean(dataCode.getServerBean());
				resp = (Resp<?>) baseService.execute(jsonData, dataCode.getFunc());
				return resp;
			} catch (Exception e) {
				logger.error("error:{}",e);
			}
		}
		return resp;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.ctx = applicationContext;
	}

}
