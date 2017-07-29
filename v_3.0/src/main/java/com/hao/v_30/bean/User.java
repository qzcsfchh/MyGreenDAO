package com.hao.v_30.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * Desc:
 * Created by huanghao123 on 7/18 0018.
 */
@Entity
public class User {
    @Id
    private Long id;
    @Unique
    private String key;
    @NotNull
    private String name;

    private int age;

    private Date date;

    @Generated(hash = 668241617)
    public User(Long id, String key, @NotNull String name, int age, Date date) {
        this.id = id;
        this.key = key;
        this.name = name;
        this.age = age;
        this.date = date;
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

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


}
