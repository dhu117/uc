package com.dhu.uc.model;

import java.io.Serializable;
import java.util.Date;

import com.dhu.common.ResourceConfig;
import com.dhu.common.util.StringUtil;

public class AdminAuth implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7590505817507587659L;

	private Integer id;

	private String authName;

	private String remark;

	private String appCode;

	private Date createTime;

	private Date updateTime;
	
	private String url;
	private Integer parentId;
	private Integer level;

	public String getUrl() {
		if(StringUtil.isNotEmpty(appCode)){
			return ResourceConfig.getString(appCode)+url;
		}
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
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
}