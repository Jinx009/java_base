package service.basicFunctions.job;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import database.basicFunctions.dao.job.CommonJobDao;
import database.models.job.CommonJob;
import service.basicFunctions.HttpService;
import utils.BaseConstant;
import utils.HttpData;

@Service
public class CommonJobService {

	@Autowired
	private CommonJobDao commonJobDao;
	@Autowired
	private HttpService httpService;
	
	
	public void update(CommonJob commonJob){
		commonJobDao.update(commonJob);
	}
	
	public void save(CommonJob commonJob){
		commonJob.setCreateTime(new Date());
		commonJob.setStatus(0);
		commonJobDao.save(commonJob);
	}

	public List<CommonJob> findNotFinish(String serviceName) {
		return commonJobDao.findNotFinish(serviceName);
	}
	
	
	public void update(){
		List<CommonJob> list = commonJobDao.findNotFinishAll();
		if(list!=null){
			for(CommonJob commonJob:list){
				Integer id = commonJob.getBaseId();
				String result = httpService.get(HttpData.jobFind(id));
				JSONObject jsonObject = JSONObject.parseObject(result);
				String code = jsonObject.getString("respCode");
				if(BaseConstant.HTTP_OK_CODE.equals(code)){
					String jsonStr =  jsonObject.getString(BaseConstant.PARAMS);
					JSONObject jsObject = JSONObject.parseObject(jsonStr);
					Integer status = jsObject.getInteger("status");
					if(!status.equals(commonJob.getStatus())){
						commonJob.setStatus(status);
						commonJob.setFinishTime(new Date());
						commonJobDao.update(commonJob);
					}
				}
			}
		}
	}

	public List<CommonJob> findAll(String serviceName) {
		return commonJobDao.findAllByServiceName(serviceName);
	}
	
}
