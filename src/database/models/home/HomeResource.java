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
	@Column(name = "id",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "uri")
	private String uri;
	@Column(name = "status")
	private Integer status;
	@Column(name = "description")
	private String description;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "parent_id")
	private Integer parentId;
	@Column(name = "type")
	private Integer type;
	
}
