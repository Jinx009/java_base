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
 * 摄像头
 * @author jinx
 *
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_camera_v")
public class Camera {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "camera_index")
    private String cameraIndex;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "vedio_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vedioTime;
	
}
