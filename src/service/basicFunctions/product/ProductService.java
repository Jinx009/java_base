package service.basicFunctions.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import utils.HttpUtils;

@Service
public class ProductService {
	
	private static final Logger log = LoggerFactory.getLogger(ProductService.class);

	public String getStatus(){
		try {
			String result = HttpUtils.get("http://111.231.132.234:8091/getdata,863703032244720");
			return result;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		
		return null;
	}
	
	public String setStatus(String status){
		try {
			String result = HttpUtils.get("http://111.231.132.234:8091/cmd,863703032244720,"+status);
			return result;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		
		return null;
	}
	
	public String setPM2_5(){
		try {
			String result = HttpUtils.get("http://111.231.132.234:8091/cmd,863703032244720,3000");
			return result;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		
		return null;
	}
	
	public static void main(String[] args) {
//		System.out.println(getStatus());
	}

	public String setLight() {
		try {
			String result = HttpUtils.get("http://111.231.132.234:8091/cmd,863703032244720,9999");
			return result;
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		
		return null;
	}
	
}
