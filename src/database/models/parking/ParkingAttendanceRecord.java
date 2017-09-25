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

/**
 * 考勤记录
 * @author jinx
 *
 */
@Entity
@Table(name="PARKING_ATTENDANCE_RECORD")
@Setter
@Getter
public class ParkingAttendanceRecord {

	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "TO_WORK_STATUS")
	private Integer toWorkStatus;
	@Column(name = "OFF_WORK_STATUS")
	private Integer offWorkStatus;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "TO_WORK_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date toWorkTime;
	@Column(name = "OFF_WORK_TIME")
	private Date offWorkTime;
	@Column(name = "WEEK_TIME")
	private String weekTime;
	@Column(name = "WORK_TIME")
	private Double workTime;
	@Column(name = "SHOW_STATUS")
	private Integer showStatus;
	@Column(name = "WORKER_ID")
	private Integer workerId;
	@Column(name = "WORKER_NAME")
	private String workerName;
	@Column(name = "DEVICE_ID")
	private Integer deviceId;
	
	
}
