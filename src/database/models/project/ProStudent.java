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
@Table(name="pro_student")
public class ProStudent {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "mobile_phone")
	private String mobilePhone;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "name")
	private String name;
	@Column(name = "level")
	private Integer level;
	@Column(name = "description")
	private String desc;
	@Column(name = "remark_a")
	private String remarkA;
	@Column(name = "remark_b")
	private String remarkB;
	@Column(name = "remark_c")
	private String remarkC;
	@Column(name = "pwd")
	private String pwd;
	
}
