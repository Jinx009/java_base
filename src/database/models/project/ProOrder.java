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
@Table(name="pro_order")
public class ProOrder {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "mobile_phone")
	private String mobilePhone;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "name")
	private String name;
	@Column(name = "user_id")
	private Integer userId;
	@Column(name = "user_type")
	private String userType;
	@Column(name = "type")
	private Integer type;
	@Column(name = "order_time")
	private String orderTime;
	@Column(name = "status")
	private Integer status;
	@Column(name = "order_date")
	private String orderDate;
	@Column(name = "num")
	private Integer num;
	@Column(name = "real_num")
	private Integer realNum;
	@Column(name = "money")
	private Double money;
	@Column(name = "remark")
	private String remark;
	
}
