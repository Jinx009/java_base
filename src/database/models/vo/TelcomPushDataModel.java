package database.models.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TelcomPushDataModel {

	private String deviceId;
	private String gatewayId;
	private String notifyType;
	private List<PushModel> services;
	private PushModel service;

	
}

