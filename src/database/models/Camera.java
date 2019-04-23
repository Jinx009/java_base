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
@Table(name = "tbl_camera")
public class Camera {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "device_number")
    private String deviceNumber;
    @Column(name = "street_id")
    private Integer streetId;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "park_number")
    private String parkNumber;
    @Column(name = "device_type")//设备型号
    private String deviceType;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "happen_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date happenTime;
	
}
