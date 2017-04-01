package main.entry.api;

import org.springframework.stereotype.Controller;



@Controller
public class OpenAPIs {
	
	//直线用户API对接方式的主入口类  这次全部是html所以api方式放弃了。

//	//private HttpWebIOHelper httpWebIOHelper = new HttpWebIOHelper();
//	
//	@Autowired  
//	private UserInfoService userInfoService;
//	@Autowired
//	private ApplicationsService applicationsService;
//    
//    @RequestMapping(value = "/openAPI/registerUser") 
//    public void registerNewUser(HttpServletResponse response,HttpServletRequest request) throws Exception{
//    	
//		Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
//		nbReturn nbRet = new nbReturn();
//		
//		if( jsonMap != null){
//			String username = (String) jsonMap.get("username");
//			String password = (String) jsonMap.get("password");
//			String mobile = (String) jsonMap.get("mobilePhone");
//			String email = (String) jsonMap.get("email");
//			String appID = (String) jsonMap.get("appID");
//			String appSignature = (String) jsonMap.get("appSignature");
//			String applyDateTime = (String) jsonMap.get("applyDateTime");
//			
//			//这里需要通过applyDateTime, appSignature 以及 appID 验证APPID的有效性
//			nbRet = applicationsService.checkSignature(appID, applyDateTime, appSignature);
//			
//			if( !nbRet.isSuccess() ){
//				//验证signature 失败了
//				HttpWebIOHelper.printReturnJson(nbRet, response);
//				return;
//			}
//			
//			//验证signature成功，开始注册用户
//			nbRet = userInfoService.RegisterUser(username, 
//												 password, 
//												 mobile,
//												 email,
//												 appID);
//			
//		}else{
//			nbRet.setError(nbReturn.ReturnCode.PARAMETER_PHARSE_ERROR);
//			
//		}
//
//		HttpWebIOHelper.printReturnJson(nbRet, response);
//    }
//    
//    @RequestMapping(value = "/openAPI/verifyUser") 
//    public void verifyUser(HttpServletResponse response,HttpServletRequest request) throws Exception{
//    	
//    	Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
//		nbReturn nbRet = new nbReturn();
//		
//		if( jsonMap != null){
//			String username = (String) jsonMap.get("username");
//			String password = (String) jsonMap.get("password");
//			String appID = (String) jsonMap.get("appID");
//			String appSignature = (String) jsonMap.get("appSignature");
//			String applyDateTime = (String) jsonMap.get("applyDateTime");
//			Long lifecycleSec = (Long) jsonMap.get("lifecycleSec");
//			String clientUuid = (String) jsonMap.get("clientUuid");
//			
//			//这里需要通过applyDateTime, appSignature 以及 appID 验证APPID的有效性
//			nbRet = applicationsService.checkSignature(appID, applyDateTime, appSignature);
//			
//			if( !nbRet.isSuccess() ){
//				//验证signature 失败了
//				HttpWebIOHelper.printReturnJson(nbRet, response);
//				return;
//			}
//			
//			//验证signature成功，开始验证用户
//			nbRet = userInfoService.verifyUser(	username, 
//												password, 
//												appID, 
//												clientUuid, 
//												lifecycleSec, 
//												true);
//			
//		}else{
//			nbRet.setError(nbReturn.ReturnCode.PARAMETER_PHARSE_ERROR);
//		}
//		
//		HttpWebIOHelper.printReturnJson(nbRet, response);
//    }
//    
//    @RequestMapping(value = "/openAPI/addConfigExtraUserAttribute") 
//    public void addExtraUserAttribute(HttpServletResponse response,HttpServletRequest request) throws Exception{
//    	configExtraUserAttibute(response, request, OperationFlags.USER_EXTRA_ATTRIBUTE_CONFIG_ADD);
//    }
//    
//    @RequestMapping(value = "/openAPI/removeConfigExtraUserAttribute") 
//    public void removeExtraUserAttribute(HttpServletResponse response,HttpServletRequest request) throws Exception{
//    	configExtraUserAttibute(response, request, OperationFlags.USER_EXTRA_ATTRIBUTE_CONFIG_REMOVE);
//    }
//    
//    @RequestMapping(value = "/openAPI/updateConfigExtraUserAttribute") 
//    public void updateExtraUserAttribute(HttpServletResponse response,HttpServletRequest request) throws Exception{
//    	configExtraUserAttibute(response, request, OperationFlags.USER_EXTRA_ATTRIBUTE_CONFIG_UPDATE);
//    }
//    
//    
//    
//    public void configExtraUserAttibute(HttpServletResponse response,HttpServletRequest request, OperationFlags opFlag) throws Exception{
//    	
//    	Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
//		nbReturn nbRet = new nbReturn();
//		
//		if( jsonMap != null){
//			String attributeCode = (String) jsonMap.get("attCode");
//			String attributeName = (String) jsonMap.get("attName");
//			String attributeDescription = (String) jsonMap.get("attDes");
//			String appID = (String) jsonMap.get("appId");
//			String appSignature = (String) jsonMap.get("appSignature");
//			String applyDateTime = (String) jsonMap.get("applyDateTime");
//			
//			//这里需要通过applyDateTime, appSignature 以及 appID 验证APPID的有效性
//			nbRet = applicationsService.checkSignature(appID, applyDateTime, appSignature);
//			
//			if( !nbRet.isSuccess() ){
//				//验证signature 失败了
//				HttpWebIOHelper.printReturnJson(nbRet, response);
//				return;
//			}
//			
//			//验证signature成功，开始验证用户
////			nbRet = userInfoService.configUserExtraAttributes( appID,
////															   attributeCode,
////															   attributeName,
////															   attributeDescription,
////															   opFlag);
//			
//		}else{
//			nbRet.setError(nbReturn.ReturnCode.PARAMETER_PHARSE_ERROR);
//		}
//		
//		HttpWebIOHelper.printReturnJson(nbRet, response);
//    }
    
    
}
