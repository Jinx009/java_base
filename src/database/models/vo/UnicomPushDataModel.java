package database.models.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UnicomPushDataModel {

	private String serialNumber;
	private String timestamp;
	private String subscriptionId;
	private String resourcePath;
	private String value;
	
}
