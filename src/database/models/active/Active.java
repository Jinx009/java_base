package database.models.active;

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
@Table(name = "ACTIVE_ACTIVE")
@Getter
@Setter
public class Active implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "KEYWORD")
	private String keyword;
	@Column(name = "DESCRIPTION")
	private String desc;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIME)
	private Date createTime;
	

}
