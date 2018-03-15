package database.models.project.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProParkPlaceModel {

//	  {
//	        "id": 57, 
//	        "createTime": "2018-03-15 09:54:57", 
//	        "updateTime": "2018-03-15 09:54:57", 
//	        "version": 0, 
//	        "parkPlaceId": "10459", 
//	        "code": "智信001", 
//	        "remark": null, 
//	        "magneticStripeId": "0000000000000001", 
//	        "storeOrganId": "10255", 
//	        "companyOrganId": "10254", 
//	        "status": "EMPTY", 
//	        "inParkProduct": null
//	      }
	
	private Integer id;
	private String createTime;
	private String updateTime;
	private Integer version;
	private String parkPlaceId;
	private String code;
	private String remark;
	private String magneticStripeId;
	private String storeOrganId;
	private String companyOrganId;
	private String status;
	private String inParkProduct;
	
}
