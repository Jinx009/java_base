package main.entry.webapp.data.project.home;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import common.helper.StringUtil;
import database.models.home.HomeUser;
import database.models.project.ProPaper;
import main.entry.webapp.BaseController;
import service.basicFunctions.project.ProPaperService;
import service.basicFunctions.project.ProResultService;
import utils.Resp;
import utils.translate.BaiduTranslateUtils;

@Controller
@RequestMapping(value = "/d/paper")
public class ProPaperDataController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(ProPaperDataController.class);

	@Autowired
	private ProPaperService proPaperService;
	@Autowired
	private ProResultService proResultService;

	/**
	 * 获取个人论文集合
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(path = "list")
	@ResponseBody
	public Resp<?> list(HttpServletRequest request) {
		Resp<?> resp = new Resp<>(false);
		try {
			HomeUser homeUser = getSessionHomeUser(request);
			return new Resp<>(proPaperService.findByUserId(homeUser.getId()));
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	/**
	 * 新建论文
	 * 
	 * @return
	 */
	@RequestMapping(path = "save")
	@ResponseBody
	public Resp<?> save(String title, String abstractContent, String filePath, HttpServletRequest req) {
		Resp<?> resp = new Resp<>(false);
		try {
			HomeUser homeUser = getSessionHomeUser(req);
			ProPaper proPaper = proPaperService.save(title, abstractContent, filePath, homeUser.getId());
			String content = readContent("/Users/jinx/Documents/jobs/git_mine/.metadata/.plugins/org.eclipse.wst.server.core/tmp0/wtpwebapps/java_base/" + filePath);
			proResultService.save(content, 1, proPaper.getId());
			String res = translate(content);
			if(StringUtil.isNotBlank(res)){ //翻译
				 proResultService.save(res,2,proPaper.getId());
			}
			return new Resp<>(true);
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

	private static String translate(String content){
		StringBuffer buffer = new StringBuffer();
		if (!"base file is not written by english or file is not avliable!".equals(content)) {
			content = content.replaceAll("\r|\n", "");
			String[] s = content.split("\\.");
			for (int i = 0; i < s.length; i++) {
				try {
					if (StringUtil.isNotBlank(s[i]) && !" ".equals(s[i])) {
						String result = BaiduTranslateUtils.getTransResult(f(s[i]), "auto", "zh");
						JSONObject jsonObject = JSONObject.parseObject(result);
						JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("trans_result"));
						JSONObject jsonObject2 = jsonArray.getJSONObject(0);
						buffer.append(jsonObject2.getString("dst"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
		return buffer.toString();
	}
	
	private static String readContent(String path) {
		try {
			if (path.endsWith(".doc")) {
				InputStream is = new FileInputStream(new File(path));
				WordExtractor ex = new WordExtractor(is);
				String text2003 = ex.getText();
				ex.close();
				return text2003;
			} else if (path.endsWith("docx")) {
				OPCPackage opcPackage = POIXMLDocument.openPackage(path);
				POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
				String text2007 = extractor.getText();
				extractor.close();
				return text2007;
			} else if (path.endsWith("txt")) {
				BufferedReader buf = new BufferedReader(new FileReader(path));
				StringBuffer sbuf = new StringBuffer();
				String line = null;
				while ((line = buf.readLine()) != null) {
					sbuf.append(line);
				}
				buf.close();
				return sbuf.toString();
			} else {
				return "base file is not written by english or file is not avliable!";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}



	public static String f(String s) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			builder.append(s.charAt(i));
			String t = builder.toString().replaceAll("\\s*", "").replaceAll(" ", "");
			if(StringUtil.isNotBlank(t)){
				s = s.substring(i, s.length());
				break;
			}
		}
		return s;
	}

	public static void main(String[] args) {
		String content = readContent("/Users/jinx/Downloads/1.docx");
		if (!"base file is not written by english or file is not avliable!".equals(content)) {
			StringBuffer buffer = new StringBuffer();
			content = content.replaceAll("\r|\n", "");
			String[] s = content.split("\\.");
			for (int i = 0; i < s.length; i++) {
				try {
					if (StringUtil.isNotBlank(s[i]) && !" ".equals(s[i])) {
						String result = BaiduTranslateUtils.getTransResult(f(s[i]), "auto", "zh");
						JSONObject jsonObject = JSONObject.parseObject(result);
						JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("trans_result"));
						JSONObject jsonObject2 = jsonArray.getJSONObject(0);
						buffer.append(jsonObject2.getString("dst"));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
			System.out.println(buffer.toString());
//			System.out.println(f(" It sounds as though you were l"));
		}
	}

}
