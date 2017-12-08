package database.models.device.vo;

import org.springframework.beans.BeanUtils;

import database.models.device.DeviceRouter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceRouterVo extends DeviceRouter{

	private String locationName;
	
	public static DeviceRouterVo instance(DeviceRouter protocol) {
		DeviceRouterVo model = new DeviceRouterVo();
		BeanUtils.copyProperties(protocol, model);
		return model;
    }

	public static DeviceRouter trans(DeviceRouterVo model) {
		DeviceRouter d = new DeviceRouter();
		BeanUtils.copyProperties(model, d);
		return d;
	}
	
}
