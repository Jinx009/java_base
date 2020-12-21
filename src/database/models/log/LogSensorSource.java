package database.models.log;

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
@Table(name = "tbl_sensor_sourcelog")
public class LogSensorSource  {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "nb")
	private Long nb;//NB发送条数
	@Column(name = "ws")
	private Long ws;//2530发送条数
	@Column(name = "rt")
	private Long rt;//雷达检测次数（覆水+车检）
	@Column(name = "wt")
	private Long wt;//磁场波动时间，单位s
	@Column(name = "ut")
	private Long ut;//升级次数
	@Column(name = "mac")
	private String mac;//升级次数
	
	
}
