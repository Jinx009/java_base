package database.models.project;


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
 * 
 * @author Jinx
 *
 */
@Getter
@Setter
@Entity
@Table(name="PRO_TOKEN")
public class ProToken {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
    @Column(name = "BASE_ID")
    private String baseId;//mac地址或者商户appId
    @Column(name = "TOKEN")
    private String token;//当前token
    @Column(name = "TIME_STAMP")
    private long timestamp;//失效时间戳
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_TIME")
    private Date createTime;//创建时间

}
