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

/**
 * 街道
 * @author jinx
 *
 */
@Getter
@Setter
@Entity
@Table(name = "tbl_street")
public class Street {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "name")
    private String name;
    @Column(name = "parkTotal")
    private Integer parkTotal;
    @Column(name = "street_number")
    private String streetNumber;
    @Column(name = "address")
    private String address;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
	
}
