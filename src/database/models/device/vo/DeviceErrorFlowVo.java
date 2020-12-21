package database.models.device.vo;

import database.models.device.DeviceErrorFlow;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class DeviceErrorFlowVo {

	private DeviceErrorFlow deviceErrorFlow;
	private String areaName;
	private String typeName;
	private String errorName;
	private String solution;
	
}
