package database.models.device;

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
@Table(name = "tbl_bluetooth_log")
public class DeviceCard {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "uuid_value")
    private String uuid;
    @Column(name = "signal_value")
    private String signalValue;
    @Column(name = "report_time")
    private String reportTime;
    @Column(name = "status")
    private Integer status;
    @Column(name = "router_mac")
    private String routerMac;
    @Column(name = "mac")
    private String mac;
	
}
