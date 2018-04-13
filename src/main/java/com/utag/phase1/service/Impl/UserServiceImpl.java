package com.utag.phase1.service.Impl;

import com.utag.phase1.dao.DaoService.UserDao;
import com.utag.phase1.dao.UserDaoImpl;
import com.utag.phase1.domain.Task;
import com.utag.phase1.service.UserService;
import com.utag.phase1.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;


@Service
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
