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
@Table(name="pro_task_title")
public class ProResult {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "paper_id")
	private Integer paperId;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "language_type")
	private Integer languageType;
	@Column(name = "content")
	private String content;
	@Column(name = "status")
	private Integer status;
	@Column(name = "score")
	private Double score;
	@Column(name = "remark_a")
	private String remarkA;
	@Column(name = "remark_b")
	private String remarkB;
	@Column(name = "remark_c")
	private String remarkC;
	
}
