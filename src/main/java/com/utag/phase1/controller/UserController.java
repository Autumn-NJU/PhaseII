package com.utag.phase1.controller;



import com.utag.phase1.service.UserService;
import com.utag.phase1.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


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
    public Response<Boolean> register(String user, String password)throws IOException{
        System.out.println("Get Register...");
        return userService.saveUser(user, password);
    }


}
