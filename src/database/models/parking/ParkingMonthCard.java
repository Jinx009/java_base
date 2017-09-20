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

/**
 * 会员卡
 * @author jinx
 *
 */
@Entity
@Table(name="PARKING_MONTH_CARD")
@Setter
@Getter
public class ParkingMonthCard {

	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "START_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;
	@Column(name = "END_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;
	@Column(name = "SHOW_STATUS")
	private Integer showStatus;
	@Column(name = "PRICE")
	private Double price;
	@Column(name = "OPERATOR")
	private Integer operator;
	@Column(name = "OPERATOR_NAME")
	private String operatorName;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "TYPE")
	private Integer type;
	@Column(name = "PLATE_NO")
	private String plateNo;
	@Column(name = "USER_ID")
	private Integer userId;
	@Column(name = "CREATOR")
	private Integer creator;
	@Column(name = "CREATOR_NAME")
	private String creatorName;
	@Column(name = "CARD_NO")
	private String cardNo;
	
}
