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
	XINJIANG("zhanway_xinjiang","21c4c522-2d8c-4a9f-888b-57eb9ecb0c85","新疆"),
	EVCARD("ev_card","bc6a9067-44a2-44dc-87b7-c08414350e10","EVCard"),
	SHENZHEN_OUFEIGUANG("oufeiguang","cde17d8e-dd84-4cb3-b5ee-8c24deb58261","深圳欧菲光"),
	CHAOZHOU_ZHIXIN("chaozhouzhixin","ee9c78ec-d226-474d-8c95-11da6d54456f","潮州智信"),
	BEAR_HUNTING("bearhunting","c344ea67-6652-42d2-8d1f-3cb33f9b18b2","周浦猎熊座"),
	SITI_HUAWEI("siti_huawei","b066dd31-48fb-4240-8893-305e71669658","华为产业技术研究院"),
	HANGZHOU_SHANGCHENG("hangzhou_shangcheng","38077fe8-f5ff-4acf-b3bd-d9c24eb9b2a8","杭州上城"),
	SHANGHAI_FIRE_CONTROL("shanghai_fire_control","d1d3337f-90f2-49cf-8c0c-c3ec4f105218","上海消防")
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
