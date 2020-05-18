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
@Table(name="pro_goods")
public class ProGoods {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "time")
	private String time;
	@Column(name = "date")
	private String date;
	@Column(name = "a_name")
	private String aName;
	@Column(name = "b_name")
	private String bName;
	@Column(name = "c_name")
	private String cName;
	@Column(name = "d_name")
	private String dName;
	@Column(name = "a_type")
	private Integer aType;
	@Column(name = "a_price")
	private Double aPrice;
	@Column(name = "b_type")
	private Integer bType;
	@Column(name = "b_price")
	private Double bPrice;
	@Column(name = "c_type")
	private Integer cType;
	@Column(name = "c_price")
	private Double cPrice;
	@Column(name = "d_type")
	private Integer dType;
	@Column(name = "d_price")
	private Double dPrice;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
