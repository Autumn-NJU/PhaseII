package com.utag.phase1.service;

import com.utag.phase1.dao.enumeration.UserType;
import com.utag.phase1.domain.Task;
import com.utag.phase1.domain.User;
import com.utag.phase1.util.Response;
import com.utag.phase1.vo.UserVO;

import java.io.IOException;
import java.util.List;

public interface UserService {
    /**
     *
     * @return
     */
    Response<Boolean> saveUser(String user, String password, UserType userType) throws IOException;


    /**
     *
     * @return
     */
    Response<Boolean> deleteUser(String user);


    /**
     *
     * @return
     */
    Response<Boolean> updateUser(String user, String password);


    /**
     *
     * @return
     */
    Response<Boolean> canLogin(String user, String password);


    /**
     * 获取用户列表
     * @return
     */
    Response<List<UserVO>> listUser();

    /**
     * 得到工人数
     * @return
     */
    Response<Integer> getWorkerNum();

    /**
     * 得到发布者数
     * @return
     */
    Response<Integer> getRequesterNum();

    /**
     * 根据用户名返回用户信息
     * @param userName
     * @return
     */
    Response<UserVO> getUserByName(String userName);
}
