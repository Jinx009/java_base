package service.basicFunctions;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.dao.AccAngleLogDao;
import database.dao.IoTCloudEventLogDao;
import database.models.AccAngleLog;
import database.models.IoTCloudEventLog;

@Service
public class AccAngleLogService {

	@Autowired
	private AccAngleLogDao accAngleLogDao;
	@Autowired
	private IoTCloudEventLogDao ioTCloudEventLogDao;

	public void save(String data, int type) {
		try {
			if(type==3) {
				String mac = data.substring(0, 16);
				String cmd = data.substring(20, 22);
				String x = null,y = null;
				if("68".equals(cmd)) {
					x = getData100(data.substring(50, 51), data.substring(50, 54));
					y = getData100(data.substring(54, 55), data.substring(54, 58));
				}
				if("69".equals(cmd)) {
					x = getData100(data.substring(54, 55), data.substring(54, 58));
					y = getData100(data.substring(58, 59), data.substring(58, 62));
				}
				AccAngleLog log = accAngleLogDao.findByMac(mac);
				if(x!=null) {
					double dx = Double.valueOf(x);
					if(Math.abs(dx)>Math.abs(log.getX())) {
						log.setUpdateTime(new Date());
						log.setX(dx);
						accAngleLogDao.update(log);
					}
					if(Math.abs(dx)>3.5) {
						IoTCloudEventLog event = new IoTCloudEventLog();
						event.setCreateTime(new Date());
						event.setFatherType("WARN");
						event.setType("ANGEL");
						event.setMac(mac);
						event.setDescription("当日x角度值到达："+dx);
						ioTCloudEventLogDao.save(event);
					}
				}
				if(y!=null) {
					double dy = Double.valueOf(y);
					if(Math.abs(dy)>Math.abs(log.getY())) {
						log.setUpdateTime(new Date());
						log.setX(dy);
						accAngleLogDao.update(log);
					}
					if(Math.abs(dy)>3.5) {
						IoTCloudEventLog event = new IoTCloudEventLog();
						event.setCreateTime(new Date());
						event.setFatherType("WARN");
						event.setType("ANGEL");
						event.setMac(mac);
						event.setDescription("当日y角度值到达："+dy);
						ioTCloudEventLogDao.save(event);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private String getData100(String index, String _d) throws Exception {
		int _index = Integer.parseInt(index, 16);
		Integer a = Integer.valueOf(_d, 16);
		String b = Integer.toBinaryString(a);
		String[] arrs = b.split("");
		String[] arr = new String[16];
		int i = 0;
		for (String s : arrs) {
			if (s != null && !"".equals(s)) {
				arr[i] = s;
				i++;
			}
		}
		String c = "";
		Integer e = Integer.parseInt(b, 2);
		if (_index > 8) {
			for (String d : arr) {
				if (d != null && !"".equals(d)) {
					if (d.equals("1")) {
						c += "0";
					} else {
						c += "1";
					}
				}
			}
			e = (Integer.parseInt(c, 2) + 1) * -1;
		} else {
			e = Integer.parseInt(_d, 16);
		}
		Double r = Double.valueOf(e) / 100;
		BigDecimal f = new BigDecimal(r);
		double g = f.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
		DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
		String result = decimalFormat.format(g);
		return result;
	}


	public String convertHexToString(String hex) {
		StringBuilder sb = new StringBuilder();
		StringBuilder temp = new StringBuilder();
		for (int i = 0; i < hex.length() - 1; i += 2) {
			String output = hex.substring(i, (i + 2));
			int decimal = Integer.parseInt(output, 16);
			sb.append((char) decimal);
			temp.append(decimal);
		}
		return sb.toString();
	}
	
	
}
