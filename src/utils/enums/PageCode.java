package utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 页面跳转
 * 
 * @author jinx
 *
 */
@Getter
@AllArgsConstructor
public enum PageCode {
	

	 DEVICE_ROUTER_LIST(        "device_router_list",         "/device/router",                true),
	 DEVICE_ROUTER_JOB(         "device_router_job",          "/device/router_job",            true),
	 DEVICE_ROUTER_DETAIL(      "device_router_detail",       "/device/router_detail",         true),
	 
	 DEVICE_REPEATER_LIST(      "device_repeater_list",       "/device/repeater",              true),
	 
	 DEVICE_SENSOR_LIST(        "device_sensor_list",         "/device/sensor",                true),
	 DEVICE_SENSOR_JOB(         "device_sensor_job",          "/device/sensor_job",            true),
	 DEVICE_SENSOR_DETAIL(      "device_sensor_detail",       "/device/sensor_detail",         true),
	 
	 DEVICE_CROSS_SENSOR_LIST(  "device_cross_sensor_list",   "/device/cross_sensor" ,         true),
	 DEVICE_CROSS_SENSOR_JOB(   "device_cross_sensor_job",    "/device/cross_sensor_job" ,     true),
	 DEVICE_CROSS_SENSOR_DETAIL("device_cross_sensor_detail", "/device/cross_sensor_detail" ,  true),
	 
	 DEVICE_JOB_LIST(           "device_job_list",             "/device/job",                  true),
	 
	 DEVICE_LOG_LIST(           "device_log_list",             "/device/log",                  true),
	 
	 DEVICE_ERROR_FLOW_LIST(    "device_error_flow_list",      "/device/error",                true),
	 
	 SETTING_LOCATION_LIST(     "setting_location_list",       "/setting/location",            true),
	 SETTING_AREA_LIST(         "setting_area_list",           "/setting/area",                true),
	 SETTING_APPINFO_LIST(      "setting_appinfo_list",        "/setting/appinfo",             true),
	 ;
	
	 private String code;         //编码
     private String page;   //bean名称
     private boolean needLogin;   //是否需要登陆
     
     /**
      *  根据code查询
      * @param code 接口编码
      * @return 接口编码对象
      */
     public static PageCode getByCode(String code) {
    	 PageCode[] dc = PageCode.values();
         for (PageCode e : dc) {
             if (e.getCode().equals(code))
                 return e;
         }
         return null;
     }
	
}
