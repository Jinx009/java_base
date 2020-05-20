package utils.bearhunting;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.models.log.LogSensorStatus;
import utils.HttpUtil;

import java.util.Date;

public class BearHuntingDataUtils {

	 private static final Logger log = LoggerFactory.getLogger(BearHuntingDataUtils.class);

	    public static String sendStatus(String url,LogSensorStatus s){
	        String res = "{\"status\":0}";
	        try {
	            Date date = new Date();
	            String action = "block";
	            if(s.getAvailable()==0){
	            	action = "unblock";
	            }
	            String params = "action="+action+"&devId="+s.getMac()+"&timeStamp="+date.getTime();
	            String sendParams = params+"&signature="+KeyUtils.get(params);
	            log.warn("[bearhunting status: params{}]",sendParams);
	            res = HttpUtil.sendPost(url,sendParams);
	        }catch (Exception e){
	            log.error("[bearhunting status error:{}]",e);
	        }
	        return res;
	    }

	    public static void main(String[] args){
	    	LogSensorStatus log = new LogSensorStatus();
	    	log.setAvailable(0);
	    	log.setMac("0001191107000099");
	        System.out.print(sendStatus(KeyUtils.STATUS_FIRE_URL, log));
	    }


//	    public static String sendStatusFire(String url,SensorOperationLog s, String routerMac){
//	        String res = "{\"status\":0}";
//	        try {
//	            Date date = new Date();
//	            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	            String params = "Id3="+routerMac+"&devid="+s.getMac()+"&tick="+date.getTime()+"&event="+s.getAvailable()+
//	                    "&eventtime="+simpleDateFormat.format(date.getTime());
//	            String sendParams = params+"&sign="+KeyUtils.get(params);
//	            log.warn("[bearhunting status: params{}]",sendParams);
//	            res = HttpUtils.sendPost(url,sendParams);
//	        }catch (Exception e){
//	            log.error("[bearhunting status error:{}]",e);
//	        }finally {
//	            return res;
//	        }
//	    }
//	
}
