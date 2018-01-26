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
@Table(name="pro_user")
public class ProUser {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "mobile_phone")
	private String mobilePhone;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "pwd")
	private String pwd;
	@Column(name = "current_points")
	private Integer currentPoints;
	@Column(name = "real_name")
	private String realName;
	@Column(name = "nick_name")
	private String nickName;
	@Column(name = "openid")
	private String openid;
	@Column(name = "address")
	private String address;
	@Column(name = "pic_path")
	private String picPath;
	
}