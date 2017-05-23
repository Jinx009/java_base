package service.common;

import javax.annotation.PostConstruct;

public abstract class AbstractBaseService {

	private String str = "test";
	
	@PostConstruct
	public void init(){
		str = "init str";
	}
	
	public String getStr(){
		return str;
	}
}
