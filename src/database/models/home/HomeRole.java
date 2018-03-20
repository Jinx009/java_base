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

@Setter
@Getter
@Entity
@Table(name = "home_role")
public class HomeRole implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "status")
	private Integer status;
	@Column(name = "name")
	private String name;
	@Column(name = "level")
	private Integer level;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_trime")
	private Date createTime;
	@Column(name = "description")
	private String description;
	
}
