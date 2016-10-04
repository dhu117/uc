package com.dhu.common.util;

import java.util.HashMap;
import java.util.Map;

import com.dhu.common.CityThreadLocal;
import com.dhu.common.Constants;
import com.dhu.common.ResourceConfig;
import com.dhu.framework.conf.GlobalConfig;
import com.dhu.portal.enums.CityNameEnum;

/**
 * 配置参数初始化
 * @author Fant
 */
public class ConfigUtil {

	private static Map<String, Map<String, Object>> configMap = null;
	
	public static Map<String, Object> getConfigMap() {
    	if(configMap == null){
    		initConfigMap();
		}
		return configMap.get(CityThreadLocal.get());
	}
	
	/**
     * 初始化configMap
     */
    private synchronized static void initConfigMap(){
    	if(configMap == null){
    		configMap = new HashMap<String, Map<String, Object>>();
    		String[] citys = Constants.ALL_CITY.split(",");
    		for (String cityCode : citys) {
				Map<String, Object> configMap2 = new HashMap<String, Object>();
	        	configMap2.put("env", GlobalConfig.getEnv());
	        	configMap2.put("version", Constants.JSVERSION);
	        	configMap2.put("cityCode", cityCode);
	        	configMap2.put("cityName", CityNameEnum.cityNameMap.get(cityCode));
	        	
	        	configMap2.put("managehost",ResourceConfig.getString("manage.host"));	        		        	
	        	configMap2.put("apihost",ResourceConfig.getString("api.host"));
	        	configMap2.put("openhost",ResourceConfig.getString("open.host"));
	        	configMap2.put("chathost",ResourceConfig.getString("chat.host"));
	        	configMap2.put("publichost",ResourceConfig.getString("public.host"));
	        	configMap2.put("statichost",ResourceConfig.getString("static.host"));
	        	configMap2.put("loginhost",ResourceConfig.getString("login.host"));
	        	
	        	configMap2.put("webhost",CommonFunctions.getConfigByCity("web.host", cityCode));
	        	configMap2.put("uchost",CommonFunctions.getConfigByCity("uc.host", cityCode));
	        	configMap2.put("agenthost",CommonFunctions.getConfigByCity("agent.host", cityCode));
	        	configMap2.put("xinfanghost",CommonFunctions.getConfigByCity("xinfang.host", cityCode));
	        	configMap.put(cityCode, configMap2);
			}       	
    	}
    }
}
