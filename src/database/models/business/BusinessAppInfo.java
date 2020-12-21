package database.models.business;

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
@Table(name = "tbl_app_info")
public class BusinessAppInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "app_id")
	private String appId;
	@Column(name = "app_secret")
	private String appSecret;
	@Column(name = "description")
	private String description;
	@Column(name = "rec_st")
	private Integer recSt;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "notice_url")
	private String noticeUrl;
	@Column(name = "path")
	private String path;
	
}
