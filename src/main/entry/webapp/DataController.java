package main.entry.webapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.home.HomeUser;
import service.basicFunctions.home.HomeUserService;
import utils.StringUtil;
import utils.model.BaseConstant;
import utils.model.Resp;

@Controller
@RequestMapping(value = "/data")
public class DataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(DataController.class);

	@Autowired
	private HomeUserService homeUserService;

	@RequestMapping(path = "/login")
	@ResponseBody
	public Resp<?> login(String username, String pwd, HttpServletRequest request) {
		Resp<?> resp = new Resp<>(false);
		try {
			HomeUser homeUser = homeUserService.login(username, pwd);
			if (homeUser != null) {
				setSessionHomeUser(homeUser, request);
				return new Resp<>(true);
			} else {
				return new Resp<>(BaseConstant.NOT_VALIDATE_COE, BaseConstant.NOT_VALIDATE_MSG, null);
			}
		} catch (Exception e) {
			log.error("error:{]", e);
		}
		return resp;
	}
	

	@RequestMapping(path = "/loginOut")
	@ResponseBody
	public Resp<?> loginOut(String username, String pwd, HttpServletRequest request) {
		Resp<?> resp = new Resp<>(false);
		try {
			setSessionNull(request);
		} catch (Exception e) {
			log.error("error:{]", e);
		}
		return resp;
	}



	@RequestMapping(path = "/download/log")
	public void down(String fileName, HttpServletRequest request, HttpServletResponse response) {
		InputStream inputStream = null;
		try {
			String fullName = StringUtil.add(BaseConstant.DEVICE_LOG_FILE_PATH, fileName);
			File file = new File(fullName);
			if (!file.exists())
				return;

			response.setCharacterEncoding("utf-8");
			response.setContentType("multipart/form-data");
			response.setHeader("Content-Disposition", StringUtil.add("attachment;fileName=", fileName));

			inputStream = new FileInputStream(file);
			OutputStream os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = inputStream.read(b)) > 0) {
				os.write(b, 0, length);
			}
			inputStream.close();
		} catch (Exception e) {
			log.error("error:{]", e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				log.error("error:{}", e);
			} finally {
				inputStream = null;
			}
		}
	}

}
