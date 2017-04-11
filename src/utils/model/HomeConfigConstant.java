package utils.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpSession;

import database.models.home.HomeResource;


/**
 * 后台账户登录相关
 * @author jinx
 *
 */
public class HomeConfigConstant {

	public static Map<String,Object> resourceList = new HashMap<String,Object>();
	public static Map<String,String> sessionIdMap = new HashMap<String,String>();
	
	/**
	 * 判断用户是否有效
	 * @param sessionId
	 * @return
	 */
	public static boolean checkSession(String sessionId){
		 Set<Entry<String,String>> entrySet = sessionIdMap.entrySet();
         for (Entry<String,String> entry : entrySet) {
            if (sessionId.equals(entry)) {
                return true;
            }
         }
         return false;
	}
	
	/**
	 * 新的登陆成功账户放到map中
	 * @param adminName
	 * @param sessionId
	 */
	public static void putNewSession(String adminName,String sessionId){
		sessionIdMap.put(adminName, sessionId);
	}
	
	/**
	 * 存放菜单
	 * @param menus
	 * @param sessionId
	 */
	public static void putMenu(List<HomeResource> menus,String sessionId){
		resourceList.put("_menu"+sessionId,menus);
	}
	
	/**
	 * 获取菜单
	 * @param session
	 * @return
	 */
	public static Object getResourceBySession(HttpSession session){
		if(resourceList.containsKey("_menu"+session.getId())){
			return resourceList.get("_menu"+session.getId());
		}
		return null;
	}
	
}
