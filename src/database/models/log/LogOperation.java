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
public class LogOperation {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
//	1.023
//	1.026
//	1.027
//	1.035
//	2.100
//	2.101
//	2.102
//	2.103
//	2.104
//	2.110
//	2.200
//	2.206
//	2.208
//	2.209
//	2.20T
	@Column(name = "ver")
	private String ver;
	@Column(name = "type")
	private String type;
	@Column(name = "lng")
	private String lng;
	@Column(name = "lat")
	private String lat;
	@Column(name = "pic_url")
	private String picUrl;
	
}
