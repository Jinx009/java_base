package service.basicFunctions.parking;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.parking.ParkingInvoiceDao;
import database.models.parking.ParkingInvoice;

@Service
public class ParkingInvoiceService {

	@Autowired
	private ParkingInvoiceDao parkingInvoiceDao;
	
	public List<ParkingInvoice> findAll(){
		return parkingInvoiceDao.findAll();
	}
	
	public ParkingInvoice save(ParkingInvoice parkingInvoice){
		parkingInvoice.setCreateTime(new Date());
		parkingInvoice.setSendTime(new Date());
		parkingInvoice.setShowStatus(1);
		parkingInvoice.setStatus(1);
		return parkingInvoiceDao.save(parkingInvoice);
	}
	
	public void delete(Integer id){
		ParkingInvoice parkingInvoice = parkingInvoiceDao.find(id);
		parkingInvoice.setShowStatus(0);
		parkingInvoiceDao.update(parkingInvoice);
	}
	
	public void updateStatus(Integer id,Integer status){
		ParkingInvoice parkingInvoice = parkingInvoiceDao.find(id);
		parkingInvoice.setStatus(status);
		parkingInvoice.setSendTime(new Date());
		parkingInvoiceDao.update(parkingInvoice);
	}
	
}
