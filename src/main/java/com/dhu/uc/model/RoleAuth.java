package com.dhu.uc.model;

import java.io.Serializable;

public class RoleAuth extends AdminAuth implements Serializable {

	private static final long serialVersionUID = -5844191125006792120L;
	private Integer authId;

	private String creator;
	private String updator;
	private String roleCode;
	private String roleName;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	
	public Integer getAuthId() {
		if(authId==null){
			return super.getId();
		}
		return authId;
	}

	public void setAuthId(Integer authId) {
		this.authId = authId;
	}

}