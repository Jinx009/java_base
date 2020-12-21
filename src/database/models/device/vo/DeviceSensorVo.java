package database.models.device.vo;

import org.springframework.beans.BeanUtils;

import database.models.device.DeviceSensor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceSensorVo extends DeviceSensor{
	
	
	private String areaName;
	private String locationName;

	public static DeviceSensorVo instance(DeviceSensor protocol) {
		DeviceSensorVo model = new DeviceSensorVo();
		BeanUtils.copyProperties(protocol, model);
		return model;
    }

	public static DeviceSensor trans(DeviceSensorVo model) {
		DeviceSensor deviceSensor = new DeviceSensor();
		BeanUtils.copyProperties(model, deviceSensor);
		return deviceSensor;
	}
	
}
