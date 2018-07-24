package main.entry.webapp.data.gateway;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import utils.Resp;
import utils.weather.WeatherUtils;

@Controller
@RequestMapping(value = "/rest/weather")
public class GatewayWeatherDataController {

	private static final Logger log = LoggerFactory.getLogger(GatewayWeatherDataController.class);
	
	@RequestMapping(path = "/data")
	@ResponseBody
	public Resp<?> get(String cityName){
		Resp<?> resp = new Resp<>(false);
		try {
			return new Resp<>(WeatherUtils.get(cityName));
		} catch (Exception e) {
			log.error("error:{}",e);
		}
		return resp;
	}
	
}
