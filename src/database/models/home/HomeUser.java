package database.models.home;

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
@Table(name = "home_user")
@Getter
@Setter
public class HomeUser implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "USER_NAME")
	private String userName;
	@Column(name = "PWD")
	private String pwd;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "REAL_NAME")
	private String realName;
	
}
