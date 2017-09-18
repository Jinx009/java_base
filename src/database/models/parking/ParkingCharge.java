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
@Table(name="PARKING_CHARGE")
@Setter
@Getter
public class ParkingCharge {

	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "AREA_ID")
	private Integer areaId;
	@Column(name = "STREET_ID")
	private Integer streetId;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "DATE_TYPE")
	private Integer dateType;
	@Column(name = "SINGLE_DATE")
	private Date singleDate;
	@Column(name = "START_TIME")
	private Integer startTime;
	@Column(name = "END_TIME")
	private Integer endTime;
	@Column(name = "PRICE")
	private Double price;
	@Column(name = "FREE_TIME")
	private Integer freeTime;
	@Column(name = "SHOW_STATUS")
	private Integer showStatus;
	@Column(name = "AREA_NAME")
	private String areaName;
	@Column(name = "STREET_NAME")
	private String streetName;
	
	
}
