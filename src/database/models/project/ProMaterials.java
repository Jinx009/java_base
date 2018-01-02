package database.models.project;

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
@Table(name="pro_materials")
public class ProMaterials {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "type")
	private Integer type;
	@Column(name = "content")
	private String content;
	@Column(name = "order_num")
	private Integer orderNum;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "prop")
	private Integer prop;
	
}
