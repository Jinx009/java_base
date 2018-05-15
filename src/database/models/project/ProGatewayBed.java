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
@Table(name="pro_gateway_bed")
public class ProGatewayBed {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "breath")
	private Integer breath;
	@Column(name = "state")
	private String state;
	@Column(name = "wet")
	private String wet;
	@Column(name = "heartbeat")
	private Integer heartbeat;
	@Column(name = "pos")
	private String pos;
	@Column(name = "ts")
	private String ts;
	@Column(name = "sn")
	private String sn;
	
}
