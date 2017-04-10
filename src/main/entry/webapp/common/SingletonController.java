package main.entry.webapp.common;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/common")
@Scope(value = "prototype")
public class SingletonController {

	private static int INTEGER_ONE = 0;
	private int integerTwo = 0;
	
	/**
	 * 
	 * @return :INTEGER_ONE每次都+1,integerTwo=1 @Scope默认为singleton时两者皆会每次+1
	 */
	@RequestMapping(path = "/singleton")
	@ResponseBody
	public String singleton(){
		INTEGER_ONE++;
		integerTwo++;
		return "one:"+INTEGER_ONE+",two:"+integerTwo;
	}
	
}
