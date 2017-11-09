package database.models.job;

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
@Table(name="COMMON_JOB")
@Setter
@Getter
public class CommonJob {

	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "CMD")
	private String cmd;
	@Column(name = "TARGET")
	private String target;
	@Column(name = "JOB_DETAIL")
	private String jobDetail;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "FINISH_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishTime;
	@Column(name = "CREATE_TIME")
	private Date createTime;
	@Column(name = "BASE_ID")
	private Integer baseId;
	@Column(name = "SERVICE_NAME")
	private String serviceName;
	
}
