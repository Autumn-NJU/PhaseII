package com.utag.phase1.dao;

import com.utag.phase1.dao.DaoService.UserDao;
import com.utag.phase1.domain.User;
import com.utag.phase1.util.FileTool;
import com.utag.phase1.util.GsonTool;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserDaoImpl  implements UserDao {
    private static final String FILE_NAME = "user.json";
    /**
     * 任务的经验和奖励, 暂定为20, 10
     */
    private static final double EXPERIENCE = 20;
    private static final int CREDIT = 10;


    private ArrayList<User> init() {
        ArrayList<String> userStrList = (ArrayList<String>) FileTool.readFile(FILE_NAME);
        ArrayList<User> userList = new ArrayList<>();

        for(String str: userStrList){
            userList.add(GsonTool.getBean(str, User.class));
        }

        return userList;
    }

    @Override
    public boolean saveUser(String user, String password) {
        User user1 = new User(user, password);
        String jsonStr = GsonTool.toJson(user1);
        return !(isUserExist(user)) && FileTool.writeFile(FILE_NAME, jsonStr);
    }

    @Override
    public boolean deleteUser(String user) {
        if(!isUserExist(user))
            return false;
        ArrayList<User> userList = init();
        ArrayList<String> contentList = new ArrayList<>();

        for(User u: userList){
            if(!u.getUsername().equals(user)) {
                User user1 = new User(u.getUsername(), u.getPassword());
                String jsonStr = GsonTool.toJson(user1);
                contentList.add(jsonStr);
            }
        }

        return FileTool.rewriteFile(FILE_NAME, contentList);
    }

    @Override
    public boolean updateUser(String user, String password) {
        if(!isUserExist(user))
            return false;
        ArrayList<User> userList = init();
        ArrayList<String> contentList = new ArrayList<>();

        for(User u: userList){
            if(!u.getUsername().equals(user)) {
                contentList.add(GsonTool.toJson(new User(u.getUsername(), u.getPassword())));
            }
            else{
                contentList.add(GsonTool.toJson(new User(u.getUsername(), password)));
            }
        }

        return FileTool.rewriteFile(FILE_NAME, contentList);
    }

    @Override
    public User getUserByName(String user) {
        for(User u: init()){
            if(u.getUsername().equals(user))
                return u;
        }
        return null;
    }

    @Override
    public boolean canLogin(String user, String password) {
        if(!isUserExist(user))
            return false;

        ArrayList<String> userStrList = (ArrayList<String>) FileTool.readFile(FILE_NAME);

        for(String userStr: userStrList){
            User user1 = GsonTool.getBean(userStr, User.class);
            if(user1.getUsername().equals(user) && user1.getPassword().equals(password))
                return true;
        }
        return false;
    }

    @Override
    public boolean updateProperty(String user, double property) {
        ArrayList<String> strList = new ArrayList<>();
        for(User u: init()){
            if(u.getUsername().equals(user)){
                u.setProperty(property);
                u.setCredit(u.getCredit() + CREDIT);
                u.setExperience(u.getExperience() + EXPERIENCE);
            }
            strList.add(GsonTool.toJson(u));
        }
        return FileTool.rewriteFile(FILE_NAME, strList);
    }

    @Override
    public boolean bePunished(String user) {
        ArrayList<String> strList = new ArrayList<>();
        for(User u: init()){
            if(u.getUsername().equals(user)){
                u.setCredit(u.getCredit() - CREDIT);
            }
            strList.add(GsonTool.toJson(u));
        }
        return FileTool.rewriteFile(FILE_NAME, strList);
    }


    private boolean isUserExist(String user) {
        ArrayList<User> userList = init();
        for(User u: userList){
            if(u.getUsername().equals(user))
                return true;
        }
        return false;
    }

}
