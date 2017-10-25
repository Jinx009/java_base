package utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppInfo {
	
	MA_AN_SHAN("maanshan","69f81c72-82c3-40c9-932b-0ee2d3928599","马鞍山路口"),
	WU_HAN("wuhan","86fd0aa0-1f5c-4374-9756-b26868acd7ae","武汉路口"),
	PARKING_DEMO("parking_demo","5144cde5-0c91-42bb-9f04-7aa5118fca16","停车测试"),
	SUZHOU_DEMO("suzhou","b0ae29fc-466a-4eda-8f34-fb7170dc4633","苏州试点"),
	NB_JINGAN("nb_jingan","22621b86-9bbe-45fb-bdbc-0d4b522c7fdf","静安NB"),
	COMPANY_TEST("pos_taiyuan","7926bbf2-7ecc-43fb-885a-883bd1a8651b","公司和太原公用测试pos")
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
