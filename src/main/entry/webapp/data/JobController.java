package main.entry.webapp.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;

import database.models.IoTCloudDevice;
import database.models.IotCloudLog;
import main.entry.webapp.BaseController;
import service.basicFunctions.IotCloudDeviceService;
import service.basicFunctions.IotCloudLogService;
import utils.BaseConstant;
import utils.Constant;
import utils.HttpUtils;
import utils.HttpsUtil;
import utils.JsonUtil;
import utils.Resp;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Controller
@RequestMapping(value = "/job")
public class JobController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(JobController.class);

	@Autowired
	private IotCloudDeviceService iotCloudDeviceService;
	@Autowired
	private IotCloudLogService iotCloudLogService;

	@RequestMapping(path = "/send")
	@ResponseBody
	public Resp<?> job(@RequestBody Map<String, Object> data) {
		Resp<String> resp = new Resp<>(false);
		try {
			String mac = getString(data, "mac");
			String data1 = getString(data, "data");
			IoTCloudDevice ioTCloudDevice = iotCloudDeviceService.findByMac(mac);
			if (ioTCloudDevice.getType() == 1) {
				String msg = HttpUtils.putUnicomJson(BaseConstant.getSendUrl(ioTCloudDevice.getImei()),
						"{\"resourceValue\":\"" + data1 + "\"}", "endhZG1pbjpadyMwMjE=");
				IotCloudLog iotCloudLog = new IotCloudLog();
				iotCloudLog.setData(data1);
				iotCloudLog.setFromSite("unicom");
				iotCloudLog.setImei(ioTCloudDevice.getImei());
				iotCloudLog.setMac(ioTCloudDevice.getMac());
				iotCloudLog.setType(1);
				iotCloudLog.setCreateTime(new Date());
				iotCloudLogService.save(iotCloudLog);
				log.warn("msg:{}", msg);
			} else if (ioTCloudDevice.getType() == 2) {
				HttpsUtil httpsUtil = new HttpsUtil();
				httpsUtil.initSSLConfigForTwoWay();
				String accessToken = login(httpsUtil);
				String urlPostAsynCmd = Constant.POST_ASYN_CMD;
				String appId = Constant.APPID;
				String deviceId = ioTCloudDevice.getDeviceId();
				String callbackUrl = Constant.REPORT_CMD_EXEC_RESULT_CALLBACK_URL;
				String serviceId = "data";
				String method = "command";
				ObjectNode paras = JsonUtil.convertObject2ObjectNode("{\"CMD_DATA\":\"" + data1 + "\"}");
				Map<String, Object> paramCommand = new HashMap<>();
				paramCommand.put("serviceId", serviceId);
				paramCommand.put("method", method);
				paramCommand.put("paras", paras);
				Map<String, Object> paramPostAsynCmd = new HashMap<>();
				paramPostAsynCmd.put("deviceId", deviceId);
				paramPostAsynCmd.put("command", paramCommand);
				paramPostAsynCmd.put("callbackUrl", callbackUrl);
				String type =  getString(data, "type");
				if(type!=null&&"0".equals(type)){
					paramPostAsynCmd.put("expireTime", 0);
				}
				String jsonRequest = JsonUtil.jsonObj2Sting(paramPostAsynCmd);
				Map<String, String> header = new HashMap<>();
				header.put(Constant.HEADER_APP_KEY, appId);
				header.put(Constant.HEADER_APP_AUTH, "Bearer" + " " + accessToken);
				IotCloudLog iotCloudLog = new IotCloudLog();
				iotCloudLog.setData(data1);
				iotCloudLog.setFromSite("telcom");
				iotCloudLog.setCreateTime(new Date());
				iotCloudLog.setImei(ioTCloudDevice.getImei());
				iotCloudLog.setMac(ioTCloudDevice.getMac());
				iotCloudLog.setType(1);
				iotCloudLogService.save(iotCloudLog);
				HttpResponse responsePostAsynCmd = httpsUtil.doPostJson(urlPostAsynCmd, header, jsonRequest);
				String responseBody = httpsUtil.getHttpResponseBody(responsePostAsynCmd);
				log.warn("msg:{}", responseBody);
				return new Resp<>(JSONObject.parseObject(responseBody).getString("commandId"));
			}else if (ioTCloudDevice.getType() == 3) {//loraWan
				String  msg = sendAndRcvHttpPostBase("https://api.opg-iot.cn/thingpark/lrc/rest/downlink?DevEUI="+mac+"&FPort=1&Payload="+data1,"");
				IotCloudLog iotCloudLog = new IotCloudLog();
				iotCloudLog.setData(data1);
				iotCloudLog.setFromSite("lorawan");
				iotCloudLog.setImei("");
				iotCloudLog.setMac(ioTCloudDevice.getMac());
				iotCloudLog.setType(1);
				iotCloudLog.setCreateTime(new Date());
				iotCloudLogService.save(iotCloudLog);
				log.warn("msg:{}", msg);
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

    private static String sendAndRcvHttpPostBase(String url,String sendData){
        String result = "";
        BufferedReader in = null;
        DataOutputStream out = null;
        HttpsURLConnection httpsConn = null;
        HttpURLConnection httpConn = null;
        try{
            URL myURL = new URL(url);
                httpsConn =    (HttpsURLConnection) myURL.openConnection();
                TrustManager[] trustAllCerts = new TrustManager[]{
                        new X509TrustManager() {
                            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                return null;
                            }
                            public void checkClientTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                            public void checkServerTrusted(
                                    java.security.cert.X509Certificate[] certs, String authType) {
                            }
                        }
                };
                SSLContext sc = SSLContext.getInstance("TLS");
                sc.init(null, trustAllCerts, new java.security.SecureRandom());
                httpsConn.setSSLSocketFactory(sc.getSocketFactory());
                HostnameVerifier hv = new HostnameVerifier() {
                    @Override
                    public boolean verify(String urlHostName, SSLSession session) {
                        return true;
                    }
                };
                httpsConn.setHostnameVerifier(hv);

                httpsConn.setRequestProperty("Accept-Charset", "utf-8");
                httpsConn.setRequestProperty("User-Agent","java HttpsURLConnection");
                httpsConn.setRequestMethod("POST");
                httpsConn.setUseCaches(false);
                httpsConn.setDoInput(true);
                httpsConn.setInstanceFollowRedirects(true);
                if(sendData !=null){
                    httpsConn.setDoOutput(true);
                    // 获取URLConnection对象对应的输出流
                    out = new DataOutputStream(httpsConn.getOutputStream());
                    // 发送请求参数
                    out.write(sendData.getBytes("utf-8"));
                    // flush输出流的缓冲
                    out.flush();
                    out.close();
                }
                // 取得该连接的输入流，以读取响应内容
                in = new BufferedReader(new InputStreamReader(httpsConn.getInputStream(),"utf-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
                log.warn("=====反回结果====={}", line);
            }
        }catch(IOException e){
        	 log.error("error:{}",e);
            result = null;
        }catch(Exception e){
        	 log.error("error:{}",e);
            result = null;
        }finally{
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                }
            }
            if(httpConn!=null){
                httpConn.disconnect();
            }
            if(httpsConn!=null){
                httpsConn.disconnect();
            }
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        log.warn("data:{}",result);
        return result;
    }
    
    
    
	
}
