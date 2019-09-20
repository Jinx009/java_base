package database.models.lf;

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
@Table(name="pro_lf_log")
public class LfLog {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    @Column(name = "SensorID")
    private String SensorID;
    @Column(name = "SensorType")
    private Integer SensorType;
    @Column(name = "data1")
    private String data1;
    @Column(name = "data2")
    private String data2;
    @Column(name = "data3")
    private String data3;
    @Column(name = "AcqTime")
    private Long AcqTime;
    @Column(name = "PushTime")
    private Long PushTime;
	
}
