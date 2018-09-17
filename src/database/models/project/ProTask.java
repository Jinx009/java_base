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
	@Column(name = "no_id")
	private String noId;
	@Column(name = "name")
	private String name;
	@Column(name = "dep")
	private String dep;
	@Column(name = "description")
	private String description;
	@Column(name = "flight")
	private String flight;
	@Column(name = "pick_time")
	private String pickTime;
	@Column(name = "picked_time")
	private String pickedTime;
	@Column(name = "mail_time")
	private String mailTime;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "driver_mobile")
	private String driverMobile;
	@Column(name = "status")
	private Integer status;
	@Column(name = "driver_name")
	private String driverName;
	@Column(name = "task_title")
	private String taskTitle;
	@Column(name = "date_str")
	private String dateStr;
	@Column(name = "task_title_id")
	private Integer taskTitleId;
	@Column(name = "show_status")
	private Integer showStatus;
	
	
}
