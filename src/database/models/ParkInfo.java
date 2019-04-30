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
@Table(name = "tbl_park_info")
public class ParkInfo {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "sLocation")
    private String sLocation;//事情发生地点
    @Column(name = "sCameraName")
    private String sCameraName;
    @Column(name = "sCameraIndex")
    private String sCameraIndex;//监控点唯一标识
    @Column(name = "iVehicleEnterstate")
    private Integer iVehicleEnterstate;//车辆进入状态 0 -无效 1 –驶入 2 –驶出
    @Column(name = "sParkingid")
    private String sParkingid;//停车位标号
    @Column(name = "tEventTime")
    private String tEventTime;//事件发生时间
    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "sPlateNo")
    private String sPlateNo;
    @Column(name = "sendTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;
    @Column(name = "sPlateColor")
    private String sPlateColor;//车身颜色
    @Column(name = "sVehicleColor")
    private String sVehicleColor;//车身颜色
    @Column(name = "sWholeSenceUrl")
    private String sWholeSenceUrl;//全景图url
    @Column(name = "sFutrureUrl")
    private String sFutrureUrl;//特写图url
    @Column(name = "baseId")
    private Integer baseId;//对方数据库原始id
    @Column(name = "mac")
    private String mac;
    
}
