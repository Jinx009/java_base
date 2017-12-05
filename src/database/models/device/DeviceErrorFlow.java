package database.models.device;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_error_flow")
public class DeviceErrorFlow {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	@Column(name = "create_time")
	private Date createTime;
	
}
