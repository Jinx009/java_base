package database.models.guodong;

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
@Table(name="GUODONG_JOB")
@Setter
@Getter
public class GuodongJob {

	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "JOB_CONTENT")
	private String data;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "TIME_S")
	private String time_s;
	@Column(name = "DEV_EUI")
	private String devEui;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "task_id")
	private String taskId;
	
}
