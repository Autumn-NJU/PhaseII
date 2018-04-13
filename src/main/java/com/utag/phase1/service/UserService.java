package com.utag.phase1.service;

import com.utag.phase1.domain.Task;
import com.utag.phase1.domain.User;
import com.utag.phase1.util.Response;

import java.io.IOException;
import java.util.List;

public interface UserService {
    /**
     *
     * @return
     */
    public Response<Boolean> saveUser(String user, String password) throws IOException;


    /**
     *
     * @return
     */
    public Response<Boolean> deleteUser(String user) throws IOException;


    /**
     *
     * @return
     */
    public Response<Boolean> updateUser(String user, String password) throws IOException;


    /**
     *
     * @return
     */
    public Response<Boolean> canLogin(String user, String password) throws IOException;


}
