package database.models.project;

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
@Table(name="PRO_GATEWAY_AREA")
public class ProGatewayArea {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "area_id")
	private Integer areaId;
	@Column(name = "store_id")
	private Integer storeId;
	@Column(name = "name")
	private String name;
	@Column(name = "lat")
	private Double lat;
	@Column(name = "lng")
	private String lng;
	@Column(name = "address")
	private String address;
	
}
