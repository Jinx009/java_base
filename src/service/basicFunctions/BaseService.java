package service.basicFunctions;

import java.lang.reflect.Method;
import java.util.HashMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.enums.DataCode;

/**
 * 所有数据接口基础service
 * 
 * @author jinx
 *
 */
public abstract class BaseService {

	private static final Logger log = LoggerFactory.getLogger(BaseService.class);
	
    //当前具体类
    private Class<?> clazz = this.getClass();
    //存放类中的方法列表
    private HashMap<String, Method> methods = new HashMap<String,Method>();
    //方法参数列表
    private Class<?>[] types = new Class[] { String.class };

    @PostConstruct
    public void init(){
        for (DataCode interCode : DataCode.values()) {
            String serverBean = interCode.getServerBean();
            serverBean = this.firstUpper(serverBean);
            String className = clazz.getSimpleName();
            log.debug("serverBean:{},className:{}", serverBean, className);
            if(serverBean.equals(className)) {
                String funcName = interCode.getFunc();
                try {
                    Method method = clazz.getMethod(funcName,types);
                    methods.put(funcName, method);
                } catch (NoSuchMethodException e) {
                    log.error("NoSuchMethodException className【{}】 funcName:【{}】", className, funcName);
                }
            }
        }
        log.warn("BaseService.init; methods:{}", methods);
    }

    /**
     * 首字母转大写
     * @param serverBean
     * @return
     */
    private String firstUpper(String serverBean) {
        return serverBean.substring(0, 1).toUpperCase() + serverBean.substring(1, serverBean.length());
    }

    /**
     * 执行方法总类
     * @param params
     * @return
     * @throws Exception
     */
    public Object execute(String params,String operator) throws Exception {
        return this.dispatchMethod(params, operator);
    }

    /**
     * 实际执行句柄
     */
    private Object dispatchMethod(String params, String operator) throws Exception {
        Method method = this.getMethod(operator);
        log.warn(method.toString());
        return method.invoke(this,params);
    }

    /**
     * 反射获取方法
     * @param name 方法名
     * @return 方法对象
     */
    private Method getMethod(String name) {
        return methods.get(name);
    }
	
}
