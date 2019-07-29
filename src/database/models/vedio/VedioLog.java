package database.models.vedio;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="vedio_log")
@Setter
@Getter
public class VedioLog {

	@Id
	@Column(name = "id",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "task_id")
	private Integer taskId;
	@Column(name = "vedio_time")
	private String vedioTime;
	@Column(name = "status")
	private Integer status;
	@Column(name = "car_number")
	private String carNumber;
	@Column(name = "cp_x")
	private String cp_x;
	@Column(name = "cp_y")
	private String cpY;
	@Column(name = "car_x")
	private String carX;
	@Column(name = "car_y")
	private String carY;
	
}
