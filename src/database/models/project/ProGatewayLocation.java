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
@Table(name="pro_gateway_location")
public class ProGatewayLocation{

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "location_id")
	private Integer locationId;
	@Column(name = "company_id")
	private Integer companyId;
	@Column(name = "name")
	private String name;
	@Column(name = "appid")
	private String appId;
	@Column(name = "address")
	private String address;
	
}