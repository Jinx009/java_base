package main.entry.webapp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

@Component
@Lazy(value = false)
public class StatusCheckTask {

	private static final Logger log = LoggerFactory.getLogger(StatusCheckTask.class);

	@Scheduled(cron = "0 */10 * * * ?") // XX分钟处理一次
	public void init() {
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://10.0.0.18:5432/port", "gv","Hik12345");
			c.setAutoCommit(false);
			log.warn("Connect Sql Success!");
			stmt = c.createStatement();
 
			ResultSet rs = stmt.executeQuery("select * from device_type_code");
			while(rs.next()){
				int id = rs.getInt("id");
				log.warn("id:{}",id);
//				System.out.println(id + "," + name + "," + age + "," + address.trim() + "," + salary);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			log.error("e:{}",e);
		}
	}

	public static void main(String[] args){
		Connection c = null;
		Statement stmt = null;
		try {
			Class.forName("org.postgresql.Driver");
			c = DriverManager.getConnection("jdbc:postgresql://10.0.0.18:5432/port", "gv","Hik12345");
			c.setAutoCommit(false);
			log.warn("Connect Sql Success!");
			stmt = c.createStatement();
 
			ResultSet rs = stmt.executeQuery("select * from device_type_code");
			while(rs.next()){
				String id = rs.getString("name");
				log.warn("name:{}",id);
//				System.out.println(id + "," + name + "," + age + "," + address.trim() + "," + salary);
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (Exception e) {
			log.error("e:{}",e);
		}
	}
	
}
