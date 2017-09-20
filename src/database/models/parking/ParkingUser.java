package database.models.parking;

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

@Entity
@Table(name="PARKING_USER")
@Setter
@Getter
public class ParkingUser {

	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;
	@Column(name = "ACCOUNT")
	private Double account;
	@Column(name = "TYPE")
	private Integer type;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "PARKING_TIMES")
	private Integer parkingTimes;
	@Column(name = "TOTAL_COST")
	private Double totalCost;
	@Column(name = "MONTH_NO")
	private String monthNo;
	@Column(name = "CREDIT")
	private Integer credit;
	@Column(name = "BLACK_LIST")
	private Integer blackList;
	@Column(name = "SHOW_STATUS")
	private Integer showStatus;
	@Column(name = "PLATE_NO")
	private String plateNo;
	
	
}
