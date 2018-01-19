package database.models.project.model;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProRuleModel {

    private Integer period;
	private String amountOfMoney;
	private String storeOrganId;
	private String companyOrganId;
	private String amountOfMoneyForNotEnough;
	private Integer freeBeginTime;
	private Integer freeEndTime;
	private Date createTime;
	
}
