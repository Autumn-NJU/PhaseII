package com.utag.phase1.service.Impl;

import com.utag.phase1.dao.DaoService.UserDao;
import com.utag.phase1.dao.enumeration.UserType;
import com.utag.phase1.domain.User;
import com.utag.phase1.service.UserService;
import com.utag.phase1.util.Response;
import com.utag.phase1.util.TransVO;
import com.utag.phase1.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
public class UserServiceImpl implements UserService  {
    @Autowired
    private UserDao userDao;


    @Override
    public Response<Boolean> saveUser(String user, String password, UserType userType){
        try{
            userDao.saveUser(user, password, userType);
            return new Response<>(true, "Succeed to register!");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(false, "Fail to register!");
        }
    }

    @Override
    public Response<Boolean> deleteUser(String user) {
        try{
            userDao.deleteUser(user);
            return new Response<>(true, "Succeed to delete user!");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(false, "Fail to delete user!");
        }
    }

    @Override
    public Response<Boolean> updateUser(String user, String password) {
        try {
            userDao.updateUser(user, password);
            return new Response<>(true, "Succeed to update user!");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(false, "Fail to update user!");
        }
    }

    @Override
    public Response<Boolean> canLogin(String user, String password) {
        try{
            return new Response<>(userDao.canLogin(user, password), "Succeed to login!");
        }catch (Exception e){
            e.printStackTrace();
            return new Response<>(false, "Fail to login!");
        }
    }

    @Override
    public Response<List<UserVO>> listUser() {
        try{
            List<UserVO> list = new ArrayList<>();
            for(User u: userDao.listUser()){
                list.add(TransVO.transUserVO(u));
            }
            return new Response<List<UserVO>>( true,  list, "Succeed to list user!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list user!");
        }
    }

    @Override
    public Response<Integer> getWorkerNum() {
        try{
            int num = userDao.getWorkerNum();
            return new Response<>(true, num, "Succeed to get worker num!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to get worker num!");
        }
    }

    @Override
    public Response<Integer> getRequesterNum() {
        try {
            int num = userDao.getRequesterNum();
            return new Response<>(true, num, "Succeed to get requester num!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to get requester num!");
        }
    }

    @Override
    public Response<UserVO> getUserByName(String userName) {
        try{
            User user = userDao.getUserByName(userName);
            UserVO userVO = TransVO.transUserVO(user);
            return new Response<>(true, userVO, "Succeed to get user info!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to get user info!");
        }

    }


}
