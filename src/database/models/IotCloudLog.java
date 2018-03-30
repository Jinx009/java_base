package database.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name="pro_log")
public class IotCloudLog {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "imei")
	private String imei;
	@Column(name = "mac")
	private String mac;
	@Column(name = "data")
	private String data;
	@Column(name = "type")
	private int type;//0上行 1下行
	@Column(name = "from_site")
	private String fromSite;
	
	
}
