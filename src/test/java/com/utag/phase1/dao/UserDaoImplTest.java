package com.utag.phase1.dao;

import com.utag.phase1.dao.DaoService.UserDao;
import com.utag.phase1.dao.enumeration.UserType;
import com.utag.phase1.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import static org.junit.Assert.*;


public class UserDaoImplTest {
    private UserDao userDao = new UserDaoImpl();

    private static final String str = "admin";
    private static final String str1 = "py";
    private static final String str2 = "pypy233";
    private static final String UPPWD = "012345";
    private static final UserType u1 = UserType.Requester;
    private static final UserType u2 = UserType.Worker;


    @Test
    public void saveUser() {
        assertEquals(true, userDao.saveUser(str, str, u1));
        assertEquals(true, userDao.saveUser(str1, str1, u1));
        assertEquals(true, userDao.saveUser(str2, str2, u2));

    }

    @Test
    public void deleteUser() {
        assertEquals(true, userDao.deleteUser(str));
        assertEquals(true, userDao.deleteUser(str1));
        assertEquals(true, userDao.deleteUser(str2));

    }

    @Test
    public void updateUser() {
        assertEquals(true, userDao.updateUser(str, UPPWD));
        assertEquals(true, userDao.updateUser(str1, UPPWD));
        assertEquals(true, userDao.updateUser(str2, UPPWD));
      
    }

    @Test
    public void canLogin() {
        assertEquals(false, userDao.canLogin(str, str));
    }

    @Test
    public void getUserByName(){
        User user = userDao.getUserByName(str);
        System.out.println(user.getPassword());
    }

    @Test
    public void updateProperty(){
        assertEquals(true, userDao.updateProperty(str, 10000));
        assertEquals(false, userDao.updateProperty("", 1));
        assertEquals(false, userDao.updateProperty(null, 1));
        assertEquals(false, userDao.updateProperty("non_exist", 1));
        assertEquals(true, userDao.updateProperty(str1, 10000));
        assertEquals(true, userDao.updateProperty(str2, 10000));
        assertEquals(true, userDao.updateProperty(str, 10000));
    }

    @Test
    public void bePunished(){
        assertEquals(false, userDao.bePunished(""));
        assertEquals(false, userDao.bePunished(null));
        assertEquals(false, userDao.bePunished("non_exist"));
        assertEquals(true, userDao.bePunished(str));
        assertEquals(true, userDao.bePunished(str1));
        assertEquals(true, userDao.bePunished(str2));
    }

    @Test
    public void listUser(){
        assertEquals(3, userDao.listUser().size());
    }

}