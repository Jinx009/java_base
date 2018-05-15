package database.models.project.model;

import database.models.project.ProGatewayBed;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProGatewayBedModel {

	private ProGatewayBedSourceModel source;
	private String type;
	private ProGatewayBed data;
	
}
