package service.basicFunctions.business;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.business.BusinessMemberDao;
import database.basicFunctions.dao.business.BusinessPointsHisDao;
import database.models.business.BusinessMember;
import database.models.business.BusinessPointsHis;

@Service
public class TaskService {

	@Autowired
	private BusinessMemberDao businessMemberDao;
	@Autowired
	private BusinessPointsHisDao businessPointsHisDao;
	
	public void task() throws ParseException{
		List<BusinessMember> list = businessMemberDao.findByDate();
		if(list!=null&&!list.isEmpty()){
			int i = 0;
			for(BusinessMember businessMember : list){
				String dateStr = "2018-01-01 00:00:00";
				String dateStr1 = "2018-12-31 00:00:00";
				SimpleDateFormat seDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = seDateFormat.parse(dateStr);
				Date date1 = seDateFormat.parse(dateStr1);
				if(businessMember.getCurrentPoints()!=0){
					BusinessPointsHis businessPointsHis = new BusinessPointsHis();
					businessPointsHis.setCreateTime(date);
					businessPointsHis.setAction(0);
					businessPointsHis.setMemberId(businessMember.getId());
					businessPointsHis.setMerId(348);
					businessPointsHis.setQuantity(businessMember.getCurrentPoints());
					businessPointsHis.setRecSt(1);
					businessPointsHis.setRemarks("积分清零");
					businessPointsHis.setUseTime(date);
					businessPointsHisDao.save(businessPointsHis);
					i++;//63556   73382
					System.out.println("----------"+businessMember.getId()+"----"+businessPointsHis.getId()+"---"+i);
				}
//				businessMember.setCurrentPoints(0);
//				businessMember.setExpiredTime(date1);
//				businessMemberDao.update(businessMember);
			}
		}
	}
	
}
