package database.models.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChinaMobilePushDataModel {

	private String ds_id;
	private String dev_id;
	private Integer type;
	private String at;
	private String value;
	
}
