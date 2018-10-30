package utils;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


public class MofangSignUtils {

	private static final Logger log = LoggerFactory.getLogger(MofangSignUtils.class);

	public static String getDataString(Map<String, String> data) {
		if (data == null || data.isEmpty()) {
			return null;
		}
		Map<String, String> sortMap = new TreeMap<String, String>(new Comparator<String>() {
			@Override
			public int compare(String str1, String str2) {
				return str1.compareTo(str2);
			}
		});
		sortMap.putAll(data);
		StringBuilder result = new StringBuilder();
		for (Map.Entry<String, String> entry : sortMap.entrySet()) {
			result.append(entry.getValue());
		}
		return result.toString();
	}

	public static String encrypt(String secretKey, String message) {
		try {
			Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
			SecretKeySpec secret_key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
			sha256_HMAC.init(secret_key);
			String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(message.getBytes()));
			return hash;
		} catch (NoSuchAlgorithmException e) {
			log.error(e.getMessage(), e);
		} catch (InvalidKeyException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
	
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String,String>();
		String uuid = UUIDUtils.random();
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		map.put("path", "/core/device");
		map.put("X-POS-REQUEST-ID",uuid);
		map.put("X-POS-REQUEST-TIME", sdf.format(date));
		map.put("X-POS-ACCESS-KEY", HttpData.MOFANG_AK);
		map.put("companyOrganId","10001");
		String sign = MofangSignUtils.encrypt(HttpData.MOFANG_SK, MofangSignUtils.getDataString(map));
		map.put("X-POS-REQUEST-SIGN", sign);
		System.out.println(JSON.parseObject(HttpUtils.getMofangV2("/core/device?companyOrganId=10001",map)));
	}
	

}
