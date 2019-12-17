package main.entry.webapp;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import database.models.ParkingList;
import database.models.ParkingRecord;
import database.models.ParkingSpace;
import service.basicFunctions.ParkingListService;
import service.basicFunctions.ParkingRecordService;
import service.basicFunctions.ParkingSpaceService;

@Controller
public class PageController extends BaseController{
	
	@Autowired
	private ParkingRecordService parkingRecordService;
	@Autowired
	private ParkingSpaceService parkingSpaceService;
	@Autowired
	private ParkingListService parkingListService;
	
	/**
	 * 跳转至首页
	 * @return
	 */
	@RequestMapping(value = "")
	public String base(){
		return "/index";
	}
	
	@RequestMapping(value = "/test")
	@ResponseBody
	public String test(){
		List<ParkingRecord> list = parkingRecordService.findByDate();
		System.out.println(list.size());
		List<ParkingSpace> spaces = parkingSpaceService.findByCameraIndex("19042811061310409490");
		System.out.println(spaces.size());
		int i = 0;
		for(ParkingRecord p : list){
			int x = (Integer.valueOf(p.getCarX())+Integer.valueOf(p.getCarX2()))/2;
			int y =(Integer.valueOf(p.getCarY())+Integer.valueOf(p.getCarY2()))/2;;
			i++;
			System.out.println(i+"----"+x+"----"+y);
			for(ParkingSpace s : spaces){
				double x1 = s.getX1()*2;
				double x3 = s.getX3()*2;
				double y1 = s.getY1()*2;
				double y3 = s.getY3()*2;
				
				if(x>x1&&x<x3&&y<y3&&y>y1){
						ParkingList parkingList = new ParkingList();
						parkingList.setCreateTime(p.getVedioTime());
						parkingList.setName(s.getName());
						parkingList.setStatus(1);
						parkingListService.save(parkingList);
				}
			}
		}
		return "/index";
	}
	
	
	@RequestMapping(value = "/index")
	public String index(){
		return "/index";
	}
	
	@RequestMapping(value = "/404")
	public String _404(){
		return "/404";
	}
	
	/**
	 * spring报错
	 * @return
	 */
	@RequestMapping(value = "DevMgmt/DiscoveryTree.xml")
	public String DevMgmt(){
		return "/DevMgmt/DiscoveryTree";
	}


}
