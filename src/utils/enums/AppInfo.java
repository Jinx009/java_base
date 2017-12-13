package utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppInfo {
	
	XINJIANG("zhanway_xinjiang","21c4c522-2d8c-4a9f-888b-57eb9ecb0c85","新疆"),
	PURUAN("puruan","f6b5c9b3-45ca-47da-8af6-4cdf29d28087","浦东软件园"),
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
