package database.models.log;

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
@Table(name = "tbl_sensor_operationlog")
public class LogSensorStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "available")
    private Integer available;
    @Column(name = "change_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changeTime;
    @Column(name = "area_id")
    private Integer areaId;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "description")
    private String description;
    @Column(name = "mac")
    private String mac;
    @Column(name = "log_id")
    private String logId;
    @Column(name = "send_status")
    private Integer sendStatus;
    @Column(name = "fail_times")
    private Integer failTimes;
    @Column(name = "send_time")
    private Date sendTime;
    
    

    /**
     * 2019-04-23
     */
    @Column(name = "type")
    private Integer type;//数据来源1表示摄像头null表示地磁
    @Column(name = "status")
    private String status;
    @Column(name = "cameraId")
    private String cameraId;
    @Column(name = "cph")
    private String cph;
    @Column(name = "cp_color")
    private String cpColor;
    @Column(name = "pic_link")
    private String picLink;
    
    @Column(name = "mode")
    private String mode;
    @Column(name = "ms")
    private String ms;
    @Column(name = "dif")
    private String dif;
    @Column(name = "adif")
    private String adif;
    @Column(name = "ny")
    private String ny;
    @Column(name = "lq")
    private String lq;

}
