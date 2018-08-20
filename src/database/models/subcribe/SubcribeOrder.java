package database.models.subcribe;

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
@Table(name="pro_subcribe_order")
public class SubcribeOrder {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "mobile_phone")
	private String mobilePhone;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "park_name")
	private String parkName;
	@Column(name = "plate_number")
	private String plateNumber;
	@Column(name = "start_hour")
	private Integer startHour;
	@Column(name = "end_hour")
	private Integer endHour;
	@Column(name = "park_id")
	private Integer parkId;
	@Column(name = "date_str")
	private String dateStr;
	
}
