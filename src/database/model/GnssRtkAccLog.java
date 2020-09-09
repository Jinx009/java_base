package database.model;

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
@Table(name = "tbl_gnss_rtk_acclog")
public class GnssRtkAccLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "flag")
	private Integer flag;
	@Column(name = "alarmtype")
	private Integer alarmtype;
	@Column(name = "alarmvalue")
	private String alarmvalue;
	@Column(name = "acc_x")
	private Double accX;
	@Column(name = "acc_y")
	private Double accY;
	@Column(name = "acc_z")
	private Double accZ;
	@Column(name = "angle_x")
	private Double angleX;
	@Column(name = "angle_y")
	private Double angleY;
	@Column(name = "angle_z")
	private Double angleZ;
	@Column(name = "acc_x_p2p")
	private Double accXP2p;
	@Column(name = "acc_y_p2p")
	private Double accYP2p;
	@Column(name = "acc_z_p2p")
	private Double accZP2p;
	@Column(name = "shock_strength")
	private Double shockStrength;
	
}
