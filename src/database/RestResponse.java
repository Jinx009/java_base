package database;

import lombok.Getter;
import lombok.Setter;

/**
 * Rest 返回结果
 * @author jinx
 *
 * @param <T>
 */
@Getter
@Setter
public class RestResponse<T> {

	private String respCode;
	private String msg;
	private T params;
	
	public RestResponse(){
		
	}
	
	public RestResponse(String respCode,String msg,T params){
		this.respCode = respCode;
		this.msg = msg;
		this.params = params;
	}
}
