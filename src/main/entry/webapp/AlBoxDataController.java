package main.entry.webapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import database.models.AliParking;
import service.basicFunctions.AliParkingService;
import utils.model.Resp;

@RequestMapping(value = "/d/aibox")
@Controller
public class AlBoxDataController extends BaseController{

	private static final Logger log = LoggerFactory.getLogger(AlBoxDataController.class);
	
	@Autowired
	private AliParkingService aliParkingService;
	
	@RequestMapping(path = "save")
	@ResponseBody
	public Resp<?> save(AliParking ali){
		Resp<?> resp = new Resp<>(false);
		try {
			aliParkingService.save(ali);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	/**
	 * 导入json
	 * @return
	 */
	@RequestMapping(path = "/in")
	@ResponseBody
	public Resp<?> in(){
		Resp<?> resp = new Resp<>(false);
		try {
			String s = readFileContent("/Users/jinx/Downloads/ali.txt");
			System.out.println(s);
			JSONArray arr = JSONObject.parseArray(s);
			for(int i = 0;i<arr.size();i++) {
				JSONObject jobj = arr.getJSONObject(i);
				String transParams = jobj.getString("transParams");
				String picUrl = JSONObject.parseObject(transParams).getString("pictureUrl");
				String payload = jobj.getString("payload");
				String data = JSONObject.parseObject(payload).getString("Data");
				JSONObject jdata = JSONObject.parseObject(data);
				String type = jdata.getString("type");
				String plateNumber = jdata.getString("plateNumber");
				String status = jdata.getString("status");
				String eventTime = jdata.getString("eventTime");
				AliParking ali = new AliParking();
				ali.setCreateTime(new Date());
				ali.setPlateNumber(plateNumber);
				ali.setStatus(status);
				ali.setType(type);
				ali.setEventTime(eventTime);
				ali.setPicUrl(picUrl);
				aliParkingService.save(ali);
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("e:{}",e);
		}
		return resp;
	}
	
	public String readFileContent(String fileName) {
	    File file = new File(fileName);
	    BufferedReader reader = null;
	    StringBuffer sbf = new StringBuffer();
	    try {
	        reader = new BufferedReader(new FileReader(file));
	        String tempStr;
	        while ((tempStr = reader.readLine()) != null) {
	            sbf.append(tempStr);
	        }
	        reader.close();
	        return sbf.toString();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        if (reader != null) {
	            try {
	                reader.close();
	            } catch (IOException e1) {
	                e1.printStackTrace();
	            }
	        }
	    }
	    return sbf.toString();
	}
	
}
