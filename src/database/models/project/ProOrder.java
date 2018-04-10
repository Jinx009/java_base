package database.models.project;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="pro_order")
public class ProOrder {
	
	@Id
	@Column(unique=true, nullable=false)
	private Integer id;

//	{
//		"productId": "10051",
//		"parkPlaceId": "10001",
//		"orderId": null,
//		"updateTime": "2018-01-07 23:05:03",
//		"remark": null,
//		"sessionId": "USI_4281e6fd-1a9a-4d06-90cd-5c344d3d8ac6",
//		"plateNumber": "äº¬N123456",
//		"version": 1,
//		"storeOrganId": "10031",
//		"companyOrganId": "9999",
//		"createTime": "2018-01-07 23:53:03",
//		"notChargeReason": "test",
//		"price": 0.0900,
//		"id": 351,
//		"beginTime": 1515336683000,
//		"endTime": 1515340861000,
//		"realPrice": 0.0000,
//		"status": "PAYED"
//	}
	@Column(name = "product_id")
	private String productId;
	@Column(name = "park_place_id")
	private String parkPlaceId;
	@Column(name = "update_time")
	private String updateTime;
	@Column(name = "remark")
	private String remark;
	@Column(name = "session_id")
	private String sessionId;
	@Column(name = "plate_number")
	private String plateNumber;
	@Column(name = "version")
	private String version;
	@Column(name = "store_organ_id")
	private String storeOrganId;
	@Column(name = "company_organ_id")
	private String companyOrganId;
	@Column(name = "create_time")
	private String createTime;
	@Column(name = "not_charge_reason")
	private String notChargeReason;
	@Column(name = "price")
	private Double price;
	@Column(name = "begin_time")
	private Long beginTime;
	@Column(name = "end_time")
	private String endTime;
	@Column(name = "real_price")
	private Double realPrice;
	@Column(name = "status")
	private String status;
	
	
}
