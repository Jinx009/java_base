package database.models.device;

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
@Table(name = "tbl_job")
public class DeviceJob {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	@Column(name = "target")
	private String target;
	@Column(name = "job_detail")
	private String jobDetail;
	@Column(name = "job_result")
	private String jobResult;
	@Column(name = "cmd_content")
	private String cmdContent;
	@Column(name = "cmd")
	private String cmd;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "rec_st")
	private Integer recSt;
	@Column(name = "status")
	private Integer status;
	
}
