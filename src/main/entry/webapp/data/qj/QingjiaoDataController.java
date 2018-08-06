package main.entry.webapp.data.qj;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import common.helper.StringUtil;
import database.models.qj.QjDevice;
import database.models.qj.QjDeviceLog;
import main.entry.webapp.BaseController;
import service.basicFunctions.qj.QjDeviceLogService;
import service.basicFunctions.qj.QjDeviceService;
import utils.BaseConstant;
import utils.Resp;
import utils.msg.AlimsgUtils;

@Controller
@RequestMapping(value = "/home/cloud/qj")
public class QingjiaoDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(QingjiaoDataController.class);

	@Autowired
	private QjDeviceService qjDeviceService;
	@Autowired
	private QjDeviceLogService qjDeviceLogService;

	@RequestMapping(path = "/list")
	@ResponseBody
	public Resp<?> list(){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(qjDeviceService.findList());
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/logs")
	@ResponseBody
	public Resp<?> logs(){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(qjDeviceLogService.nearList());
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/update")
	@ResponseBody
	public Resp<?> update(String sn,String mobilePhone,Integer businessType,Integer  noticeType,Integer doneType,String longitude,String latitude,String address){
		Resp<?> resp = new Resp<>(false);
		try {
			QjDevice qjDevice = qjDeviceService.findBySn(sn);
			if(qjDevice!=null){
				if(StringUtil.isNotBlank(mobilePhone)){
					qjDevice.setMobilePhone(mobilePhone);
				}
				if(businessType!=null){
					qjDevice.setBusinessType(businessType);
				}
				if(noticeType!=null){
					qjDevice.setNoticeType(noticeType);
				}
				if(doneType!=null){
					qjDevice.setDoneType(doneType);
				}
				if(StringUtil.isNotBlank(latitude)){
					qjDevice.setLatitude(latitude);
				}
				if(StringUtil.isNotBlank(longitude)){
					qjDevice.setLongitude(longitude);
				}
				if(StringUtil.isNotBlank(address)){
					qjDevice.setAddress(address);
				}
				qjDeviceService.update(qjDevice);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
	@RequestMapping(path = "/push")
	@ResponseBody
	public Resp<?> push(String data) {
		try {
			String[] dataArray2 = data.split("");
			String[] dataArray = new String[100];
			int i = 0;
			for(String s:dataArray2){
				if(s!=null&&!"".equals(s)){
					dataArray[i] = s;
					i++;
				}
			}
			String type = dataArray[0] + dataArray[1];
			if (type.equals("68")) {
				type = "报警";
			} else {
				type = "心跳";
			}
			String sn = String.valueOf(Integer.parseInt(dataArray[2] + dataArray[3] + dataArray[4] + dataArray[5] + dataArray[6] + dataArray[7]+ dataArray[8]+ dataArray[9], 16));
			QjDevice qjDevice = qjDeviceService.findBySn(sn);
			if (qjDevice == null) {
				qjDevice = new QjDevice();
				qjDevice.setSnValue(sn);
				qjDevice.setType(type);
				qjDevice.setCreateTime(new Date());
				qjDevice.setXType(Integer.valueOf(dataArray[10] + dataArray[11]));
				qjDevice.setBaseX(getData(dataArray[12], dataArray[12] + dataArray[13] + dataArray[14] + dataArray[15]));
				qjDevice.setXValue(getData(dataArray[12], dataArray[12] + dataArray[13] + dataArray[14] + dataArray[15]));
				qjDevice.setYType(Integer.valueOf(dataArray[16] + dataArray[17]));
				qjDevice.setYValue(getData(dataArray[18], dataArray[18] + dataArray[19] + dataArray[20] + dataArray[21]));
				qjDevice.setBaseY(getData(dataArray[18], dataArray[18] + dataArray[19] + dataArray[20] + dataArray[21]));
				qjDevice.setVoltage(getData(dataArray[22], dataArray[22] + dataArray[23] + dataArray[24] + dataArray[25]));
				qjDevice.setDoneType(0);
				qjDeviceService.save(qjDevice);
				
			}else{
				qjDevice.setSnValue(sn);
				qjDevice.setType(type);
				qjDevice.setCreateTime(new Date());
				qjDevice.setXType(Integer.valueOf(dataArray[10] + dataArray[11]));
				String xValue = getData(dataArray[12], dataArray[12] + dataArray[13] + dataArray[14] + dataArray[15]);
				qjDevice.setBaseX(getData(dataArray[12], dataArray[12] + dataArray[13] + dataArray[14] + dataArray[15]));
				double _x = Double.valueOf(xValue)-Double.valueOf(qjDevice.getXValue());
				qjDevice.setXValue(String.valueOf(_x));
				qjDevice.setYType(Integer.valueOf(dataArray[16] + dataArray[17]));
				String yValue = getData(dataArray[18], dataArray[18] + dataArray[19] + dataArray[20] + dataArray[21]);
				qjDevice.setBaseY(yValue);
				double _y = Double.valueOf(yValue)-Double.valueOf(qjDevice.getYValue());
				qjDevice.setYValue(String.valueOf(_y));
				qjDevice.setVoltage(getData(dataArray[22], dataArray[22] + dataArray[23] + dataArray[24] + dataArray[25]));
				qjDevice.setDoneType(0);
				qjDeviceService.update(qjDevice);
				QjDeviceLog qjDeviceLog = new QjDeviceLog();
				qjDeviceLog.setBaseX(qjDevice.getBaseX());
				qjDeviceLog.setBaseY(qjDevice.getBaseY());
				qjDeviceLog.setSnValue(qjDevice.getSnValue());
				qjDeviceLog.setType(qjDevice.getType());
				qjDeviceLog.setVoltage(qjDevice.getVoltage());
				qjDeviceLog.setXType(qjDevice.getXType());
				qjDeviceLog.setYType(qjDevice.getYType());
				QjDeviceLog qjDeviceLog2 = qjDeviceLogService.getNearBySn(sn);
				if((qjDeviceLog2!=null&&!qjDeviceLog2.getBaseX().equals(qjDeviceLog.getBaseX())&&!qjDeviceLog2.getBaseY().equals(qjDeviceLog.getBaseY()))||qjDeviceLog2==null){
					if(qjDevice.getMobilePhone()!=null&&!"".equals(qjDevice.getMobilePhone())&&qjDevice.getNoticeType()!=null&&qjDevice.getNoticeType()==1){
						if(qjDevice.getBusinessType()==0){
							AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_MOUNTAIN_TEMPLATE, BaseConstant.MESSAGE_SIGN);
							AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_ROAD_TEMPLATE, BaseConstant.MESSAGE_SIGN);
						}else if(qjDevice.getBusinessType() == 1){
							AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_MOUNTAIN_TEMPLATE, BaseConstant.MESSAGE_SIGN);
						}else if(qjDevice.getBusinessType() == 1){
							AlimsgUtils.send(qjDevice.getMobilePhone(), BaseConstant.MESSAGE_QJ_ROAD_TEMPLATE, BaseConstant.MESSAGE_SIGN);
						}
					}
				}
				qjDeviceLogService.save(qjDeviceLog);
			}
			
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return new Resp<>(true);
	}

	private String getData(String index, String _d) throws Exception{
		log.warn("index:{},data:{}",index,_d);
		int _index = Integer.parseInt(index,16);
		Integer a = Integer.valueOf(_d, 16);
		String b = Integer.toBinaryString(a);
		String[] arrs = b.split("");
		String[] arr = new String[16]; 
		int i = 0;
		for(String s:arrs){
			if(s!=null&&!"".equals(s)){
				arr[i] = s;
				i++;
			}
		}
		String c = "";
		Integer e = Integer.parseInt(b, 2);
		if (_index>8) {
			for (String d : arr) {
				if(d!=null&&!"".equals(d)){
					if (d.equals("1")) {
						c += "0";
					} else {
						c += "1";
					}
				}
			}
			e = (Integer.parseInt(c, 2) + 1) * -1;
		}else{
			e = Integer.parseInt(_d, 16);
		}
		String result = String.valueOf(Double.valueOf(e)/1000);
		log.warn("result:{}",result);
		return result;
	}
}
