package database.models.project;

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
@Table(name="pro_gateway_access_control")
public class ProGatewayAccessControlLog {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "TenantCode")
	private String TenantCode;
	@Column(name = "OpenDoorMsgID")
	private String OpenDoorMsgID;
	@Column(name = "DeviceID")
	private Integer DeviceID;
	@Column(name = "DeviceLocalDirectory")
	private String DeviceLocalDirectory;
	@Column(name = "DeviceName")
	private String DeviceName;
	@Column(name = "PersonnelName")
	private String PersonnelName;
	@Column(name = "PersonnelID")
	private Integer PersonnelID;
	@Column(name = "CertificateCardNo")
	private String CertificateCardNo;
	@Column(name = "Mobile")
	private String Mobile;
	@Column(name = "OpenDoorPhotoList")
	private String OpenDoorPhotoList;
	@Column(name = "OpenDoorTime")
	private String OpenDoorTime;
	@Column(name = "CardSerialNumber")
	private String CardSerialNumber;
	@Column(name = "AccessWay")
	private Integer AccessWay;
	@Column(name = "Direction")
	private Integer Direction;
	@Column(name = "AccessResult")
	private String AccessResult;
	@Column(name = "PhotoHost")
	private String PhotoHost;
	@Column(name = "OpenDoorVideoList")
	private String OpenDoorVideoList ;
	@Column(name = "Timestamp")
	private String Timestamp ;
	@Column(name = "Sign")
	private String Sign ;
	
}
