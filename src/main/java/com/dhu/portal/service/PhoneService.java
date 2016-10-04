package com.dhu.portal.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.dhu.common.Constants;
import com.dhu.common.ResourceConfig;
import com.dhu.framework.cache.annotation.CheckCache;

@Service
public class PhoneService {
	
	@Autowired
    private  RestTemplate restTemplate;

	Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 *
	 * 获取主号
	 * @param model
	 * @return
	 */
	@CheckCache(timeToLive = 60*60*4,cacheNull = false)
	public String getHost(String model, String city){
        String url= ResourceConfig.getString("ext.host");
        url = url + "/api/ext/gethost?model=" + model + "&city="+city;
        try{
			JSONObject object = restTemplate.getForObject(url, JSONObject.class);
			if(Constants.STATUS_OK.equals(object.getString("status"))){
				return object.getString("hostNumber");
			}else return "";
		}catch(Exception e){
			logger.info("带看评价发送失败", e);
			return null;}

    }


}
