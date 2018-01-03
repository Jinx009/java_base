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

@Getter
@Setter
@Entity
@Table(name="pro_book_post")
public class ProBookPost {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "mobile_phone")
	private String mobilePhone;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "post_num")
	private String postNum;
	@Column(name = "remark")
	private String remark;
	@Column(name = "points")
	private Integer points;
	@Column(name = "points_remark")
	private String pointsRemark;
	@Column(name = "status")
	private Integer status;
	@Column(name = "sell_points")
	private Integer sellPoints;
	
}
