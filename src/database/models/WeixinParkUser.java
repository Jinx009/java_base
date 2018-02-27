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

@Entity
@Table(name="WEB_TOKEN_FACTORY")
@Setter
@Getter
public class WeixinParkUser {

	@Id
	@Column(name = "id",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "openid")
	private String openid;
	@Column(name = "plate_number")
	private String plateNumber;
	@Column(name = "mobole_phone")
	private String mobilePhone;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "headimg_url")
	private String headimgUrl;
	@Column(name = "nick_name")
	private String nickName;
	
}
