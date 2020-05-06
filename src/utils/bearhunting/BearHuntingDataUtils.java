package utils.bearhunting;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.models.log.LogSensorStatus;
import utils.HttpUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BearHuntingDataUtils {

	 private static final Logger log = LoggerFactory.getLogger(BearHuntingDataUtils.class);

	    public static String sendStatus(String url,LogSensorStatus s, String routerMac,Integer status){
	        String res = "{\"status\":0}";
	        try {
	            Date date = new Date();
	            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	            String params = "Id3="+routerMac+"&devid="+s.getMac()+"&tick="+date.getTime()+"&event="+s.getAvailable()+
	                    "&eventtime="+simpleDateFormat.format(date.getTime())+"&category="+status;
	            String sendParams = params+"&sign="+KeyUtils.get(params);
	            log.warn("[bearhunting status: params{}]",sendParams);
	            res = HttpUtil.sendPost(url,sendParams);
	        }catch (Exception e){
	            log.error("[bearhunting status error:{}]",e);
	        }
	        return res;
	    }

//	    public static void main(String[] args){
//	        Date date = new Date();
//	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	        String params = "Id3=00163e0e0f3d&devid=000118031600000A&tick=1537753651654&event=1"+
//	                "&eventtime="+simpleDateFormat.format(date.getTime())+"&category=1";
//	        String sendParams = params+"&sign="+KeyUtils.get(params);
//	        log.warn("[bearhunting status: params{}]",sendParams);
//	        String res = HttpUtils.sendPost(KeyUtils.STATUS_URL,sendParams);
//	        System.out.print(res);
//	    }


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
