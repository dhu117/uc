package com.dhu.portal.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;

/**
 * 短信工具类 Created by LV on 2016/4/15 0015. Email:LvLoveYuForever@gmail.com
 */
public class MobileMessageSend {
	private static final String SERVER_URL = "https://api.netease.im/";// 请求的URL
	private static final String APP_KEY = "85a81cc1dc249283572cd5b8f547d862";// 账号
	private static final String APP_SECRET = "c74428368715";// 密码
	private static final String MOULD_ID = "1";// 模板ID
	private static final String NONCE = "123456";

	public static int sendMsg(String phone) throws IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = getHttpPost(SERVER_URL + "sms/sendcode.action");
		// 设置请求参数
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		nameValuePairs.add(new BasicNameValuePair("mobile", phone));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
		// 执行请求
		HttpResponse response = httpclient.execute(post);
		String responseEntity = EntityUtils.toString(response.getEntity(), "utf-8");
		// 判断是否发送成功，发送成功返回true
		String code = JSON.parseObject(responseEntity).getString("code");
		if (code.equals("200")) {
			return 0;
		}

		return 500;
	}

	private static HttpPost getHttpPost(String url) {
		HttpPost post = new HttpPost(url);

		String curTime = String.valueOf((new Date().getTime() / 1000L));
		String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);

		// 设置请求的header
		post.addHeader("AppKey", APP_KEY);
		post.addHeader("Nonce", NONCE);
		post.addHeader("CurTime", curTime);
		post.addHeader("CheckSum", checkSum);
		post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
		return post;
	}

	public static int validateMsg(String phone, String msgCode) throws IOException {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost post = getHttpPost(SERVER_URL + "sms/verifycode.action");
		// 设置请求参数
		List<NameValuePair> nameValuePairs = new ArrayList<>();
		nameValuePairs.add(new BasicNameValuePair("mobile", phone));
		nameValuePairs.add(new BasicNameValuePair("code", msgCode));
		post.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"));
		// 执行请求
		HttpResponse response = httpclient.execute(post);
		String responseEntity = EntityUtils.toString(response.getEntity(), "utf-8");

		// 判断是否发送成功，发送成功返回true
		String code = JSON.parseObject(responseEntity).getString("code");
		if (code.equals("200")) {
			return 0;
		}

		return 500;
	}
}