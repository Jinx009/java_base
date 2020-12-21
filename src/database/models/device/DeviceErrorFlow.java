package database.models.device;

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
@Table(name = "tbl_error_flow")
public class DeviceErrorFlow {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "parent_mac")
	private String parentMac;
	@Column(name = "error_bitmap")
	private String errorBitMap;
	@Column(name = "serial_no")
	private String serialNo;
	@Column(name = "log_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date logTime;
	@Column(name = "mac")
	private String mac;
	@Column(name = "type")
	private Integer type;
	@Column(name = "rec_st")
	private Integer recSt;
	@Column(name = "status")
	private Integer status;
	@Column(name = "area_id")
	private Integer areaId;
	
}
