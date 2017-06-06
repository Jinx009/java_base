package database.models.web.pro;

import java.io.Serializable;
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
@Table(name = "PRO_ACCOUNT")
@Getter
@Setter
public class ProAccount implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "OPENID")
	private String openid;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "NICK_NAME")
	private String nickName;
	@Column(name = "APP_ID")
	private Integer appId;
	@Column(name = "APP_NAME")
	private String appName;
	@Column(name = "HEAD_IMG")
	private String headImg;
	@Column(name = "PIC1")
	private String pic1;
	@Column(name = "PIC2")
	private String pic2;
	@Column(name = "PIC3")
	private String pic3;
	@Column(name = "JOB")
	private String job;
	@Column(name = "SEX")
	private String sex;
	@Column(name = "HIGHT")
	private String hight;
	@Column(name = "DESCRIPTION")
	private String desc;
	
}
