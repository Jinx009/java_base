package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyStore;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

@SuppressWarnings({ "deprecation", "unused" })
public class HttpsUtil extends DefaultHttpClient {

	private static final Logger log = LoggerFactory.getLogger(HttpsUtil.class);

	public final static String HTTPGET = "GET";
	public final static String HTTPPUT = "PUT";
	public final static String HTTPPOST = "POST";
	public final static String HTTPDELETE = "DELETE";
	public final static String HTTPACCEPT = "Accept";
	public final static String CONTENT_LENGTH = "Content-Length";
	public final static String CHARSET_UTF8 = "UTF-8";
	private static HttpClient httpClient;

	/**
	 * Two-Way Authentication In the two-way authentication, the client needs: 1
	 * Import your own certificate for server verification; 2 Import the CA
	 * certificate of the server, and use the CA certificate to verify the
	 * certificate sent by the server; 3 Set the domain name to not verify
	 * (Non-commercial IoT platform, no use domain name access.)
	 */
	public void initSSLConfigForTwoWay() throws Exception {
		String demo_base_Path = Constant.CERT_DIR;// "/Users/jinx/Documents/jobs/git_mine/base/base";//System.getProperty("user.dir");
		String selfcertpath = demo_base_Path + Constant.SELFCERTPATH;
		String trustcapath = demo_base_Path + Constant.TRUSTCAPATH;
		log.info("1:"+selfcertpath+"  2:"+trustcapath);

		KeyStore selfCert = KeyStore.getInstance("pkcs12");
		selfCert.load(new FileInputStream(selfcertpath), Constant.SELFCERTPWD.toCharArray());
		KeyManagerFactory kmf = KeyManagerFactory.getInstance("sunx509");
		kmf.init(selfCert, Constant.SELFCERTPWD.toCharArray());
		KeyStore caCert = KeyStore.getInstance("jks");
		caCert.load(new FileInputStream(trustcapath), Constant.TRUSTCAPWD.toCharArray());
		TrustManagerFactory tmf = TrustManagerFactory.getInstance("sunx509");
		tmf.init(caCert);
		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);
		SSLSocketFactory ssf = new SSLSocketFactory(sc, SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		ClientConnectionManager ccm = this.getConnectionManager();
		SchemeRegistry sr = ccm.getSchemeRegistry();
		sr.register(new Scheme("https", 8743, ssf));

		httpClient = new DefaultHttpClient(ccm);
		
	}


	public HttpResponse doPostJson(String url, Map<String, String> headerMap, String content) {
		HttpPost request = new HttpPost(url);
		addRequestHeader(request, headerMap);
		request.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
		return executeHttpRequest(request);
	}

	public StreamClosedHttpResponse doPostMultipartFile(String url, Map<String, String> headerMap, File file) {
		HttpPost request = new HttpPost(url);
		addRequestHeader(request, headerMap);

		FileBody fileBody = new FileBody(file);
		HttpEntity reqEntity = (HttpEntity) MultipartEntityBuilder.create().addPart("file", fileBody).build();
		request.setEntity(reqEntity);

		return (StreamClosedHttpResponse) executeHttpRequest(request);
	}

	public StreamClosedHttpResponse doPostJsonGetStatusLine(String url, Map<String, String> headerMap, String content) {
		HttpPost request = new HttpPost(url);
		addRequestHeader(request, headerMap);

		request.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));

		HttpResponse response = executeHttpRequest(request);
		if (null == response) {
			System.out.println("The response body is null.");
		}

		return (StreamClosedHttpResponse) response;
	}

	public StreamClosedHttpResponse doPostJsonGetStatusLine(String url, String content) {
		HttpPost request = new HttpPost(url);

		request.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));

		HttpResponse response = executeHttpRequest(request);
		if (null == response) {
			log.error("The response body is null.");
		}

		return (StreamClosedHttpResponse) response;
	}

	private List<NameValuePair> paramsConverter(Map<String, String> params) {
		List<NameValuePair> nvps = new LinkedList<NameValuePair>();
		Set<Map.Entry<String, String>> paramsSet = params.entrySet();
		for (Map.Entry<String, String> paramEntry : paramsSet) {
			nvps.add(new BasicNameValuePair(paramEntry.getKey(), paramEntry.getValue()));
		}

		return nvps;
	}

	public StreamClosedHttpResponse doPostFormUrlEncodedGetStatusLine(String url, Map<String, String> formParams)
			throws Exception {
		log.warn("{},{}",url,JSON.toJSONString(formParams));
		HttpPost request = new HttpPost(url);
		request.setEntity(new UrlEncodedFormEntity(paramsConverter(formParams)));
		HttpResponse response = executeHttpRequest(request);
		if (null == response) {
			log.error("The response body is null.");
			throw new Exception();
		}

		return (StreamClosedHttpResponse) response;
	}

	public HttpResponse doPutJson(String url, Map<String, String> headerMap, String content) {
		HttpPut request = new HttpPut(url);
		addRequestHeader(request, headerMap);

		request.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));

		return executeHttpRequest(request);
	}

	public HttpResponse doPut(String url, Map<String, String> headerMap) {
		HttpPut request = new HttpPut(url);
		addRequestHeader(request, headerMap);

		return executeHttpRequest(request);
	}

	public StreamClosedHttpResponse doPutJsonGetStatusLine(String url, Map<String, String> headerMap, String content) {
		HttpResponse response = doPutJson(url, headerMap, content);
		if (null == response) {
			log.error("The response body is null.");
		}

		return (StreamClosedHttpResponse) response;
	}

	public StreamClosedHttpResponse doPutGetStatusLine(String url, Map<String, String> headerMap) {
		HttpResponse response = doPut(url, headerMap);
		if (null == response) {
			log.error("The response body is null.");
		}

		return (StreamClosedHttpResponse) response;
	}

	public HttpResponse doGetWithParas(String url, Map<String, String> queryParams, Map<String, String> headerMap)
			throws Exception {
		HttpGet request = new HttpGet();
		addRequestHeader(request, headerMap);

		URIBuilder builder;
		try {
			builder = new URIBuilder(url);
		} catch (URISyntaxException e) {
			log.error("URISyntaxException: {}", e);
			throw new Exception(e);

		}

		if (queryParams != null && !queryParams.isEmpty()) {
			builder.setParameters(paramsConverter(queryParams));
		}
		request.setURI(builder.build());

		return executeHttpRequest(request);
	}

	public StreamClosedHttpResponse doGetWithParasGetStatusLine(String url, Map<String, String> queryParams,
			Map<String, String> headerMap) throws Exception {
		HttpResponse response = doGetWithParas(url, queryParams, headerMap);
		if (null == response) {
			log.error("The response body is null.");
		}

		return (StreamClosedHttpResponse) response;
	}

	public HttpResponse doDelete(String url, Map<String, String> headerMap) {
		HttpDelete request = new HttpDelete(url);
		addRequestHeader(request, headerMap);

		return executeHttpRequest(request);
	}

	public StreamClosedHttpResponse doDeleteGetStatusLine(String url, Map<String, String> headerMap) {
		HttpResponse response = doDelete(url, headerMap);
		if (null == response) {
			System.out.println("The response body is null.");
		}

		return (StreamClosedHttpResponse) response;
	}

	private static void addRequestHeader(HttpUriRequest request, Map<String, String> headerMap) {
		if (headerMap == null) {
			return;
		}

		for (String headerName : headerMap.keySet()) {
			if (CONTENT_LENGTH.equalsIgnoreCase(headerName)) {
				continue;
			}

			String headerValue = headerMap.get(headerName);
			request.addHeader(headerName, headerValue);
		}
	}

	private HttpResponse executeHttpRequest(HttpUriRequest request) {
		HttpResponse response = null;

		try {
			response = httpClient.execute(request);
		} catch (Exception e) {
			log.error("executeHttpRequest failed."+e.getMessage());
		} finally {
			try {
				response = new StreamClosedHttpResponse(response);
			} catch (IOException e) {
				log.error("IOException: " + e.getMessage());
			}
		}

		return response;
	}

	public String getHttpResponseBody(HttpResponse response) throws UnsupportedOperationException, IOException {
		if (response == null) {
			return null;
		}

		String body = null;

		if (response instanceof StreamClosedHttpResponse) {
			body = ((StreamClosedHttpResponse) response).getContent();
		} else {
			HttpEntity entity = response.getEntity();
			if (entity != null && entity.isStreaming()) {
				String encoding = entity.getContentEncoding() != null ? entity.getContentEncoding().getValue() : null;
				body = StreamUtil.inputStream2String(entity.getContent(), encoding);
			}
		}

		return body;
	}
}
