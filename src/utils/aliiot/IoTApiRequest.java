package utils.aliiot;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class IoTApiRequest {

	    private String id;
	    private String version = "1.0";
	    private Map<String, Object> request = new HashMap<>();
	    private Map<String, Object> params = new HashMap<>();

	    public IoTApiRequest() {
	        id = UUID.randomUUID().toString();
	    }

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getVersion() {
	        return version;
	    }

	    public void setVersion(String version) {
	        this.version = version;
	    }

	    public Map<String, Object> getRequest() {
	        return request;
	    }

	    public void setRequest(Map<String, Object> request) {
	        this.request = request;
	    }

	    public Map<String, Object> getParams() {
	        return params;
	    }

	    public void setParams(Map<String, Object> params) {
	        this.params = params;
	    }

	    public void setApiVer(String apiVer) {
	        request.put("apiVer", apiVer);
	    }

	    public void setIoTToken(String ioTToken) {
	        request.put("ioTToken", ioTToken);
	    }

	    public void putParam(String key, Object value) {
	        params.put(key, value);
	    }
	
}
