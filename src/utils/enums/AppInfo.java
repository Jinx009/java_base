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
	WENZHOU_MODOU("wenzhou_modou","3d55af8d-e045-46e4-8fdb-75cbc7d700fb","温州魔豆"),
	PURUAN("puruan","f6b5c9b3-45ca-47da-8af6-4cdf29d28087","浦东软件园"),
	COMPANY_TEST("pos_taiyuan","7926bbf2-7ecc-43fb-885a-883bd1a8651b","公司和太原公用测试pos"),
	CHENGDU_TEST("chengdu_test","c1d1b78d-8fa3-411d-88bd-047edbcedc75","成都测试"),
	NB_1800M("wuhan_1800m_test","251ffebf-25eb-45b3-95fb-bc0d2a7d71fe","武汉1800M测试"),
	XINJIANG("zhanway_xinjiang","21c4c522-2d8c-4a9f-888b-57eb9ecb0c85","新疆")
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
