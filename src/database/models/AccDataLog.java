package database.models;

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
@Table(name="pro_acc_datalog")
public class AccDataLog {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    @Column(name = "type")
    private String type;
    @Column(name = "mac")
    private String mac;
    @Column(name = "x_type")
    private Integer xType;
    @Column(name = "voltage")
    private String voltage;
    @Column(name = "y_type")
    private Integer yType;
    @Column(name = "z_type")
    private Integer zType;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "base_x")
    private String baseX;
    @Column(name = "base_y")
    private String baseY;
    @Column(name = "base_z")
    private String baseZ;
    @Column(name = "base_acce_x")
    private String baseAcceX;
    @Column(name = "base_acce_y")
    private String baseAcceY;
    @Column(name = "base_acce_z")
    private String baseAcceZ;
    @Column(name = "acce_x_type")
    private Integer acceXType;
    @Column(name = "acce_y_type")
    private Integer acceYType;
    @Column(name = "acce_z_type")
    private Integer acceZType;
    @Column(name = "rsrp")
    private String rsrp;
    @Column(name = "rssi")
    private String rssi;
    @Column(name = "pci")
    private String pci;
    @Column(name = "snr")
    private String snr;
    @Column(name = "tem")
    private String tem;
    @Column(name = "hard_v")
    private String hardV;
    @Column(name = "soft_v")
    private String softV;
    @Column(name = "acc_x_max")
    private String accxMax;
    @Column(name = "acc_y_max")
    private String accyMax;
    @Column(name = "acc_z_max")
    private String acczMax;
    @Column(name = "acc_x_min")
    private String accxMin;
    @Column(name = "acc_y_min")
    private String accyMin;
    @Column(name = "acc_z_min")
    private String acczMin;
    @Column(name = "data_type")
    private Integer dataType;//0表示自身，1表示转发蜕变
	
}