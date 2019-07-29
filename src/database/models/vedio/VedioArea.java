package database.models.vedio;

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
@Table(name="vedio_area")
@Setter
@Getter
public class VedioArea {

	@Id
	@Column(name = "id",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "name")
	private String name;
	@Column(name = "camera_index")
	private String cameraIndex;
	@Column(name = "pic_path")
	private String picPath;
	@Column(name = "x1")
	private String x1;
	@Column(name = "x2")
	private String x2;
	@Column(name = "x3")
	private String x3;
	@Column(name = "x4")
	private String x4;
	@Column(name = "y1")
	private String y1;
	@Column(name = "y2")
	private String y2;
	@Column(name = "y3")
	private String y3;
	@Column(name = "y4")
	private String y4;
	@Column(name = "width")
	private Integer width;
	@Column(name = "height")
	private Integer height;
	@Column(name = "b")
	private Integer b;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
