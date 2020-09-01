package main.entry.webapp.mongo;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.bson.Document;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

@Component
public class MongoUtils {

//	private static Logger log = LoggerFactory.getLogger(MongoUtils.class);
	
	private static MongoClient mongoClient;
	private static MongoDatabase mongoDatabase;
	private static MongoCollection<Document> collection;

	@PostConstruct
	public void init() {
		 mongoClient = new MongoClient("106.14.94.245", 27017);
		 mongoDatabase = mongoClient.getDatabase("zhanway");
		 collection = mongoDatabase.getCollection("items");
	}
	

	public static void select(String dataStr) {
		String mac = dataStr.substring(0,16);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateStr = sdf.format(new Date());
		collection.insertOne(new Document("mac",mac).append("data", dataStr).append("dateStr", dateStr));
	}

	public static void main(String[] args) {
		System.out.println(Long.parseLong("1594623319210"));
	}
	
}
class AccLog{
	private String mac;
	private String data;
	private String dateStr;
	
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDateStr() {
		return dateStr;
	}
	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}
}
