package utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	ERROR_1("4097", "接收机通讯异常", "联系供应商", true), 
	ERROR_2("8193", "中继器通讯异常", "联系供应商", true), 
	ERROR_3("8194", "太阳能板异常","联系供应商", true), 
	ERROR_4("8196", "太阳能充电不足", "调整太阳能板方向或安装位置", true), 
	ERROR_5("8200", "铅电池损坏", "联系供应商",true), 
	ERROR_6("8208", "锂电池电压低", "联系供应商", true), 
	ERROR_7("12289", "传感器电压低", "更换设备",true), 
	ERROR_8("12290", "传感器通讯异常", "更换设备", true), 
	ERROR_9("12292", "传感器磁传感器异常", "更换设备",true), 
	ERROR_10("12296", "车位状态异常", "更换设备", true), 
	ERROR_11("0", "异常恢复", "暂不予处理",true), 
	ERROR_12("4100", "接收机断电重启", "告知", true),;

	private String code; // 编码
	private String errorInfo; // 错误信息
	private String solution; // 解决方案
	private boolean isShow;

	/**
	 * 根据code查询
	 * 
	 * @param code
	 *            接口编码
	 * @return 接口编码对象
	 */
	public static ErrorCode getByCode(String code) {
		ErrorCode[] dc = ErrorCode.values();
		for (ErrorCode e : dc) {
			if (e.getCode().equals(code))
				return e;
		}
		return null;
	}

}
