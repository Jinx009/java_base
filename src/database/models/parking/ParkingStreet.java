package database.models.parking;

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
 * 停车街道管理
 * @author jinx
 *
 */

@Entity
@Table(name="PARKING_STREET")
@Setter
@Getter
public class ParkingStreet {
	
	@Id
	@Column(name = "ID",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "NAME")
	private String name;
	@Column(name = "DESCRIPTION")
	private String desc;
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.DATE)
	private Date createTime;
	@Column(name = "AREA_ID")
	private Integer areaId;
	@Column(name = "STATUS")
	private Integer status;
	
	
}
