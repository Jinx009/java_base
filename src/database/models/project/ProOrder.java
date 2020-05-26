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
	@Column(name = "openid")
	private String openid;
	@Column(name = "date")
	private String date;
	@Column(name = "time")
	private String time;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "name")
	private String name;
	@Column(name = "user_name")
	private String userName;
	@Column(name = "price")
	private Double price;
	@Column(name = "status")
	private Integer status;
	@Column(name = "type")
	private String type;
	@Column(name = "from_site")
	private Integer fromSite;
	@Column(name = "msg")
	private String msg;
	@Column(name = "prepay_id")
	private String prepayId;
	
}
