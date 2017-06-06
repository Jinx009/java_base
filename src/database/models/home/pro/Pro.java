package database.models.home.pro;

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
@Table(name = "PRO")
@Getter
@Setter
public class Pro implements Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "NAME")
	private String naem;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "START_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	@Column(name = "END_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	@Column(name = "PIC")
	private String pic;
	@Column(name = "DESCRIPTION")
	private String desc;
	@Column(name = "APP_ID")
	private Integer appId;
	@Column(name = "CREATE_USER")
	private String createUser;

}
