package main.entry.webapp.page.pro;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import common.helper.StringUtil;
import database.models.pro.ProWechatUser;
import main.entry.webapp.BaseController;
import service.basicFunctions.pro.ProWechatUserService;
import utils.BufferUtils;
import utils.wechat.WechatData;
import utils.wechat.WechatUtil;

@Controller
public class ProWechatPageController extends BaseController{
	
	private static final Logger logger = LoggerFactory.getLogger(ProWechatPageController.class);
	
	@Autowired
	private ProWechatUserService proWechatUserService;

	/**
	 * 首页
	 * @param request
	 * @param code
	 * @param id
	 * @param qKey
	 * @return
	 */
	@RequestMapping(value = "/index")
	public String index(HttpServletRequest request,String code,String id,String qKey){
		Integer reId = 0;
		Integer userId = 0;
		Integer status = 0;
		if(StringUtil.isNotBlank(id)){
			reId = Integer.valueOf(id);
		}
		logger.warn("data:{},{}",code,qKey);
		try {
			if(StringUtil.isNotBlank(code)){
				String openid = WechatUtil.getOauthOpenId(WechatData.APP_ID, WechatData.APP_SECRET,code);
				if(null!=openid){
					ProWechatUser proWechatUser = proWechatUserService.findByOpenId(openid);
					if(null!=proWechatUser){
						status = proWechatUser.getStatus();
						setSession(request, "name", proWechatUser.getRealName());
						setSession(request, "pic", proWechatUser.getPic1());
						request.setAttribute("name", proWechatUser.getRealName());
						request.setAttribute("pic",proWechatUser.getPic1());
					}else{
						proWechatUser = proWechatUserService.saveNew(openid,qKey);
					}
					status = proWechatUser.getStatus();
					logger.warn("wechat user status:{}",status);
					userId = proWechatUser.getId();
				}
			}
			setSession(request,"reId",reId);
			request.setAttribute("userId",userId);
			request.setAttribute("status",status);
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return "/index";
	}
	
	/**
	 * 中转页
	 * @return
	 */
	@RequestMapping(value = "/q")
	public String q(HttpServletRequest request,String key,String reId){
		if(reId==null){
			reId = "0";
		}
		String realUrl =  BufferUtils.add(WechatData.OAUTH_URL_ONE,WechatData.OAUTH_URL_TWO,"/index.html?qKey=",key,"%26id=",reId,WechatData.OAUTH_URL_THREE);
		return "redirect:"+realUrl;
	}
	
	
	@RequestMapping(value = "/front/p/ranking")
	public String ranking(HttpServletRequest request,String userId){
		request.setAttribute("userId",userId);
		return "/pro/ranking";
	}
	
	@RequestMapping(value = "/front/p/person")
	public String person(HttpServletRequest request,String userId,String id,String statusA){
		request.setAttribute("userId",userId);
		request.setAttribute("id",id);
		request.setAttribute("statusA",statusA);
		return "/pro/person";
	}
	
	@RequestMapping(value = "/front/p/listGo")
	public String listGo(HttpServletRequest request,String userId){
		request.setAttribute("userId",userId);
		return "/pro/go";
	}
	
	@RequestMapping(value = "/front/p/list")
	public String list(HttpServletRequest request,String userId){
		request.setAttribute("userId",userId);
		return "/pro/list";
	}
	
	@RequestMapping(value = "/front/p/go")
	public String go(HttpServletRequest request,String id,String userId){
		request.setAttribute("userId",userId);
		request.setAttribute("id",id);
		return "/pro/go";
	}
	
	@RequestMapping(value = "/front/p/select")
	public String select(HttpServletRequest request,String userId){
		getJsApi(request);
		Integer _userId = Integer.valueOf(userId);
		ProWechatUser proWechatUser = proWechatUserService.find(_userId);
		request.setAttribute("userId",userId);
		request.setAttribute("_url", BufferUtils.add(WechatData.OAUTH_URL_TWO,"/q.html?key=",proWechatUser.getQrcode(),"&reId=",String.valueOf(userId)));
		return "/pro/select";
	}
	
	@RequestMapping(value = "/front/p/info")
	public String info(HttpServletRequest request,String userId){
		getJsApi(request);
		request.setAttribute("userId",userId);
		return "/pro/info";
	}
}
