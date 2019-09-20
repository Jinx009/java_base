package database.models.wenshidu;

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
@Table(name="pro_wenshidu_device")
public class WenshiduDevice {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    @Column(name = "device_number")
    private String deviceNumber;
    @Column(name = "name")
    private String name;
    @Column(name = "data")
    private String data;
    @Column(name = "time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
    @Column(name = "device_id")
    private String deviceId;
    @Column(name = "on_line_state")
    private Integer onLineState;

	
	
}
