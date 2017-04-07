package task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import database.models.WebTokenFactory;
import service.basicFunctions.WebTokenFactoryService;
import utils.wechat.WechatData;
import utils.wechat.WechatUtil;

@Component
@Lazy(value=false)
public class WechatJSTokenTask {

    private static final Logger logger = LoggerFactory.getLogger(WechatJSTokenTask.class);
    
    @Autowired
    private WebTokenFactoryService webTokenFactoryService;

    @Scheduled(fixedRate = 60*1000)//每一分钟执行一次
    public void refreshen() throws Exception {
       WebTokenFactory webTokenFactory = webTokenFactoryService.getByTypeAndId(WechatData.APP_ID,1);
       if(webTokenFactory!=null){
    	   checkWechatCache(WechatData.APP_ID,WechatData.APP_SECRET,webTokenFactory);
       }else{
//    	   long currentTimestamp = (long) (System.currentTimeMillis() / 1000);
//    	   String jsapi_ticket = WechatUtil.getJSApiTicket(WechatData.APP_ID, WechatData.APP_SECRET);
//    	   webTokenFactory = new WebTokenFactory();
//    	   webTokenFactory.setBaseId(WechatData.APP_ID);
//    	   webTokenFactory.setBaseType(1);
//    	   webTokenFactory.setLastTime(String.valueOf(currentTimestamp));
//    	   webTokenFactory.setTokenType(1);
//    	   webTokenFactory.setTokenValue(jsapi_ticket);
//    	   webTokenFactoryService.save(webTokenFactory);
       }
    }
    
    /**
     * 获取
     */
	public void checkWechatCache(String appId, String appSecret, WebTokenFactory webTokenFactory) throws Exception{
		if (!getTimestamp(Long.valueOf(webTokenFactory.getLastTime()))){
			long currentTimestamp = (long) (System.currentTimeMillis() / 1000);
			logger.warn("[WechatJSTokenTask.checkWechatCache:{}]",currentTimestamp);
			String jsapi_ticket = WechatUtil.getJSApiTicket(appId, appSecret);
			webTokenFactory.setLastTime(String.valueOf(currentTimestamp));
			webTokenFactory.setTokenValue(jsapi_ticket);
			webTokenFactoryService.update(webTokenFactory);
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