package com.dhu.common.util;

import java.util.UUID;

import com.dhu.common.Constants;
import com.dhu.common.ResourceConfig;

/**
 * 公用的一些方法
 * @author Fant
 * @date 2015年7月1日 下午2:16:51
 */
public class CommonFunctions {
	
	/**
	 * 生成uuid
	 * @return
	 */
	public static String getUuid(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	/**
	 * 分城市读取配置文件变量，系统之间调用使用不能区分。
	 * wap.host  uc.host  agent.host  web.host
	 * @param key
	 * @param cityCode
	 * @return
	 */
	public static String getConfigByCity(String key, String cityCode){
		String config = ResourceConfig.getString(key);
		if(Constants.DEFAULT_CITY.equals(cityCode)){
			return config;
		}else{
			return config.replaceFirst(Constants.DEFAULT_CITY, cityCode);
		}
	}
	/**
	 * 计算最大页码
	 * 
	 * @param pageSize
	 * @param recordCount
	 * @return
	 */
	public static int getMaxPageNo(int pageSize, int recordCount) {
		int maxPageNo = recordCount / pageSize;
		if (recordCount % pageSize > 0) {
			maxPageNo = maxPageNo + 1;
		}
		if (maxPageNo == 0) {
			maxPageNo = 1;
		}
		return maxPageNo;
	}

}
