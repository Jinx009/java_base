package database.models.device;


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
@Table(name = "tbl_reperter")
public class DeviceRepeater {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "mac")
	private String mac;
	@Column(name = "area_id")
	private Integer areaId;
	@Column(name = "router_mac")
	private String routerMac;
	@Column(name = "rec_st")
	private Integer recSt;
	@Column(name = "area_name")
	private String areaName;
	
}
