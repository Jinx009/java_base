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
@Table(name = "HOME_ROLE")
public class HomeRole implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "NAME")
	private String name;
	@Column(name = "LEVEL")
	private Integer level;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME")
	private Date createTime;
	@Column(name = "DESCRIPTION")
	private String description;
	
}
