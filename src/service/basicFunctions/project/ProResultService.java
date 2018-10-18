package service.basicFunctions.project;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProResultDao;
import database.models.project.ProResult;

@Service
public class ProResultService {

	@Autowired
	private ProResultDao proResultDao;
	
	public List<ProResult> list(Integer paperId){
		return proResultDao.findByPaperId(paperId);
	}

	public void save(String content, int  type, Integer paperId) {
		ProResult proResult = new ProResult();
		proResult.setContent(content);
		proResult.setCreateTime(new Date());
		proResult.setLanguageType(type);
		proResult.setPaperId(paperId);
		proResult.setRemarkA("");
		proResult.setRemarkB("");
		proResult.setRemarkC("");
		proResult.setStatus(0);
		proResult.setScore(0.00);
		proResultDao.save(proResult);
	}

	public ProResult find(Integer id) {
		return proResultDao.find(id);
	}

	public void update(ProResult proResult) {
		proResultDao.update(proResult);
	}
	
}
