package com.utag.phase1.dao;

//import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import com.utag.phase1.dao.DaoService.UserDao;
import org.junit.Test;
import java.io.IOException;


import static org.junit.Assert.*;

public class UserDaoImplTest {
    UserDao userDao = new UserDaoImpl();
    private static final String str = "admin";
    private static final String str1 = "py";
    private static final String str2 = "pypy233";


    @Test
    public void saveUser() throws IOException{
        assertEquals(userDao.saveUser(str, str), true);

    }

    @Test
    public void deleteUser()  throws IOException{

    }

    @Test
    public void updateUser() throws IOException{


    }

    @Test
    public void canLogin() throws IOException{
        assertEquals(true, userDao.canLogin(str, str));

    }

}