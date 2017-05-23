package database.models.home.vo;



import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeUserVo {

	private String userName;
	private Integer level;
	private String roleName;
	private Integer id;
	private Date createTime;
	private Integer status;
	private String realName;
	
}
