package database.models.face;

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
@Table(name="face_gateway_compare")
public class FaceGatewayCompare {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "face_token")
	private String faceToken;
	@Column(name = "score")
	private String score;
	@Column(name = "name")
	private String name;
	@Column(name = "address")
	private String address;
	@Column(name = "base64_content")
	private String base64Content;
	@Column(name = "user_img_path")
	private String userImgPath;
	@Column(name = "compare_img_path")
	private String compareImgPath;
	
}
