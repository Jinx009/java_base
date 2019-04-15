package database.models.qj;

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
@Table(name="pro_qj_device")
public class QjDevice {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    @Column(name = "type")
    private String type;
    @Column(name = "sn_value")
    private String snValue;
    @Column(name = "x_value")
    private String xValue;
    @Column(name = "x_type")
    private Integer xType;
    @Column(name = "y_value")
    private String yValue;
    @Column(name = "z_value")
    private String zValue;
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
    @Column(name = "mobile_phone")
    private String mobilePhone;
    @Column(name = "notice_type")
    private Integer noticeType;
    @Column(name = "company")
    private String company;
    @Column(name = "business_type")
    private Integer businessType;
    @Column(name = "doneType")
    private Integer doneType;
    @Column(name = "address")
    private String address;
    @Column(name = "longitude")
    private String longitude;
    @Column(name = "latitude")
    private String latitude;
	
}
