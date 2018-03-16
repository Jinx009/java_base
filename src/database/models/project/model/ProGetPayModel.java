package database.models.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProGetPayModel {

	private String productId;
	private Integer num;
	
	public ProGetPayModel(Integer num,String productId){
		this.num = num;
		this.productId = productId;
	}
	
}
