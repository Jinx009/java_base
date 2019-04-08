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

/**
 * 录制视频
 * @author jinx
 *
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_vedio")
public class Vedio {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "street_id")
    private Integer streetId;
    @Column(name = "street_name")//街道名称
    private String streetName;
    @Column(name = "camera_id")
    private Integer cameraId;
    @Column(name = "camera_number")//相机编号
    private String cameraNumber;
    @Column(name = "park_number")//关联停车位
    private String parkNumber;
    @Column(name = "start_time")//开始时间
    private String startTime;
    @Column(name = "end_time")//结束时间
    private String endTime;
    @Column(name = "vedio_status")//录制状态
    private Integer vedioStatus;
    @Column(name = "vedio_url")//视频地址
    private String vedioUrl;
    @Column(name = "vedio_name")//视频名称
    private String vedioName;
    @Column(name = "gif_url")
    private String gifUrl;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    
	
}
