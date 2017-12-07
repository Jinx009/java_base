package service.basicFunctions.device;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.models.device.vo.DeviceLogVo;
import service.basicFunctions.BaseService;
import utils.StringUtil;
import utils.model.BaseConstant;
import utils.model.Resp;

@Service
public class DeviceLogService extends BaseService {

	private static final Logger log = LoggerFactory.getLogger(DeviceLogService.class);

	public Resp<?> list(String params) {
		Resp<?> resp = new Resp<>(false);
		try {
			List<DeviceLogVo> fileList = new ArrayList<DeviceLogVo>();
			String deviceLogFilePath = BaseConstant.DEVICE_LOG_FILE_PATH;
			File f = new File(deviceLogFilePath);
			if (!f.exists()) {
				return resp;
			}
			File fa[] = f.listFiles();
			for (int i = 0; i < fa.length; i++) {
				File file = fa[i];
				String savePath = file.getAbsolutePath().replaceFirst(deviceLogFilePath, "");
				Integer lastIndex = savePath.lastIndexOf("/");
				if (lastIndex > -1)
					savePath = savePath.substring(0, lastIndex);

				DeviceLogVo logfileVo = new DeviceLogVo(file, savePath);
				fileList.add(logfileVo);
			}
			resp = new Resp<>(fileList);
			return resp;
		} catch (Exception e) {
			log.error("error:{}", e);
		}
		return resp;
	}
	
	public Resp<?> delete(String params) {
		Resp<?> resp = new Resp<>(false);
		try {
			JSONObject jsonObject = JSONObject.parseObject(params);
			String fileName = jsonObject.getString(BaseConstant.FILENAME);
			fileName = StringUtil.add(BaseConstant.DEVICE_LOG_FILE_PATH,fileName);
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
