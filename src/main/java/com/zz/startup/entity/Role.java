package com.zz.startup.entity;

import com.zz.startup.annotation.Unique;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Table(name = "t_auth_role")
@Entity
@SqlResultSetMapping(
        name = "RoleMapping",
        classes = @ConstructorResult(targetClass = Role.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "name"),
                        @ColumnResult(name = "summary"),
                        @ColumnResult(name = "create_time", type = Date.class),
                        @ColumnResult(name = "update_time", type = Date.class)
                }
        )
)
public class Role extends BaseEntity {

    @NotBlank
    @Unique
    private String name;
    private String summary;

    @Transient
    private boolean checked;
    @Transient
    private List<Authority> authorities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Role role = (Role) o;
        return role.getId().equals(getId());
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (null == getId() ? 0 : getId().hashCode());
        return hash;
    }
}
