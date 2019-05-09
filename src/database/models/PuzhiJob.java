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
@Table(name="pro_puzhijob")
public class PuzhiJob {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "featureUuid")
	private String featureUuid;
	@Column(name = "status")
	private String status;
	@Column(name = "featureDisplayName")
	private String featureDisplayName;
	@Column(name = "featureCtx")
	private String featureCtx;
	@Column(name = "createAt")
	private String createAt;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "mac")
	private String mac;
	@Column(name = "msgid")
	private String msgid;
	@Column(name = "telcom_task_id")
	private String telcomTaskId;
	@Column(name = "task_status")
	private Integer taskStatus;
	@Column(name = "cmd")
	private String cmd;
	
}
