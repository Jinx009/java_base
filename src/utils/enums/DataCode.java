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

	 DEVICE_ROUTER_LIST("device_router_list_1_0","deviceRouterService","list","router列表",true),
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
