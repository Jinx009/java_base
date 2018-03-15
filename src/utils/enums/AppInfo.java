package utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppInfo {
	
	XINJIANG("zhanway_xinjiang","21c4c522-2d8c-4a9f-888b-57eb9ecb0c85","新疆"),
	CHAOZHOUZHIXIN("chaozhouzhixin","ee9c78ec-d226-474d-8c95-11da6d54456f","潮州智信"),
	;
	
	private String appId;
	private String secret;
	private String description;
	
    /**
     *  根据code查询
     * @param code 接口编码
     * @return 接口编码对象
     */
    public static AppInfo getByAppId(String appId) {
    	AppInfo[] appInfos = AppInfo.values();
        for (AppInfo appInfo : appInfos) {
            if (appInfo.getAppId().equals(appId))
                return appInfo;
        }
        return null;
    }
}
