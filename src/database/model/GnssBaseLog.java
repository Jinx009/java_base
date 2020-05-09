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
@Table(name = "tbl_gnss_base_log")
public class GnssBaseLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "mac")
	private String mac;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "base_x")
	private Double baseX;
	@Column(name = "base_y")
	private Double baseY;
	@Column(name = "base_z")
	private Double baseZ;
	@Column(name = "base_lat")
	private Double baseLat;
	@Column(name = "base_lng")
	private Double baseLng;
	@Column(name = "base_height")
	private Double baseHeight;
	
}
