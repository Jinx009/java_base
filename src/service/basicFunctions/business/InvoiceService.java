package service.basicFunctions.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import database.basicFunctions.dao.business.InvoiceDao;
import database.models.business.Invoice;

@Service
public class InvoiceService {

	@Autowired
	private InvoiceDao invoiceDao;
	
	public Invoice findOne(){
		List<Invoice> list = invoiceDao.findAll();
		return list.get(0);
	}
	
}
