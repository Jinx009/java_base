package database.models.pro;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "pro_wechat_user")
@Getter
@Setter
public class ProWechatUser {

	@Id
	@Column(unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "nick_name")
	private String nickName;
	@Column(name = "head_img")
	private String headImg;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "login_time")
	private Date loginTime;
	@Column(name = "login_times")
	private Integer loginTimes;
	@Column(name = "age")
	private String age;
	@Column(name = "email")
	private String email;
	@Column(name = "status")
	private Integer status;
	@Column(name = "real_name")
	private String realName;
	@Column(name = "mobile_phone")
	private String mobilePhone;
	@Column(name = "job_info")
	private String jobInfo;
	@Column(name = "open_id")
	private String openid;
	@Column(name = "pic1")
	private String pic1;
	@Column(name = "pic2")
	private String pic2;
	@Column(name = "pic3")
	private String pic3;
	@Column(name = "sex")
	private String sex;
	@Column(name = "num")
	private String num;
	@Column(name = "address")
	private String address;
	@Column(name = "likes")
	private String likes;
	@Column(name = "request")
	private String request;
	@Column(name = "zodiac")
	private String zodiac;
	@Column(name = "edu")
	private String edu;
	@Column(name = "tag")
	private String tag;
	@Column(name = "danwei")
	private String danwei;
	@Column(name = "info")
	private String info;
	@Column(name = "wechat_account")
	private String wechatAccount;
	@Column(name = "star_num")
	private Integer starNum;
	@Column(name = "qrcode")
	private String qrcode;
	@Column(name = "re_name")//recommend
	private String reName;
	@Column(name = "re_id")
	private Integer reId;
	@Column(name = "be_re_num")
	private Integer beReNum;
	
}
