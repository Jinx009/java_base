package utils.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 所有ajax获取的api接口
 * 
 * @author jinx
 *
 */
@Getter
@AllArgsConstructor
public enum DataCode {

	 DEVICE_ROUTER_LIST(         "device_router_list_1_0",             "deviceRouterService",      "list",        "router page列表",        true),
	 
	 DEVICE_SENSOR_LIST(         "device_sensor_list_1_0",             "deviceSensorService",      "list",        "sensor page列表",        true),
	 DEVICE_SENSOR_DETAIL(       "device_sensor_detail_1_0",           "deviceSensorService",      "detail",      "sensor detail列表",      true),
	 
	 DEVICE_CROSS_SENSOR_LIST(   "device_cross_sensor_list_1_0",       "deviceCrossSensorService",  "list",        "cross_sensor page列表",  true),
	 DEVICE_REPEATER_LIST(		 "device_repeater_list_1_0",           "deviceRepeaterService",     "list",        "repeater page列表",      true),
	 
	 DEVICE_ERROR_FLOW_LIST(     "device_error_flow_list_1_0",         "deviceErrorFlowService",    "list",       "errorflow page列表",     true),
	 DEVICE_ERROR_FLOW_DELETE(   "device_error_flow_delete_1_0",       "deviceErrorFlowService",    "delete",     "errorflow 完成",         true),
	 
	 DEVICE_JOB_LIST(			 "device_job_list_1_0",				   "deviceJobService",			"list",		   "job page列表",           true),
	 DEVICE_JOB_CREATE(			 "device_job_create_1_0",			   "deviceJobService",			"create",	   "job 新建",				true),
	 DEVICE_JOB_DELETE(			 "device_job_delete_1_0",				"deviceJobService",			"delete", 		"job 放弃",				true),
	 
	 UPDATE_FILE_LIST(			 "update_file_list_1_0",				"uploadFileService",		"list",		 	"上传文件列表",			true),
	 
	 
	 BUSINESS_LOCATION_LIST(	"business_location_list_1_0",			"businessLocationService",	"list",			"location page列表",		true),
	 BUSINESS_LOCATION_ALL(		"business_location_all_1_0",			"businessLocationService",	"all",			"location all列表",		true),
	 
	 BUSINESS_AREA_LIST(		"business_area_list_1_0",				"businessAreaService",		"list",			"area page列表",			true),
	 BUSINESS_AREA_ALL(			"business_area_all_1_0",				"businessAreaService",		"all",			"area all列表",			true),
	 
	 BUSINESS_APPINFO_LIST(		"business_appinfo_list_1_0",			"businessAppInfoService",	"list",			"appInfo page列表",		true),
	 ;
	
	 private String code;         //编码
     private String serverBean;   //bean名称
     private String func;         //方法名
     private String desc;         //描述
     private boolean needLogin;   //是否需要登陆
     
     
     /**
      *  根据code查询
      * @param code 接口编码
      * @return 接口编码对象
      */
     public static DataCode getByCode(String code) {
         DataCode[] dc = DataCode.values();
         for (DataCode e : dc) {
             if (e.getCode().equals(code))
                 return e;
         }
         return null;
     }
     
}
