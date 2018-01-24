package database.models.project;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="PRO_IOT_ORDER")
public class ProIoTOrder {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "mac")
	private String mac;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "start_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	@Column(name = "end_time")
	private Date endTime;
	@Column(name = "description")
	private String description;
	@Column(name = "start_uuid")
	private String startUuid;
	@Column(name = "end_uuid")
	private String endUuid;
	@Column(name = "log_id")
	private String logId;
	@Column(name = "price")
	private Double price;
	@Column(name = "real_price")
	private Double realPrice;
	@Column(name = "pay_time")
	private String payTime;
	@Column(name = "status")
	private String status;
	@Column(name = "remark")
	private String remark;
	@Column(name = "car_num")
	private String carNum;
	
}
