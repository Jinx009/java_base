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
    @Column(name = "mac")
    private String mac;
    @Column(name = "change_time")
    private String changeTime;
    @Column(name = "event_time")
    private String eventTime;
    @Column(name = "type")
    private Integer type;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "send_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date sendTime;
    @Column(name = "camera_index")
    private String cameraIndex;
    @Column(name = "send_status")
    private Integer sendStatus;
    @Column(name = "update_status")
    private Integer updateStatus;
    @Column(name = "file_path")
    private String filePath;
    @Column(name = "vedio_status")
    private Integer vedioStatus;
	
}
