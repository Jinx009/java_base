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
@Table(name="vedio_task")
@Setter
@Getter
public class VedioTask {

	@Id
	@Column(name = "id",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "time")
	private Long time;
	@Column(name = "name")
	private String name;
	@Column(name = "area_id")
	private Integer areaId;
	@Column(name = "area_name")
	private String areaName;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "status")
	private Integer status; //0 转换图片中 1 图片转换完成 2压缩完成 3等待解析 4完成
	@Column(name = "vedio_start")
	private String vedioStart;
	@Column(name = "result")
	private String result;
	@Column(name = "size")
	private String size;
	@Column(name = "dir_path")
	private String dirPath;
	@Column(name = "num")
	private Integer num;
	
}
