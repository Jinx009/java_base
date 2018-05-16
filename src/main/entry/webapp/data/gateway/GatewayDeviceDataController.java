package main.entry.webapp.data.gateway;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGatewaySmokeDataService;
import utils.Resp;

@Controller
@RequestMapping("/rest/device")
public class GatewayDeviceDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GatewayDeviceDataController.class);
	
	@Autowired
	private ProGatewaySmokeDataService proGatewaySmokeDataService;
	
	@RequestMapping(path = "/push")
	@ResponseBody
	public Resp<?> push(HttpServletRequest req){
		Resp<?> resp = new Resp<>(false);
		try {
			InputStream in = req.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String data =br.readLine();
			log.warn("data:{}",data);
			proGatewaySmokeDataService.save(data);
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
} 
