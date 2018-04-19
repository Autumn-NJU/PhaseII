package com.utag.phase1.service.Impl;


import com.utag.phase1.dao.DaoService.TaskDao;
import com.utag.phase1.dao.DaoService.UserDao;
import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TaskServiceImplTest {
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserDao userDao;
    private TaskDao taskDao;

    private static final String FOLDER_NAME = "images";
    private static final String str = "admin";

    @Test
    public void saveTask() {
        System.out.println(userDao.getUserByName(str).getProperty());
        taskService.saveTask("任务1",
                2.3, str, 10, "2018-10-11 18:19:34",
                "task1", FOLDER_NAME, TagType.Whole);
    }

    @Test
    public void deleteTask() {

    }

    @Test
    public void updateTask() {

    }

    @Test
    public void claimTask() {

    }

    @Test
    public void abandonTask() {

    }

    @Test
    public void listTaskByRequester() {
    }

    @Test
    public void listTaskByWorker() {
    }
}