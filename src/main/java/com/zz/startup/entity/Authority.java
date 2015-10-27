package com.zz.startup.entity;

import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

public class Authority extends BaseEntity {

	@NotEmpty
	private String name;
	@NotEmpty
	private String permission;
	private String parentId;
	private List<String> children;
	private String desc;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<String> getChildren() {
		return children;
	}

	public void setChildren(List<String> children) {
		this.children = children;
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
        Authority a = (Authority)o;
        return a.getId().equals(getId());
	}
	
	public int hashCode() {
		int hash = 7;
		hash = 31 * hash + (null == getId() ? 0 : getId().hashCode());
		return hash;
	}
}
