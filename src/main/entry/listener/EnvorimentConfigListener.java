package main.entry.listener;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
public class EnvorimentConfigListener implements HttpSessionListener {
        
    public void sessionCreated(HttpSessionEvent se) {
        System.out.println("iot.hosting.rds.zhanwaydb.mysqlUrl:---"+System.getenv("iot_hosting_rds_zhanwaydb_mysqlUrl"));
        System.out.println("mysqlUrl:---"+System.getenv("mysqlUrl"));
    	System.out.println(System.getenv("mysqlUrl"));
	   Map<String, String> map = System.getenv();
        for(Iterator<String> itr = map.keySet().iterator();itr.hasNext();){
            String key = itr.next();
            System.out.println(key + "=" + map.get(key));
        }   
    }
    public void sessionDestroyed(HttpSessionEvent se) {
        System.out.println("iot.hosting.rds.zhanwaydb.mysqlUrl:---"+System.getenv("iot_hosting_rds_zhanwaydb_mysqlUrl"));
        System.out.println("mysqlUrl:---"+System.getenv("mysqlUrl"));
    }
}