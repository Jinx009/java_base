package database.models.parking;

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
@Table(name="PARKING_PAY")
@Setter
@Getter
public class ParkingPay {

	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "APP_ID")
	private String appId;
	@Column(name = "APP_SECRET")
	private String appSecret;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "SHOW_STATUS")
	private Integer showStatus;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "LOGO_PATH")
	private String logoPath;
	@Column(name = "MERCHANT_NUM")
	private String merchantNum;
	
}
