package database.models;

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

@Setter
@Getter
@Entity
@Table(name = "tbl_aliparking")
public class AliParking {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	@Column(name = "plate_index")
	private String plateIndex;
	@Column(name = "status")
	private String status;
	@Column(name = "platenumber")
	private String plateNumber;
	@Column(name = "plate_rect")
	private String plateRect;
	@Column(name = "plate_prob")
	private String plateProB;
	@Column(name = "plate_score")
	private String plateScore;
	@Column(name = "rect")
	private String rect;
	@Column(name = "type")
	private String type;
	@Column(name = "alarm_type")
	private String alarmType;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "pic_url")
	private String picUrl;
	@Column(name = "eventTime")
	private String eventTime;
	
	
}
