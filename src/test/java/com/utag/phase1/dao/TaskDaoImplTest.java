package com.utag.phase1.dao;

import com.utag.phase1.dao.DaoService.TaskDao;
import com.utag.phase1.dao.DaoService.UserDao;
import com.utag.phase1.dao.enumeration.TagType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class TaskDaoImplTest {
    private TaskDao taskDao =  new TaskDaoImpl();
    private UserDao userDao =  new UserDaoImpl();

    private static final String FOLDER_NAME = "images";
    private static final String str = "admin";
    private static final String pyStr = "py";
    private static final String pyStr1 = "pypy233";

    @Test
    public void saveTask() {
       assertEquals(true, taskDao.saveTask("任务2",
               2.33333, str, 10, "2018-10-11 19:19:19",
               "task2", FOLDER_NAME, TagType.Whole));
    }

    @Test
    public void deleteTask() {
        assertEquals(true, taskDao.deleteTask(1));
    }

    @Test
    public void updateTask() {
        assertEquals(true, taskDao.updateTask(4, 10000, 100,
                "2018-09-09 19:19:19", "updated"));

    }

    @Test
        public void claimTask(){
        assertEquals(true, taskDao.claimTask(1, str));
        assertEquals(true, taskDao.claimTask(2, str));
        assertEquals(true, taskDao.claimTask(3, str));
        assertEquals(true, taskDao.claimTask(1, pyStr));
        assertEquals(true, taskDao.claimTask(2, pyStr));
        assertEquals(true, taskDao.claimTask(3, pyStr));
    }

    @Test
    public void listTaskByRequester() {
        assertEquals(8, taskDao.listTaskByRequester("py").size());
        assertEquals(2, taskDao.listTaskByRequester("gay宁").size());
    }

    @Test
    public void listTaskByWorker(){
        assertEquals(1, taskDao.listTaskByWorker("worker1").size());
        assertEquals(1, taskDao.listTaskByWorker("worker2").size());
    }

    @Test
    public void listPartNum(){
        List<Integer> list = taskDao.listPartNum();
        assertEquals(4, list.size());
        for (int i = 0; i < 4; i++)
            System.out.println(list.get(i));
    }
}