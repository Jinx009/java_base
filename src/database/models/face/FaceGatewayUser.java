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
@Table(name="face_gateway_user")
public class FaceGatewayUser {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "create_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;
	@Column(name = "update_time")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	@Column(name = "face_token")
	private String faceToken;
	@Column(name = "top")
	private String top;
	@Column(name = "left")
	private String left;
	@Column(name = "rotation")
	private String rotation;
	@Column(name = "width")
	private String width;
	@Column(name = "height")
	private String height;
	@Column(name = "group_id")
	private String group_id;
	@Column(name = "image_path")
	private String imagePath;
	@Column(name = "base64_content")
	private String base64Content;
	@Column(name = "name")
	private String name;
	@Column(name = "address")
	private String address;
	@Column(name = "mobile_phone")
	private String mobilePhone;
	
}
