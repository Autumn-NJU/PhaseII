package com.utag.phase1.controller;



import com.utag.phase1.dao.enumeration.UserType;
import com.utag.phase1.service.UserService;
import com.utag.phase1.util.Response;
import com.utag.phase1.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/")
    public String getIndex(){
        return "/index.html";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @ResponseBody
    public Response<Boolean> canLogin(String user, String password) throws IOException{
        System.out.println("Get login...");
        return userService.canLogin(user, password);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public Response<Boolean> register(String user, String password, String userType)throws IOException{
        System.out.println("Get Register...");
        UserType role;
        if(userType.equals("Requester"))
            role = UserType.Requester;
        else
            role = UserType.Worker;
        return userService.saveUser(user, password, role);
    }

    @RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<UserVO>> listUser(){
        System.out.println("List user...");
        return userService.listUser();
    }
    /**
     * 得到用户数
     */
    @RequestMapping(value = "/userSize", method = RequestMethod.GET)
    @ResponseBody
    public Response<Integer> getUserNum(){
        try{
            List<UserVO> list = listUser().getData();
            return new Response<>(true, list.size(), "Succeed to get user number!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to get user number!");

        }
    }

    @RequestMapping(value = "/workerSize", method = RequestMethod.GET)
    @ResponseBody
    public Response<Integer> getWorkerNum(){
        return userService.getWorkerNum();
    }

    @RequestMapping(value = "/requesterSize", method = RequestMethod.GET)
    @ResponseBody
    public Response<Integer> getRequetserNum(){
        return userService.getRequesterNum();
    }

    /**
     * 根据用户名返回用户信息
     * @param userName
     * @return
     */
    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    @ResponseBody
    public Response<UserVO> getUserByName(String userName){
        return userService.getUserByName(userName);
    }

}
