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

@Setter
@Getter
@Entity
@Table(name = "tbl_points_usage_his")
public class BusinessPointsHis {

    public static final Integer ACTION_USE = 0;
    public static final Integer ACTION_ACQUIRE = 1;
    public static final Integer ACTION_REDUCE = 2;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "member_id")
    private Integer memberId;
    @Column(name = "action")
    private Integer action;
    @Column(name = "quantity")
    private Integer quantity;
    @Column(name = "use_time")
    private Date useTime;
    @Column(name = "acquire_time")
    private Date acquireTime;
    @Column(name = "operator")
    private Integer operator;
    @Column(name = "bill_no")
    private String billNo;
    @Column(name = "mer_id")
    private Integer merId;
    @Column(name = "remarks")
    private String remarks;
    @Column(name="rec_st")
    private Integer recSt = 1;
    @Column(name="create_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
}
