package com.utag.phase1.dao;

import com.utag.phase1.dao.DaoService.TaskDao;
import com.utag.phase1.domain.Task;
import org.junit.Test;

import static org.junit.Assert.*;

public class TaskDaoImplTest {
    private TaskDao taskDao = new TaskDaoImpl();

    @Test
    public void saveTask() {
       assertEquals(true, taskDao.saveTask("任务1",
               2.3, "py", "gay宁", null));
    }

    @Test
    public void deleteTask() {
        assertEquals(true, taskDao.deleteTask(1));
    }

    @Test
    public void updateTask() {
    }

    @Test
    public void listTask() {
    }
}