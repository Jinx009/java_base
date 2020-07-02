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
@Table(name = "tbl_gnss_rtk_log")
public class GnssRtkLog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	@Column(name = "mongo_id")
	private String _id;
	@Column(name = "lat")
	private Double lat;
	@Column(name = "height")
	private Double height;
	@Column(name = "fixrate")
	private Double fixrate;
	@Column(name = "satellites")
	private Integer satellites;
	@Column(name = "rovertag")
	private String rovertag;
	@Column(name = "datetime")
	private String datetime;
	@Column(name = "east")
	private Double east;
	@Column(name = "north")
	private Double north;
	@Column(name = "quality")
	private Integer quality;
	@Column(name = "updatetime")
	private String updatetime;
	@Column(name = "window")
	private Integer window;
	@Column(name = "basetag")
	private String basetag;
	@Column(name = "coverrate")
	private Double coverrate;
	@Column(name = "lng")
	private Double lng;
	@Column(name = "up")
	private Double up;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
