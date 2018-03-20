package database.models.home;

import java.io.Serializable;
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
@Table(name = "home_resource_role")
public class HomeResourceRole implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "role_id")
	private Integer roleId;
	@Column(name = "resource_id")
	private Integer resourceId;
	
}
