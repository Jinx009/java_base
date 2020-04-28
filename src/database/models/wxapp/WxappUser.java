package database.models.wxapp;

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
@Table(name="pro_wxapp_user")
@Setter
@Getter
public class WxappUser {

	@Id
	@Column(name = "id",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "openid")
	private String openid;
	@Column(name = "status")
	private Integer status;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
   	private Date createTime;
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
   	private Date updateTime;
	
}
