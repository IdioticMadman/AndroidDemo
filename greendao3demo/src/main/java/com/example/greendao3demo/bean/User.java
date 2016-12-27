package com.example.greendao3demo.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 在此写用途
 *
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.greendao3demo.bean.User.java
 * @author: robert
 * @date: 2016-12-05 11:56
 */
@Entity
public class User {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    @NotNull
    private String userId;
    private String userName;
    @Generated(hash = 1513057349)
    public User(Long id, @NotNull String userId, String userName) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
    }
    @Generated(hash = 586692638)
    public User() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserId() {
        return this.userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return this.userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
