package main.entry.webapp.mongo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.SimpleTimeZone;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import database.model.GnssMongoDeviceModel;
import database.model.GnssRtkLog;

public class MongoUtil {

	private static Logger log = LoggerFactory.getLogger(MongoUtil.class);
	

	
	/**
	 * 保存一个站点
	 * @param basetag
	 * @param tag
	 * @param substatus
	 * @param topic
	 * @param tagtype
	 */
	public static void save(String basetag,String tag,Integer substatus,String topic,Integer tagtype) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("setting");
		MongoCollection<Document> collection = mongoDatabase.getCollection("tags");
		collection.insertOne(new Document("basetag",basetag)
				.append("tag",tag)
				.append("topic",topic)
				.append("substatus", substatus)
				.append("tagtype", tagtype)
				.append("time", new Date().getTime()));
		mongoClient.close();
	}
	
	public static void updateTag(String tag,Integer substatus,Integer tagtype, String basetag, String dataType) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("setting");
		MongoCollection<Document> collection = mongoDatabase.getCollection("tags");
		collection.updateOne(Filters.eq("tag", tag), new Document("$set",new Document("substatus",substatus).append("tagtype", tagtype)
				.append("basetag", basetag).append("topic", dataType)));
		mongoClient.close();
	}
	
	@SuppressWarnings("rawtypes")
	public static GnssMongoDeviceModel selectTag(String tag) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("setting");
		MongoCollection<Document> collection = mongoDatabase.getCollection("tags");
		Bson filter = Filters.eq("tag",tag);
		FindIterable findIterable = collection.find(filter);
		MongoCursor cursor = findIterable.iterator();
		GnssMongoDeviceModel model = new GnssMongoDeviceModel();
		while (cursor.hasNext()) {
			String str = JSONObject.toJSONString(cursor.next());
			model = JSONObject.parseObject(str, GnssMongoDeviceModel.class);
		}
		mongoClient.close();
		return model;
	}

	@SuppressWarnings("rawtypes")
	public static List<GnssRtkLog> select() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("result");
		MongoCollection<Document> collection = mongoDatabase.getCollection("hourlyresult");
		List<GnssRtkLog> logs = new ArrayList<GnssRtkLog>();
		// 查找集合中的所有文档
		FindIterable findIterable = collection.find();
		MongoCursor cursor = findIterable.iterator();
		while (cursor.hasNext()) {
			String str = JSONObject.toJSONString(cursor.next());
			GnssRtkLog log = JSONObject.parseObject(str, GnssRtkLog.class);
			logs.add(log);
		}
//		log.warn(JSONObject.toJSONString(logs));
		mongoClient.close();
		return logs;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<GnssRtkLog> selectdailyresult() {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("result");
		MongoCollection<Document> collection = mongoDatabase.getCollection("dailyresult");
		List<GnssRtkLog> logs = new ArrayList<GnssRtkLog>();
		// 查找集合中的所有文档
		FindIterable findIterable = collection.find();
		MongoCursor cursor = findIterable.iterator();
		while (cursor.hasNext()) {
			String str = JSONObject.toJSONString(cursor.next());
			GnssRtkLog log = JSONObject.parseObject(str, GnssRtkLog.class);
			logs.add(log);
		}
//		log.warn(JSONObject.toJSONString(logs));
		mongoClient.close();
		return logs;
	}
	
	@SuppressWarnings("rawtypes")
	public static List<GnssRtkLog> selectdailyresult(String time) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("result");
		MongoCollection<Document> collection = mongoDatabase.getCollection("dailyresult");
		List<GnssRtkLog> logs = new ArrayList<GnssRtkLog>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(Long.parseLong(time));
		// 指定查询过滤器
		log.warn("date:{},date:{}",sdf.format(date), dateToISODate( sdf.format(date)));
		Bson filter = Filters.gt("datetime",dateToISODate( sdf.format(date)));
		// 指定查询过滤器查询
		FindIterable findIterable = collection.find(filter);
		MongoCursor cursor = findIterable.iterator();
		while (cursor.hasNext()) {
			String str = JSONObject.toJSONString(cursor.next());
			GnssRtkLog log = JSONObject.parseObject(str, GnssRtkLog.class);
			logs.add(log);
		}
		log.warn(JSONObject.toJSONString(logs));
		mongoClient.close();
		return logs;
	}

	@SuppressWarnings("rawtypes")
	public static List<GnssRtkLog> select(String time) {
		MongoClient mongoClient = new MongoClient("localhost", 27017);
		MongoDatabase mongoDatabase = mongoClient.getDatabase("result");
		MongoCollection<Document> collection = mongoDatabase.getCollection("hourlyresult");
		List<GnssRtkLog> logs = new ArrayList<GnssRtkLog>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(Long.parseLong(time));
		// 指定查询过滤器
		log.warn("date:{},date:{}",sdf.format(date), dateToISODate( sdf.format(date)));
		Bson filter = Filters.gt("datetime",dateToISODate( sdf.format(date)));
		// 指定查询过滤器查询
		FindIterable findIterable = collection.find(filter);
		MongoCursor cursor = findIterable.iterator();
		while (cursor.hasNext()) {
			String str = JSONObject.toJSONString(cursor.next());
			GnssRtkLog log = JSONObject.parseObject(str, GnssRtkLog.class);
			logs.add(log);
		}
		log.warn(JSONObject.toJSONString(logs));
		mongoClient.close();
		return logs;
	}

	
	
	public static Date dateToISODate(String dateStr){
	    //T代表后面跟着时间，Z代表UTC统一时间
	    Date date = formatD(dateStr);
	    SimpleDateFormat format =
	            new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
	    format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
	    String isoDate = format.format(date);
	    try {
	        return format.parse(isoDate);
	    } catch (ParseException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static Date formatD(String dateStr){
	        return formatD(dateStr,DATE_TIME_PATTERN);
	}

	public static Date formatD(String dateStr ,String format)  {
	        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
	        Date ret = null ;
	        try {
	            ret = simpleDateFormat.parse(dateStr) ;
	        } catch (ParseException e) {
	        }
	        return ret;
	}

}