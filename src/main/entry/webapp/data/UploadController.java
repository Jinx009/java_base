package main.entry.webapp.data;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import utils.BaseConstant;
import utils.Resp;
import utils.wechat.WechatUtil;

@Controller
@RequestMapping(value = "/upload")
public class UploadController {
	
	private static final Logger logger = LoggerFactory.getLogger(UploadController.class);

	/**
	 * 上传图片
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/img")
	@ResponseBody
	public Resp<?> uploadImg(HttpServletRequest request,HttpServletResponse response,String wechatMediaId){
		Resp<?> resp = new Resp<>(BaseConstant.HTTP_ERROR_CODE,BaseConstant.HTTP_ERROR_MSG,"");
		try {
			String path = request.getSession().getServletContext().getRealPath("")+"/themes/data/"; 
			String url = WechatUtil.downToLocal(path, wechatMediaId);
			return new Resp<>(BaseConstant.HTTP_OK_CODE,BaseConstant.HTTP_OK_MSG,url);
		} catch (Exception e) {
			logger.error("error:{}",e);
		}
		return resp;
	}
	
	
}
