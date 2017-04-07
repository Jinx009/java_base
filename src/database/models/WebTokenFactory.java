package database.models;


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
@Table(name="web_token_factory")
@Setter
@Getter
public class WebTokenFactory {

	@Id
	@Column(unique=true, nullable=false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "TOKEN_TYPE")//类型1表示jsApi,2accessToken
	private Integer tokenType;
	@Column(name = "BASE_TYPE")
	private Integer baseType;//1表示微信
	@Column(name = "TOKEN_VALUE")
	private String tokenValue;
	@Column(name = "LAST_TIME")
	private String lastTime;
	@Column(name = "BASE_ID")
	private String baseId;
}
