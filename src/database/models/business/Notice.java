package database.models.business;

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
@Table(name = "tbl_notice")
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(name = "title")
	private String title;
	@Column(name = "type")
	private String type;
	@Column(name = "content")
	private String content;
	@Column(name = "status")
	private String status;
	@Column(name = "create_time")
	private String createTime;
	@Column(name = "time")
	private String time;
	@Column(name = "autor")
	private String autor;
	
}
