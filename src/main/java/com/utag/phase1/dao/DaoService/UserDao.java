package com.utag.phase1.dao.DaoService;


import com.utag.phase1.domain.User;

import java.io.IOException;

/**
 * 用户的数据层
 */
public interface UserDao {
    /**
     *增加用户
     * @return
     */
    boolean saveUser(String user, String password);

    /**
     *删除用户
     * @return
     */
    boolean deleteUser(String user);

    /**
     *更新用户信息
     * @return
     */
    boolean updateUser(String user, String password);

    /**
     * 根据姓名查找用户
     * @param user
     * @return
     */
    User getUserByName(String user);


    /**
     *判断能否登录
     * @return
     */
    boolean canLogin(String user, String password);

    /**
     * 更新财产
     * @param user
     * @param property
     * @return
     */
    boolean updateProperty(String user, double property);

    /**
     * 放弃任务的惩罚
     * @param user
     * @return
     */
    boolean bePunished(String user);


}
