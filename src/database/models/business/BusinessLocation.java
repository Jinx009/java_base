package database.models.business;

import java.io.Serializable;
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
@Table(name = "tbl_location")
public class BusinessLocation implements Serializable{

	private static final long serialVersionUID = 7341878406954473019L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "description")
	private String description;
	@Column(name = "app_info_id")
	private Integer appInfoId;
	@Column(name = "app_info_desc")
	private String appInfoDesc;
	@Column(name = "notice_type")
	private Integer noticeType;
	@Column(name = "rec_st")
	private Integer recSt;
	@Column(name = "name")
	private String name;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	
}
