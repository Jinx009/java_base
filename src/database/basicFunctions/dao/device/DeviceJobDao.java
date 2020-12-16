package database.basicFunctions.dao.device;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import database.common.BaseDao;
import database.common.PageDataList;
import database.common.QueryParam;
import database.common.OrderFilter.OrderType;
import database.models.device.DeviceCrossSensor;
import database.models.device.DeviceJob;
import database.models.device.DeviceSensor;
import utils.model.BaseConstant;

@Repository
public class DeviceJobDao extends BaseDao<DeviceJob>{

	public PageDataList<DeviceJob> findAll(Integer p) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("recSt", 1);
		queryParam.addPage(p, BaseConstant.PAGE_SIZE);
		queryParam.addOrder(OrderType.DESC, "id");
		return findPageList(queryParam);
	}

	public List<DeviceJob> findByTarget(String routerMac) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status",0);
		queryParam.addParam("target", routerMac);
		return findByCriteria(queryParam);
	}

	public void save(DeviceSensor deviceSensor, String cmd, String jobDetail) {
		DeviceJob deviceJob = new DeviceJob();
		deviceJob.setCmd(cmd);
		deviceJob.setCreateTime(new Date());
		deviceJob.setJobDetail(jobDetail);
		deviceJob.setRecSt(1);
		deviceJob.setStatus(0);
		deviceJob.setTarget(deviceSensor.getRouterMac());
		save(deviceJob);
	}

	public void save(DeviceCrossSensor deviceCrossSensor, String cmd, String jobDetail) {
		DeviceJob deviceJob = new DeviceJob();
		deviceJob.setCmd(cmd);
		deviceJob.setCreateTime(new Date());
		deviceJob.setJobDetail(jobDetail);
		deviceJob.setRecSt(1);
		deviceJob.setStatus(0);
		deviceJob.setTarget(deviceCrossSensor.getRouterMac());
		save(deviceJob);
	}

	public void save(String mac, String cmd, String jobDetail) {
		DeviceJob deviceJob = new DeviceJob();
		deviceJob.setCmd(cmd);
		deviceJob.setCreateTime(new Date());
		deviceJob.setJobDetail(jobDetail);
		deviceJob.setRecSt(1);
		deviceJob.setStatus(0);
		deviceJob.setTarget(mac);
		save(deviceJob);
	}

	public List<DeviceJob> findByMacAndStatus(String mac, int i) {
		QueryParam queryParam = QueryParam.getInstance();
		queryParam.addParam("status", i);
		queryParam.addParam("target", mac);
		return findByCriteria(queryParam);
	}
	
}
