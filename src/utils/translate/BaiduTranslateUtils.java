package utils.translate;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import common.helper.MD5Util;
import utils.HttpUtils;

public class BaiduTranslateUtils {
	
	private static final String APP_ID = "20180821000196942";
	private static final String APP_SECRET = "gzLAd6_lR9ZykvsX7Clb";
	

	private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";

    public static String getTransResult(String query, String from, String to) {
        Map<String, String> params = buildParams(query, from, to);
        return HttpUtils.sendPost(TRANS_API_HOST, getUrlWithQueryString(params));
    }

    private static Map<String, String> buildParams(String query, String from, String to) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", APP_ID);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = APP_ID + query + salt + APP_SECRET; // 加密前的原文
        params.put("sign", MD5Util.md5(src));

        return params;
    }
    
    public static String getUrlWithQueryString( Map<String, String> params) {
        StringBuilder builder = new StringBuilder("");
        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(value);

            i++;
        }

        return builder.toString();
    }

    protected static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


	
}
