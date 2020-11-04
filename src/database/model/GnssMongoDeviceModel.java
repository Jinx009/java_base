package database.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GnssMongoDeviceModel {

	private String _id;
	private String basetag;
	private String tag;
	private long time;
	private int substatus;
	private int tagtype;
	private String topic;
	
}
