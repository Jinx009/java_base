package database.models.business;

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
@Table(name = "tbl_member")
public class BusinessMember {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "login_name")
    private String loginName;
    @Column(name = "password")
    private String password;
    @Column(name = "login_type")
    private Integer loginType;
    @Column(name = "member_number")
    private String memberNumber;
    @Column(name = "register_time")
    private Date registerTime;
    @Column(name = "expired_time")
    private Date expiredTime;
    @Column(name = "current_points")
    private Integer currentPoints;
    @Column(name = "last_access_time")
    private Date lastAccessTime;
    @Column(name = "service_open_rules")
    private String serviceOpenRules;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "address")
    private String address;
    @Column(name = "real_name")
    private String realName;
    @Column(name = "gender")
    private Integer gender;
    @Column(name = "email")
    private String email;
    @Column(name = "identity_card")
    private String identityCard;
    @Column(name = "weixin_id")
    private String weixinId;
    @Column(name = "plate_number")
    private String plateNumber;
    @Column(name = "plate_number2")
    private String plateNumber2;
    @Column(name = "plate_number_abbr")
    private String plateNumberAbbr;
    @Column(name = "plate_number_abbr2")
    private String plateNumberAbbr2;
    @Column(name = "card_type")
    private Integer cardType;
    @Column(name = "rec_st")
    private int recSt = 1;
    @Column(name = "create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Column(name = "birthday")
    private Date birthday;
	
}
