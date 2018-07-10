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
@Table(name="pro_task")
public class ProTask {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "mobile_phone")
	private String mobilePhone;
	@Column(name = "dep")
	private String dep;
	@Column(name = "description")
	private String description;
	@Column(name = "name")
	private String name;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "number")
	private String number;
	@Column(name = "pick_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date pickTime;
	@Column(name = "picked_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date pickedTime;
	@Column(name = "flight")
	private String flight;
	@Column(name = "driver_mobile")
	private String driverMobile;
	@Column(name = "status")
	private Integer status;
	@Column(name = "driver_name")
	private String driverName;
	
	
	
}
