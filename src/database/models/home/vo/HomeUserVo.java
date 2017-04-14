package database.models.home.vo;



import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HomeUserVo {

	private String userName;
	private Integer level;
	private String roleName;
	private Integer id;
	private String createTime;
	private Integer status;
	
}
