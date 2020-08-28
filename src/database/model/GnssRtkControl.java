package database.model;

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
@Table(name = "tbl_gnss_rtk_control")
public class GnssRtkControl {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "cmd")
	private String cmd;
	@Column(name = "status")
	private Integer status;
	@Column(name = "content")
	private String content;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	@Column(name = "mac")
	private String mac;
	@Column(name = "cmd_name")
	private String cmdName;
	@Column(name = "result")
	private Integer result;
	@Column(name = "result_str")
	private String resultStr;
	
}
