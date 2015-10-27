package com.zz.startup.entity;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class Role extends BaseEntity {

	@NotEmpty
	private String roleName;
	@NotEmpty
	private String roleCode;
	private List<String> permissions;
	private List<Authority> authorities;
	
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public List<String> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
	public List<Authority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}

	@Override
	public boolean equals(Object o) {
		if(o == null){
            return false;
        }
        if (o == this){
           return true;
        }
        if (getClass() != o.getClass()){
            return false;
        }
        Role role = (Role)o;
        return (role != null && role.getId().equals(getId()));
	}
	
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (null == getId() ? 0 : getId().hashCode());
		return hash;
	}
}
