package com.utag.phase1.domain;

/**
 * 用户信息的持久类
 */
import java.io.Serializable;

public class User implements Serializable {
    /**
     *用户名
     */
    private String username;

    /**
     *密码
     */
    private String password;

    /**
     * 财产
     */
    private double property;

    /**
     * 级别
     */
    private int level;

    /**
     * 经验值
     */
    private double experience;



    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, double property, int level, double experience) {
        this.username = username;
        this.password = password;
        this.property = property;
        this.level = level;
        this.experience = experience;
    }

    public double getProperty() {
        return property;
    }

    public void setProperty(double property) {
        this.property = property;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getExperience() {
        return experience;
    }

    public void setExperience(double experience) {
        this.experience = experience;
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
}
