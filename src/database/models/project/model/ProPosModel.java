package database.models.project.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProPosModel {

	private String name;
	private String mac;
	private String model;
	private Date createTime;
	
}
