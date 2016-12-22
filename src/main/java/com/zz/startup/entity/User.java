package com.zz.startup.entity;

import com.zz.startup.annotation.Unique;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.List;

@Table(name = "t_auth_user")
@Entity
public class User extends BaseEntity {

    private static final long serialVersionUID = 6478593744952785316L;

    @NotEmpty(message = "用户名不能为空")
    @Unique
    private String username;
    private String password;
    private String salt;

    @Transient
    private List<Role> roles;

    @Transient
    private String plainPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }
}
