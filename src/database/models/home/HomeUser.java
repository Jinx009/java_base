package database.models.home;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 后台登陆用户
 * 
 * @author jinx
 *
 */
@Entity
@Table(name = "tbl_admin")
public class HomeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name="real_name")
    private String realName;
    @Column(name="mobile")
    private String mobile;
    @Column(name="rec_st")
    private int recSt;
    @Column(name="create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
	
}
