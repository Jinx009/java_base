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
@Table(name="pro_class_order")
public class ProClassOrder {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "mobilePhone")
	private String mobilePhone;
	@Column(name = "name")
	private String name;
	@Column(name = "class_name")
	private String className;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "class_id")
	private String classId;
	@Column(name = "class_time")
	private String classTime;
	@Column(name = "class_date")
	private String classDate;
	@Column(name = "status")
	private Integer status;
	
}
