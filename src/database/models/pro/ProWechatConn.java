package database.models.pro;

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
@Table(name = "pro_wechat_conn")
@Getter
@Setter
public class ProWechatConn {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "like_user")
	private Integer likeUserId;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "status")
	private int status;
	@Column(name = "meeting_date")
	private String meetingDate;
	@Column(name = "username")
	private String userName;
	@Column(name = "like_user_name")
	private String likeUserName;
	@Column(name = "user_sex")
	private String userSex;
	@Column(name = "like_user_sex")
	private String likeUserSex;
	@Column(name = "first")
	private Integer first;
	@Column(name = "user_pic")
	private String userPic;
	@Column(name = "likeUserPic")
	private String likeUserPic;
	
}
