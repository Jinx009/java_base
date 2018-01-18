package database.models.project.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProOrderStatisticsModel {

	private Integer countAmount;
	private Double priceAmount;
	private Integer minuteAmount;
	
	public Double getPriceAmount() {
		if(this.priceAmount==null)
			return 0.00;
		return priceAmount;
	}
	
	
}
