package service.basicFunctions.file;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import service.basicFunctions.BaseService;
import utils.model.Resp;

@Service
public class UploadFileService extends BaseService {

	private static final Logger log = LoggerFactory.getLogger(UploadFileService.class);

	public Resp<?> list(String params) {
		Resp<?> resp = new Resp<>(false);
		try {
			String baseDirectoryName = "/Users/jinx/Downloads/test/";
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

}
