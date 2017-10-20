package database.models.pro;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * 管理token
 * @author Jinx
 *
 */
@Entity
@Table(name="pro_wechat_token")
@Setter
@Getter
public class ProWechatToken {

	@Id
	@Column(name = "id",unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "token_type")//类型1表示jsApi,2accessToken
	private Integer tokenType;
	@Column(name = "base_type")
	private Integer baseType;//1表示微信
	@Column(name = "token_value")
	private String tokenValue;
	@Column(name = "last_time")
	private String lastTime;
	@Column(name = "base_id")
	private String baseId;
}