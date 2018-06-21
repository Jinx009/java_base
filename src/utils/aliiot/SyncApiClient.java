package utils.aliiot;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.alibaba.cloudapi.sdk.core.BaseApiClient;
import com.alibaba.cloudapi.sdk.core.BaseApiClientBuilder;
import com.alibaba.cloudapi.sdk.core.annotation.NotThreadSafe;
import com.alibaba.cloudapi.sdk.core.annotation.ThreadSafe;
import com.alibaba.cloudapi.sdk.core.enums.Method;
import com.alibaba.cloudapi.sdk.core.enums.Scheme;
import com.alibaba.cloudapi.sdk.core.model.ApiRequest;
import com.alibaba.cloudapi.sdk.core.model.ApiResponse;
import com.alibaba.cloudapi.sdk.core.model.BuilderParams;
import com.alibaba.fastjson.JSONObject;

@ThreadSafe
public class SyncApiClient extends BaseApiClient {

	private SyncApiClient(BuilderParams builderParams) {
		super(builderParams);
	}

	@NotThreadSafe
	public static class Builder extends BaseApiClientBuilder<Builder, SyncApiClient> {

		@Override
		protected SyncApiClient build(BuilderParams params) {
			return new SyncApiClient(params);
		}
	}

	public static Builder newBuilder() {
		return new Builder();
	}

	public static SyncApiClient getInstance() {
		return getApiClassInstance(SyncApiClient.class);
	}

	public ApiResponse postBody(String host, String path, IoTApiRequest request, boolean isHttps,
			Map<String, String> headers) throws UnsupportedEncodingException {
		byte[] body = JSONObject.toJSONString(request).getBytes("UTF-8");
		ApiRequest apiRequest = new ApiRequest(isHttps ? Scheme.HTTPS : Scheme.HTTP, Method.POST_BODY, host, path,
				body);
		if (null != headers && headers.size() > 0) {
			for (Map.Entry<String, String> header : headers.entrySet()) {
				apiRequest.getHeaders().put(header.getKey(), header.getValue());
			}
		}
		return syncInvoke(apiRequest);
	}

	public ApiResponse postBody(String host, String path, IoTApiRequest request, boolean isHttps)
			throws UnsupportedEncodingException {

		return postBody(host, path, request, isHttps, null);
	}

	public ApiResponse postBody(String host, String path, IoTApiRequest request) throws UnsupportedEncodingException {
		return postBody(host, path, request, false, null);
	}

}
