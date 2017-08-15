package common.helper.tool.util;

import javax.servlet.http.HttpServletRequest;
/**
 * 用户客户端判断
 * 
 * @authorJinx
 *
 */
public final class AgentUtil
{
	/**
	 * 判断是否为微信浏览器
	 * @param request
	 * @return
	 */
	public static boolean judgeAgent(HttpServletRequest request)
	{
		String ua =request.getHeader("user-agent").toLowerCase();
		
		System.out.println(ua);
		
		if (ua.indexOf("micromessenger") > 0) 
		{
		       return true;
		}
		
		return false;
	}
}
