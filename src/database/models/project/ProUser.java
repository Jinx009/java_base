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
@Table(name="PRO_USER")
public class ProUser {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "car_num")
	private String carNum;
	@Column(name = "card")
	private String card;
	@Column(name = "create_time")
	private Date createTime;
	@Column(name = "mobile_phone")
	private String mobilePhone;
	@Column(name = "name")
	private String name;
	@Column(name = "status")
	private Integer status;
	
}
