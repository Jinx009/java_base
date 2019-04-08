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
 * 停车记录
 * @author jinx
 *
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_parking_record")
public class ParkingRecord {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "street_id")
    private Integer streetId;
    @Column(name = "status")
    private Integer status;
    @Column(name = "street_name")//街道名称
    private String streetName;
    @Column(name = "camera_id")
    private Integer cameraId;
    @Column(name = "camera_number")//相机编号
    private String cameraNumber;
    @Column(name = "park_number")//关联车位号
    private String parkNumber;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "happen_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date happenTime;
    @Column(name = "send_time")//发送时间
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;
    @Column(name = "send_status")//发送状态
    private Integer sendStatus;
    @Column(name = "pic_url")//首张合成图
    private String picUrl;
    @Column(name = "vedio_url")//小视频
    private String vedioUrl;
    @Column(name = "pic_next_url")//三分钟后合成图
    private String picNextUrl;
	
}
