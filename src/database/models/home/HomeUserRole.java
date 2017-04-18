package database.models.home;

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

@Setter
@Getter
@Entity
@Table(name = "HOME_USER_ROLE")
public class HomeUserRole {

	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "ROLE_ID")
	private Integer roleId;
	@Column(name = "USER_ID")
	private Integer userId;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
