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
 * 发票管理
 * @author jinx
 *
 */
@Entity
@Table(name="PARKING_INVOICE")
@Setter
@Getter
public class ParkingInvoice {

	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "TYPE")
	private Integer type;
	@Column(name = "NAME")
	private String name;
	@Column(name = "ADDRESS")
	private String address;
	@Column(name = "BANK_NAME")
	private String bankName;
	@Column(name = "BANK_NO")
	private String bankNo;
	@Column(name = "ID_NO")
	private String idNo;
	@Column(name = "SHOW_STATUS")
	private Integer showStatus;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "SEND_TIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date sendTime;
	@Column(name = "EMAIL")
	private String email;
	
}
