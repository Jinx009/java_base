package main.entry.webapp.data.pro;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.pro.ProQRCode;
import main.entry.webapp.BaseController;
import service.basicFunctions.pro.ProQRCodeService;
import utils.BaseConstant;
import utils.BufferUtils;
import utils.Resp;
import utils.wechat.WechatData;

@Controller
@RequestMapping(value = "/front/d")
public class ProQRCodeDataController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(ProQRCodeDataController.class);

	@Autowired
	private ProQRCodeService proQRCodeService;

	/**
	 * 获取当前所有现成二维码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/codeList")
	@ResponseBody
	public Resp<?> QRCodeList(HttpServletRequest request) {
		Resp<?> resp = new Resp<>(BaseConstant.HTTP_ERROR_CODE, BaseConstant.HTTP_ERROR_MSG, "");
		try {
			List<ProQRCode> list = proQRCodeService.findAll();
			logger.warn("data:{}", list);
			return new Resp<>(list);
		} catch (Exception e) {
			logger.error("error", e);
		}
		return resp;
	}

	/**
	 * 保存二维码
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/saveCode")
	@ResponseBody
	public Resp<?> saveQRCode(HttpServletRequest request, String key, String desc) {
		Resp<?> resp = new Resp<>(BaseConstant.HTTP_ERROR_CODE, BaseConstant.HTTP_ERROR_MSG, "");
		try {
			ProQRCode proQRCode = proQRCodeService.findByKey(key);
			if (proQRCode != null) {
				resp.setMsg("该关键字已经存在！");
			} else {
				String url = BufferUtils.add(WechatData.OAUTH_URL_TWO,"/q.html?key=",key);  
				String realUrl = BufferUtils.add(WechatData.OAUTH_URL_ONE,WechatData.OAUTH_URL_TWO,"/index.html?qKey=",key,WechatData.OAUTH_URL_THREE);
				proQRCode = new ProQRCode();
				proQRCode.setCreateTime(new Date());
				proQRCode.setKeyword(key);
				proQRCode.setRealUrl(realUrl);
				proQRCode.setUrl(url);
				proQRCode.setDescription(desc);
				proQRCodeService.save(proQRCode);
				return new Resp<>(true);
			}
		} catch (Exception e) {
			logger.error("error", e);
		}
		return resp;
	}

}
