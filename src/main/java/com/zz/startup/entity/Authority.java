package com.zz.startup.entity;

import com.zz.startup.annotation.Unique;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_auth_authority")
@Entity
@SqlResultSetMapping(
        name = "AuthMapping",
        classes = @ConstructorResult(targetClass = Role.class,
                columns = {
                        @ColumnResult(name = "id", type = Long.class),
                        @ColumnResult(name = "name"),
                        @ColumnResult(name = "permission"),
                        @ColumnResult(name = "summary"),
                        @ColumnResult(name = "create_time", type = Date.class),
                        @ColumnResult(name = "update_time", type = Date.class)
                }
        )
)
public class Authority extends BaseEntity {

    @NotEmpty
    @Unique
    private String name;
    @NotEmpty
    private String permission;
    private String summary;

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

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
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
        Authority a = (Authority) o;
        return a.getId().equals(getId());
    }

    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (null == getId() ? 0 : getId().hashCode());
        return hash;
    }
}
