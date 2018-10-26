package main.entry.webapp.data.gateway;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.project.ProGatewaySmokeDevice;
import lombok.Getter;
import lombok.Setter;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProGatewaySmokeDataService;
import service.basicFunctions.project.ProGatewaySmokeDeviceService;
import utils.HttpUtils;
import utils.Resp;

@Controller
@RequestMapping("/rest/device")
public class GatewayDeviceDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(GatewayDeviceDataController.class);
	
	@Autowired
	private ProGatewaySmokeDataService proGatewaySmokeDataService;
	@Autowired
	private ProGatewaySmokeDeviceService proGatewaySmokeDeviceService;
	

	
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
			ProGatewaySmokeDevice proGatewaySmokeDevice = proGatewaySmokeDeviceService.findByMac(data.substring(10,25));
			if(proGatewaySmokeDevice!=null){
				if(proGatewaySmokeDevice.getSendUrl()!=null&&!"".equals(proGatewaySmokeDevice.getSendUrl())){
					ExecutorService executorService = Executors.newCachedThreadPool();
					executorService.execute(new SendThread(data,proGatewaySmokeDevice.getSendUrl()));
					executorService.shutdown();
					return new Resp<>(true);
				}
			}else{
				proGatewaySmokeDeviceService.save(data.substring(10,25));
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	
	
} 
@Getter
@Setter
class SendThread implements Runnable{
	
	private static final Logger log = LoggerFactory.getLogger(SendThread.class);
	
	private String data;
	private String url;
	
	public SendThread(String data,String url){
		this.data = data;
		this.url = url;
	}

	public void run() {
		try {
			log.warn("smoke data:{}",data);
			HttpUtils.postText(url,data);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(!Thread.currentThread().isInterrupted()){
				Thread.currentThread().interrupt();
			}
		}
	}
	
}
