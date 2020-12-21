package database.models.business.vo;

import org.springframework.beans.BeanUtils;

import database.models.business.BusinessArea;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessAreaVo extends BusinessArea{

	private String locationName;
	
	public static BusinessAreaVo instance(BusinessArea protocol) {
		BusinessAreaVo model = new BusinessAreaVo();
		BeanUtils.copyProperties(protocol, model);
		return model;
    }

	public static BusinessArea trans(BusinessAreaVo model) {
		BusinessArea businessArea = new BusinessArea();
		BeanUtils.copyProperties(model, businessArea);
		return businessArea;
	}
	
}
