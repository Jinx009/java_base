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

/**
 * 导航
 * @author jinx
 *
 */
@Getter
@Setter
@Entity
@Table(name = "home_resource")
public class HomeResource implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "URI")
	private String uri;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "DESCRIPTION")
	private String description;
	@Column(name = "CREATE_TIME")
	private Date createTime;
	@Column(name = "PARENT_ID")
	private Integer parentId;
	@Column(name = "TYPE")
	private Integer type;
	@Column(name = "ICON")
	private String icon;
	
}
