package com.dhu.uc.model;

import java.io.Serializable;
import java.util.Date;

public class Admin implements Serializable {
	
	private static final long serialVersionUID = -7408543362360430367L;

	private String userName;

	private String password;

	private Date createTime;

	private Date updateTime;

	private Integer status;

	private String lastLoginIP;

	private Integer lastLoginFailureCount;

	private String email;

	private String phone;

	private String realName;

	private Integer type;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getLastLoginIP() {
		return lastLoginIP;
	}

	public void setLastLoginIP(String lastLoginIP) {
		this.lastLoginIP = lastLoginIP;
	}

	public Integer getLastLoginFailureCount() {
		return lastLoginFailureCount;
	}

	public void setLastLoginFailureCount(Integer lastLoginFailureCount) {
		this.lastLoginFailureCount = lastLoginFailureCount;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}