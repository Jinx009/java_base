package database.models.device.vo;

import org.springframework.beans.BeanUtils;

import database.models.device.DeviceCrossSensor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeviceCrossSensorVo extends DeviceCrossSensor{

	private static final long serialVersionUID = 389020779779002593L;
	
	private String areaName;
	private String locationName;
	
	public static DeviceCrossSensorVo instance(DeviceCrossSensor protocol) {
		DeviceCrossSensorVo model = new DeviceCrossSensorVo();
		BeanUtils.copyProperties(protocol, model);
		return model;
    }

	public static DeviceCrossSensor trans(DeviceCrossSensorVo model) {
		DeviceCrossSensor deviceCrossSensor = new DeviceCrossSensor();
		BeanUtils.copyProperties(model, deviceCrossSensor);
		return deviceCrossSensor;
	}
	
}
