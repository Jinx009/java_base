package database.models;

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
@Table(name = "tbl_vedio")
public class ParkingVedio {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "time")
	private Long time;
	@Column(name = "zip_name")
	private String zipName;
	@Column(name = "camera_index")
	 private String cameraIndex;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "finish_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date finishTime;
	@Column(name = "status")
	private Integer status; //0 新建 1 转换zip名称成功 2 下发录制 3 录制完成 4 截图完成 5 打包完成 6 发送解析 7 彻底完成
	@Column(name = "vedio_start")
	private String vedioStart;
	@Column(name = "vedio_end")
	private String vedioEnd;
	@Column(name = "dir_path")
	private String dirPath;
	@Column(name = "result")
	private String result;
	
}
