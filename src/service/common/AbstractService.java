package service.common;

import org.springframework.stereotype.Service;

@Service
public class AbstractService extends AbstractBaseService{

	private String str = "str_01";
	
	public String getStr(){
		return super.getStr()+"this:"+str;
	}
	
}
