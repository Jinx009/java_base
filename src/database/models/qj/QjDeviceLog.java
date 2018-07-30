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
@Table(name="pro_qj_devicelog")
public class QjDeviceLog {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    @Column(name = "type")
    private String type;
    @Column(name = "sn_value")
    private String snValue;
    @Column(name = "x_type")
    private Integer xType;
    @Column(name = "voltage")
    private String voltage;
    @Column(name = "y_type")
    private Integer yType;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "base_x")
    private String baseX;
    @Column(name = "base_y")
    private String baseY;
	
}
