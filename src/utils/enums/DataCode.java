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
	 DEVICE_ROUTER_DETAIL(       "device_router_detail_1_0",           "deviceRouterService",      "detail",      "router 详情",            true),
	 DEVICE_ROUTER_EDIT(         "device_router_edit_1_0",             "deviceRouterService",      "edit",        "router edit",            true),
	 DEVICE_ROUTER_ALL(          "device_router_all_1_0",              "deviceRouterService",      "all",         "router 列表",            true),
	 
	 DEVICE_CARD_LIST(           "device_card_list_1_0",              "deviceCardService",         "list",         "电子卡片 列表",           true),
	 
	 DEVICE_SENSOR_LIST(         "device_sensor_list_1_0",             "deviceSensorService",      "list",        "sensor page列表",        true),
	 DEVICE_SENSOR_DETAIL(       "device_sensor_detail_1_0",           "deviceSensorService",      "detail",      "sensor detail列表",      true),
	 DEVICE_SENSOR_ALL(          "device_sensor_all_1_0",              "deviceSensorService",      "all",         "sensor  列表",            true),
	 DEVICE_SENSOR_SET_AREAL(    "device_sensor_setArea_1_0",          "deviceSensorService",      "setArea",     "sensor  设置区域",        true),
	 DEVICE_SENSOR_SET_UPDATE(   "device_sensor_setUpdate_1_0",        "deviceSensorService",      "setUpdate",   "sensor  参数更新",        true),
	 
	 DEVICE_CROSS_SENSOR_LIST(   "device_cross_sensor_list_1_0",       "deviceCrossSensorService",  "list",        "cross_sensor page列表",  true),
	 DEVICE_CROSS_SENSOR_DETAIL( "device_cross_sensor_detail_1_0",     "deviceCrossSensorService",  "detail",      "cross_sensor 详情",      true),
	 DEVICE_CROSS_SENSOR_ALL(    "device_cross_sensor_all_1_0",        "deviceCrossSensorService",  "all",         "cross_sensor 列表",      true),
   DEVICE_CROSS_SENSOR_SET_AREAL("device_cross_sensor_setArea_1_0",    "deviceCrossSensorService",  "setArea",     "sensor  设置区域",        true),
  DEVICE_CROSS_SENSOR_SET_UPDATE("device_cross_sensor_setUpdate_1_0",  "deviceCrossSensorService",  "setUpdate",   "sensor  参数更新",        true),
	 
	 DEVICE_REPEATER_LIST(		 "device_repeater_list_1_0",           "deviceRepeaterService",    "list",        "repeater page列表",      true),
	 
	 DEVICE_ERROR_FLOW_LIST(     "device_error_flow_list_1_0",         "deviceErrorFlowService",   "list",        "errorflow page列表",     true),
	 DEVICE_ERROR_FLOW_DELETE(   "device_error_flow_delete_1_0",       "deviceErrorFlowService",   "delete",      "errorflow 完成",         true),
	 
	 DEVICE_JOB_LIST(			 "device_job_list_1_0",				   "deviceJobService",		   "list",		   "job page列表",          true),
	 DEVICE_JOB_BASE_CREATE(	 "device_job_base_create_1_0",		   "deviceJobService",		   "baseCreate",   "job 新建",			    true),
	 DEVICE_JOB_CREATE(			 "device_job_create_1_0",			   "deviceJobService",		   "create",	   "job 新建",			    true),
	 DEVICE_JOB_CROSS_CREATE(	 "device_job_cross_create_1_0",		   "deviceJobService",		   "crossCreate",  "cross job 新建",			true),
	 DEVICE_JOB_ROUTER_CREATE(	 "device_job_router_create_1_0",	   "deviceJobService",		   "routerCreate", "router job 新建",		true),
	 DEVICE_JOB_DELETE(			 "device_job_delete_1_0",			   "deviceJobService",		   "delete", 	   "job 放弃",				true),
	 
	 DEVICE_LOG_LIST(			 "device_log_list_1_0",			       "deviceLogService",		   "list", 	        "log 列表",				true),
	 DEVICE_LOG_DELETE(			 "device_log_delete_1_0",			   "deviceLogService",		   "delete", 	    "log 删除",				true),
	 
	 UPDATE_FILE_LIST(			 "update_file_list_1_0",			   "uploadFileService",		   "list",		 	"上传文件列表",			true),
	 UPDATE_FILE_DELETE(		 "update_file_delete_1_0",			   "uploadFileService",		   "delete",		 "上传文件删除",			true),
	 
	 
	 BUSINESS_LOCATION_LIST(	 "business_location_list_1_0",		   "businessLocationService",	"list",			"location page列表",		true),
	 BUSINESS_LOCATION_ALL(		 "business_location_all_1_0",		   "businessLocationService",	"all",			"location all列表",		true),
	 BUSINESS_LOCATION_EDIT(	 "business_location_edit_1_0",		   "businessLocationService",	"edit",			"location edit",		true),
	 BUSINESS_LOCATION_CREATE(	 "business_location_create_1_0",	   "businessLocationService",	"create",		"location create",	    true),
	 BUSINESS_LOCATION_DELETE(	 "business_location_delete_1_0",	   "businessLocationService",	"delete",		"location delete",	    true),
	 BUSINESS_LOCATION_DETAIL(	 "business_location_detail_1_0",	   "businessLocationService",	"detail",		"location detail",	    true),
	 
	 BUSINESS_AREA_LIST(		 "business_area_list_1_0",			   "businessAreaService",		"list",			"area page列表",			true),
	 BUSINESS_AREA_ALL(			 "business_area_all_1_0",			   "businessAreaService",		 "all",			"area all列表",			true),
	 BUSINESS_AREA_EDIT(		 "business_area_edit_1_0",			   "businessAreaService",		 "edit",		"area edit",			true),
	 BUSINESS_AREA_CREATE(		 "business_area_create_1_0",		   "businessAreaService",		 "create",		"area create",			true),
	 BUSINESS_AREA_DELETE(		 "business_area_delete_1_0",		   "businessAreaService",		 "delete",		"area delete",			true),
	 BUSINESS_AREA_DETAIL(		 "business_area_detail_1_0",		   "businessAreaService",		 "detail",		"area detail",			true),
	 
	 BUSINESS_APPINFO_LIST(		 "business_appinfo_list_1_0",		   "businessAppInfoService",	"list",			"appInfo page列表",		true),
	 BUSINESS_APPINFO_EDIT(		 "business_appinfo_edit_1_0",		   "businessAppInfoService",	"edit",			"appInfo edit",		    true),
	 BUSINESS_APPINFO_CREATE(	 "business_appinfo_create_1_0",		   "businessAppInfoService",	"create",		"appInfo save",		    true),
	 BUSINESS_APPINFO_DETAIL(	 "business_appinfo_detail_1_0",		   "businessAppInfoService",	"detail",		"appInfo detail",		true),
	 BUSINESS_APPINFO_DELETE(	 "business_appinfo_delete_1_0",		   "businessAppInfoService",	"delete",		"appInfo delete",		true),
	 BUSINESS_APPINFO_ALL(	     "business_appinfo_all_1_0",		   "businessAppInfoService",	"all",		    "appInfo all",		    true),
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
