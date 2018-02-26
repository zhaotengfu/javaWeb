package com.ztf.core.jdbc.entity;


import com.ztf.core.jdbc.annotation.Field;
import com.ztf.core.jdbc.annotation.PK;
import com.ztf.core.jdbc.annotation.Table;

/**
 * Created by ztf on 2018-1-16.
 */
@Table(value = "sys_users")
public class UserEntity {

    @PK
    @Field("id")
    private Integer id;

    private String username;

    private String password;

    private String salt;

    private String locked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }
}
