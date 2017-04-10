package database.models.home;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "HOME_RESOURCE_ROLE")
public class HomeResourceRole {

	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "CREATE_TIME")
	private Date createTime;
	@Column(name = "ROLE_ID")
	private Integer roleId;
	@Column(name = "RESOURCE_ID")
	private Integer resourceId;
	
}
