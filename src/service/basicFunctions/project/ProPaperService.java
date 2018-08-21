package service.basicFunctions.project;


import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.project.ProPaperDao;
import database.models.project.ProPaper;

@Service
public class ProPaperService {

	@Autowired
	private ProPaperDao proPaperrDao;
	
	public List<ProPaper> findByUserId(Integer userId){
		return proPaperrDao.findByUserId(userId);
	}

	public ProPaper save(String title, String abstractContent, String filePath,Integer userId) {
		ProPaper proPaper = new ProPaper();
		proPaper.setAbstractContent(abstractContent);
		proPaper.setCreateTime(new Date());
		proPaper.setFilePath(filePath);
		proPaper.setTitle(title);
		proPaper.setUserId(userId);
		return proPaperrDao.save(proPaper);
	}
	
}
