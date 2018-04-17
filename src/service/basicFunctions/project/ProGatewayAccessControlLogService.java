package service.basicFunctions.project;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProGatewayAccessControlLogDao;
import database.common.PageDataList;
import database.models.project.ProGatewayAccessControlLog;

@Service
public class ProGatewayAccessControlLogService {

	@Autowired
	private ProGatewayAccessControlLogDao proGatewayAccessControlLogDao;
	
	public void save(ProGatewayAccessControlLog proAccessControlLog){
		proAccessControlLog.setCreateTime(new Date());
		proGatewayAccessControlLogDao.save(proAccessControlLog);
	}
	
	public PageDataList<ProGatewayAccessControlLog> pageList(Integer p){
		return proGatewayAccessControlLogDao.pageList(p);
	}
	
}
