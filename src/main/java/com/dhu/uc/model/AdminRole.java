package com.dhu.uc.model;

import java.io.Serializable;
import java.util.Date;

public class AdminRole extends Role implements Serializable {

	private static final long serialVersionUID = -5844191125006792120L;
	private String creator;
	private String updator;
	private String userName;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}

}