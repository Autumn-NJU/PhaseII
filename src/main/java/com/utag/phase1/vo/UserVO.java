package com.utag.phase1.vo;

import com.utag.phase1.dao.enumeration.UserType;

import java.io.Serializable;

/**
 * 用户的VO模型
 */
public class UserVO implements Serializable {
    /**
     *用户名
     */
    private String username;

    /**
     *密码
     */
    private String password;

    /**
     * 信用值
     */
    private int credit;

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

    /**
     * 会员值
     */
    private double vip;

    /**
     * 用户类型
     */
    private UserType userType;


    public UserVO() {
    }

    public UserVO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserVO(String username, String password, double property, int level, double experience) {
        this.username = username;
        this.password = password;
        this.property = property;
        this.level = level;
        this.experience = experience;
    }

    public UserVO(String username, String password, int credit, double property,
                int level, double experience, double vip, UserType userType) {
        this.username = username;
        this.password = password;
        this.credit = credit;
        this.property = property;
        this.level = level;
        this.experience = experience;
        this.vip = vip;
        this.userType = userType;
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

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public double getVip() {
        return vip;
    }

    public void setVip(double vip) {
        this.vip = vip;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
