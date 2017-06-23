package database.models.pro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 项目方蓝牙设备
 * @author jinx
 *
 */
@Entity
@Table(name = "PRO_BLUETOOTH_DEVICE")
@Getter
@Setter
public class ProBluetoothDevice implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "MAC")
	private String mac;
	@Column(name = "CREATE_TIME")
	private Date createTime;
	@Column(name = "CAR_NO")
	private String carNo;
	@Column(name = "UPDATE_TIME")
	private Date updateTime;
	@Column(name = "STATUS")
	private Integer status;
	@Column(name = "MOBILE_PHONE")
	private String mobilePhone;
	@Column(name = "UUID")
	private String uuid;
	
}
