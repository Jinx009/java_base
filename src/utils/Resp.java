package utils;

import com.alibaba.fastjson.JSON;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Resp<T> {

	private String code;
	private String msg;
	private T data;
	
	public Resp(String code,String msg,T t){
		this.code = code;
		this.msg = msg;
		this.data = t;
	}
	
	public Resp(T t){
		this.code = RespData.OK_CODE;
		this.msg = RespData.OK_MSG;
		this.data = t;
	}
	
	public Resp(boolean b){
		if(b){
			this.code = RespData.OK_CODE;
			this.msg = RespData.OK_MSG;
		}else{
			this.code = RespData.ERROR_CODE;
			this.msg = RespData.ERROR_MSG;
		}
	}
	
	public String toString(){
		return JSON.toJSONString(this);
	}
	
}
