package database.models.device;

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
@Table(name = "tbl_sensor_info")
public class DeviceSensorInfo {

		@Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer id;
	    @Column(name = "mac")
	    private String mac;
	    @Column(name = "address")
	    private String address;
	    @Column(name = "park_number")
	    private String parkNumber;
	    @Column(name = "create_time")
	    @Temporal(TemporalType.TIMESTAMP)
	    private Date createTime;
	
}
