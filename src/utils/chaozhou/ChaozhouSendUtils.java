package utils.chaozhou;

import java.util.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import database.models.log.LogSensorStatus;
import utils.HttpUtil;

public class ChaozhouSendUtils {

	 private static final Logger log = LoggerFactory.getLogger(ChaozhouSendUtils.class);

	    public static String sendStatus(LogSensorStatus s, String routerMac){
	        String res = "{\"state\":1}";
	        try {
	            Date date = new Date();
	            String params = "id3="+routerMac+"&devid="+s.getMac()+"&tick="+date.getTime()+"&event="+s.getAvailable()+"&eventtick="+s.getChangeTime().getTime();
	            String sendParams = params+"&sign="+ KeyUtils.get(params);
	            log.warn("[chaozhou status: params{}]",sendParams);
	            res = HttpUtil.sendPost(KeyUtils.STATUS_URL,sendParams);
	        }catch (Exception e){
	            log.error("[chaozhou status error:{}]",e);
	        }
	        return res;
	    }
	
}
