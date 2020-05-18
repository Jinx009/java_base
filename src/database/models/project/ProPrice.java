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
@Table(name="pro_price")
public class ProPrice {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "time")
	private String time;
	@Column(name = "a_name")
	private String aName;
	@Column(name = "a_price")
	private Double aPrice;
	@Column(name = "b_name")
	private String bName;
	@Column(name = "b_price")
	private Double bPrice;
	@Column(name = "c_name")
	private String cName;
	@Column(name = "c_price")
	private Double cPrice;
	@Column(name = "d_name")
	private String dName;
	@Column(name = "d_price")
	private Double dPrice;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "level")
	private Integer level;
	
}