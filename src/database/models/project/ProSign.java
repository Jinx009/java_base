package database.models.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="PRO_SIGN")
public class ProSign {

	@Id
	@Column(unique=true, nullable=false)
	private Integer id;
	@Column(name = "key_id")
	private String keyId;
	@Column(name = "update_time")
	private String updateTime;
	@Column(name = "remark")
	private String remark;
	@Column(name = "session_id")
	private String sessionId;
	@Column(name = "type")
	private String type;
	@Column(name = "version")
	private String version;
	@Column(name = "user_id")
	private String userId;
	@Column(name = "content")
	private String content;
	@Column(name = "store_organ_id")
	private String storeOrganId;
	@Column(name = "company_organ_id")
	private String companyOrganId;
	@Column(name = "create_time")
	private String createTime;
	@Column(name = "member_id")
	private String memberId;
	
}
