package com.utag.phase1.service.Impl;

import com.utag.phase1.dao.DaoService.TaskDao;
import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.domain.Task;
import com.utag.phase1.service.TaskService;
import com.utag.phase1.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public Response saveTask(String name, double reward, String requester, int workerLimit, String ddl,
                             String description, List<String> pictureList, TagType tagType) {
        try {
            taskDao.saveTask(name, reward, requester, workerLimit, ddl, description, pictureList, tagType);
            return new Response(true, "Succeed to save task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response(false, "Fail to save task!");
        }
    }

    @Override
    public Response deleteTask(int id) {
        try{
            taskDao.deleteTask(id);
            return new Response(true, "Succeed to delete task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response(true, "Fail to delete task!");
        }

    }

    @Override
    public Response updateTask(int id, double reward, int workerLimit, String ddl, String description) {
        try{
            taskDao.updateTask(id, reward, workerLimit, ddl, description);
            return new Response(true, "Succeed to update task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response(false, "Fail to update task!");
        }

    }

    @Override
    public Response claimTask(int id, String worker) {
        try{
            taskDao.claimTask(id, worker);
            return new Response(true, "Succeed to claim task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response(false, "Fail to claim task!");
        }

    }

    @Override
    public Response abandonTask(int id, String worker) {
        return null;
    }

    @Override
    public Response<List<Task>> listTaskByRequester(String requester) {

        return null;
    }

    @Override
    public Response<List<Task>> listTaskByWorker(String worker) {
        return null;
    }

}
