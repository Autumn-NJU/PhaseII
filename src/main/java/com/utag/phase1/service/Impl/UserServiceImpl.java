package com.utag.phase1.service.Impl;

import com.utag.phase1.dao.DaoService.UserDao;
import com.utag.phase1.service.UserService;
import com.utag.phase1.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class UserServiceImpl implements UserService  {
    @Autowired
    private UserDao userDao;


    @Override
    public Response<Boolean> saveUser(String user, String password){
        try{
            userDao.saveUser(user, password);
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


}
