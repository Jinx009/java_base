package main.entry.webapp.data.qj;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.qj.QjDevice;
import main.entry.webapp.BaseController;
import service.basicFunctions.qj.QjDeviceService;
import utils.Resp;

@Controller
@RequestMapping(value = "/home/cloud/qj")
public class QingjiaoDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(QingjiaoDataController.class);

	@Autowired
	private QjDeviceService qjDeviceService;

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
	
	@RequestMapping(path = "/push")
	@ResponseBody
	public Resp<?> push(String data) {
		try {
			String[] dataArray = data.split("");
			String type = dataArray[0] + dataArray[1];
			if (type.equals("68")) {
				type = "报警";
			} else {
				type = "心跳";
			}
			String sn = dataArray[2] + dataArray[3] + dataArray[4] + dataArray[5] + dataArray[6] + dataArray[7]
					+ dataArray[8]+ dataArray[9];
			QjDevice qjDevice = qjDeviceService.findBySn(sn);
			if (qjDevice == null) {
				qjDevice = new QjDevice();
				qjDevice.setSnValue(sn);
				qjDevice.setType(type);
				qjDevice.setCreateTime(new Date());
				qjDevice.setXType(Integer.valueOf(dataArray[10] + dataArray[11]));
				qjDevice.setXValue(getData(dataArray[12], dataArray[12] + dataArray[13] + dataArray[14] + dataArray[15]));
				qjDevice.setYType(Integer.valueOf(dataArray[16] + dataArray[17]));
				qjDevice.setYValue(getData(dataArray[18], dataArray[18] + dataArray[19] + dataArray[20] + dataArray[21]));
				qjDevice.setVoltage(getData(dataArray[22], dataArray[22] + dataArray[23] + dataArray[24] + dataArray[25]));
				qjDeviceService.save(qjDevice);
			}else{
				qjDevice.setSnValue(sn);
				qjDevice.setType(type);
				qjDevice.setCreateTime(new Date());
				qjDevice.setXType(Integer.valueOf(dataArray[10] + dataArray[11]));
				qjDevice.setXValue(getData(dataArray[12], dataArray[12] + dataArray[13] + dataArray[14] + dataArray[15]));
				qjDevice.setYType(Integer.valueOf(dataArray[16] + dataArray[17]));
				qjDevice.setYValue(getData(dataArray[18], dataArray[18] + dataArray[19] + dataArray[20] + dataArray[21]));
				qjDevice.setVoltage(getData(dataArray[22], dataArray[22] + dataArray[23] + dataArray[24] + dataArray[25]));
				qjDeviceService.update(qjDevice);
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
		String[] arr = b.split("");
		String c = "";
		Integer e = Integer.parseInt(b, 2);
		if (_index>8) {
			for (String d : arr) {
				if (d.equals("1")) {
					c += "0";
				} else {
					c += "1";
				}
			}
			e = (Integer.parseInt(c, 2) + 1) * -1;
		}else{
			e = Integer.valueOf(String.valueOf(Integer.parseInt(_d, 16)));
		}
		return String.valueOf(Double.valueOf(e)/1000);
	}
}
