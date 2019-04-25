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
 * 停车位
 * @author jinx
 *
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_parking_space")
public class ParkingSpace {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "street_id")
    private Integer streetId;
    @Column(name = "status")
    private Integer status;
    @Column(name = "street_name")
    private String streetName;
    @Column(name = "camera_id")
    private Integer cameraId;
    @Column(name = "camera_number")
    private String cameraNumber;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "happen_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date happenTime;
    @Column(name = "park_number")
    private String parkNumber;
    @Column(name = "mac")
    private String mac;
	
}
