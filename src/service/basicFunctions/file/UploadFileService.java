package service.basicFunctions.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import service.basicFunctions.BaseService;
import utils.StringUtil;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class UploadFileService extends BaseService {

	private static final Logger log = LoggerFactory.getLogger(UploadFileService.class);

	/**
	 * 上传固件管理
	 * @param params
	 * @return
	 */
	public Resp<?> list(String params) {
		Resp<?> resp = new Resp<>(false);
		try {
			String baseDirectoryName = BaseConstant.BASE_DERICTORY_NAME;
			 File file=new File(baseDirectoryName);
			if (file.isDirectory() && file.exists()) {
				List<String> list = new ArrayList<String>();
				for (File temp : file.listFiles()) {
					if (temp.isFile()) {
						list.add(temp.toString().split(baseDirectoryName)[1]);
					}
				}
				return new Resp<>(list);
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	
	/**
	 * 删除无用日志文件
	 * @param params
	 * @return
	 */
	public Resp<?> delete(String params) {
		Resp<?> resp = new Resp<>(false);
		try {
			JSONObject jsonObject = JSONObject.parseObject(params);
			String fileName = jsonObject.getString(BaseConstant.FILENAME);
			fileName = StringUtil.add(BaseConstant.BASE_DERICTORY_NAME,fileName);
			File f = new File(fileName);
			if (!f.exists()||f.isDirectory()) {
				resp.setMsg(BaseConstant.FILE_NOT_FOUND);
				return resp;
			}else{
				f.delete();
				resp = new Resp<>(true);
				return resp;
			}
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}

}
