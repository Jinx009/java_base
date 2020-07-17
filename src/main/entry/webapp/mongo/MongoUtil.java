package main.entry.webapp.mongo;

import java.util.ArrayList;
import java.util.List;

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

import database.model.GnssRtkLog;

public class MongoUtil {

	private static Logger log = LoggerFactory.getLogger(MongoUtil.class);

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
		// 指定查询过滤器
		Bson filter = Filters.gt("updatetime", time);
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

	public static void main(String[] args) {
		select("2020-05-24T20:00:00.000Z");
	}
}