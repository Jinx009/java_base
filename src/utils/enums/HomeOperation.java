package utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 后来操作日志使用
 * @author jinx
 *
 */
@Getter
@AllArgsConstructor
public enum HomeOperation {

	LOGIN("登陆","LOGIN",1),
	EDIT_OPERATION("业务操作","EDIT_OPERATION",2),
	ADD_USER("新增用户","ADD_USER",3),
	DEL_USER("删除用户","DEL_USER",4),
	EDIT_USER("编辑用户","EDIT_USER",5),
	CHANGE_PWD("修改密码","CHANGE_PWD",6),
	OTHER("其他","OTHER",101);
	
	private String descption;
	private String name;
	private int code;
	
}
