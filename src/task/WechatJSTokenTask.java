package task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import database.models.pro.ProWechatToken;
import service.basicFunctions.pro.ProWechatTokenService;
import utils.wechat.WechatData;
import utils.wechat.WechatUtil;

@Component
@Lazy(value=false)
public class WechatJSTokenTask {

    private static final Logger logger = LoggerFactory.getLogger(WechatJSTokenTask.class);
    
    @Autowired
    private ProWechatTokenService proWechatTokenService;

    @Scheduled(cron = "0 */2 * * * ")//每一小时执行一次
    public void refreshen() throws Exception {
       ProWechatToken proWechatToken = proWechatTokenService.getByTypeAndId(WechatData.APP_ID,1);
       if(proWechatToken!=null){
    	   checkWechatCache(WechatData.APP_ID,WechatData.APP_SECRET,proWechatToken);
       }else{
    	   long currentTimestamp = (long) (System.currentTimeMillis() / 1000);
    	   String jsapi_ticket = WechatUtil.getJSApiTicket(WechatData.APP_ID, WechatData.APP_SECRET);
    	   proWechatToken = new ProWechatToken();
    	   proWechatToken.setBaseId(WechatData.APP_ID);
    	   proWechatToken.setBaseType(1);
    	   proWechatToken.setLastTime(String.valueOf(currentTimestamp));
    	   proWechatToken.setTokenType(1);
    	   proWechatToken.setTokenValue(jsapi_ticket);
    	   proWechatTokenService.save(proWechatToken);
       }
    }
    
    /**
     * 获取
     */
	public void checkWechatCache(String appId, String appSecret, ProWechatToken proWechatToken) throws Exception{
		if (!getTimestamp(Long.valueOf(proWechatToken.getLastTime()))){
			long currentTimestamp = (long) (System.currentTimeMillis() / 1000);
			logger.warn("[WechatJSTokenTask.checkWechatCache:{}]",currentTimestamp);
			String jsapi_ticket = WechatUtil.getJSApiTicket(appId, appSecret);
			proWechatToken.setLastTime(String.valueOf(currentTimestamp));
			proWechatToken.setTokenValue(jsapi_ticket);
			proWechatTokenService.update(proWechatToken);
		} 
	}
	
	/**
	 * 校验时间是否过期
	 */
	public static boolean getTimestamp(long timestamp){
		long currentTimestamp = (long) (System.currentTimeMillis() / 1000);
		if ((currentTimestamp - timestamp) >= 7200){
			return false;
		}
		return true;
	}


    //@Scheduled(cron = "0 0 1 * * ?")//每天凌晨1点整
    //@Scheduled(cron = "0 30 0 * * ?")//每天凌晨0点30分
    //@Scheduled(cron = "0 */60 * * * ?")//1小时处理一次

}