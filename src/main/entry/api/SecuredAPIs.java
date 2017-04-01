package main.entry.api;


import org.springframework.stereotype.Controller;



@Controller
public class SecuredAPIs {
	
	//直线用户API对接方式的主入口类  这次全部是html所以api方式放弃了。

//	@Autowired
//	UserInfoService userInfoService;
//	
//	 @RequestMapping(value = "/securedAPI/opExtraUserAttributes") 
//	 public void opExtraUserAttibute(HttpServletResponse response,HttpServletRequest request) throws Exception{
//		 
//		 NbTokenPublisher nbTokenPublisher= (NbTokenPublisher)request.getAttribute("filterGetTokenPublisher");
//		 
//		 Map<String, Object> jsonMap = HttpWebIOHelper.servletInputStream2JsonMap(request);
//		 nbReturn nbRet = new nbReturn();
//		 if( jsonMap != null){
//			 String attributeCode = (String) jsonMap.get("attCode");
//			 String attributeValue = (String) jsonMap.get("attVal");
//			
//			 //token的有效性由filter来检验了，不需要再在API中检验，但要从token获取appID和userID
//			 
//			 //开始增删改查用户扩展信息
//			 //TODO:
//			 
////			 nbRet = userInfoService.operateExtraInfo( 	nbTokenPublisher.getApplicationId(),
////					 									nbTokenPublisher.getNbUser(),
////					 									attributeCode,
////					 									attributeValue,
////					 									OperationFlags.USER_EXTRA_ATTRIBUTE_ADD);
//				
//			}else{
//				nbRet.setError(nbReturn.ReturnCode.PARAMETER_PHARSE_ERROR);
//			}
//			
//			HttpWebIOHelper.printReturnJson(nbRet, response);
//	    }
	 
}
