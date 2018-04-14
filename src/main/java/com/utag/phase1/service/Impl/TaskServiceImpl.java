package com.utag.phase1.service.Impl;

import com.utag.phase1.dao.DaoService.TaskDao;
import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.domain.Task;
import com.utag.phase1.service.TaskService;
import com.utag.phase1.util.Response;
import com.utag.phase1.util.TransVO;
import com.utag.phase1.vo.TaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskDao taskDao;

    @Override
    public Response<Boolean> saveTask(String name, double reward, String requester, int workerLimit, String ddl,
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
    public Response<Boolean> deleteTask(int id) {
        try{
            taskDao.deleteTask(id);
            return new Response(true, "Succeed to delete task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response(true, "Fail to delete task!");
        }

    }

    @Override
    public Response<Boolean> updateTask(int id, double reward, int workerLimit, String ddl, String description) {
        try{
            taskDao.updateTask(id, reward, workerLimit, ddl, description);
            return new Response(true, "Succeed to update task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response(false, "Fail to update task!");
        }

    }

    @Override
    public Response<Boolean> claimTask(int id, String worker) {
        try{
            taskDao.claimTask(id, worker);
            return new Response(true, "Succeed to claim task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response(false, "Fail to claim task!");
        }

    }

    @Override
    public Response<Boolean> abandonTask(int id, String worker) {
       try{
           taskDao.abandonTask(id, worker);
           return new Response<>(true, "Succeed to abandon task!");
       }catch (Exception ex){
           ex.printStackTrace();
           return new Response<>(false, "Fail to abandon task!");
       }
    }

    @Override
    public Response<List<TaskVO>> listTaskByRequester(String requester) {
        try {
            List<TaskVO> list = new ArrayList<>();
            List<Task> doList = taskDao.listTaskByRequester(requester);
            for (Task t : doList) {
                list.add(TransVO.transTaskVO(t));
            }
            return new Response<List<TaskVO>>(true, list,
                    "Succeed to list task by requester!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list task by requester!");
        }
    }

    @Override
    public Response<List<TaskVO>> listTaskByWorker(String worker) {
        try{
            List<TaskVO> list = new ArrayList<>();
            List<Task> doList = taskDao.listTaskByWorker(worker);
            for (Task t : doList) {
                list.add(TransVO.transTaskVO(t));
            }
            return new Response<List<TaskVO>>(true, list,
                    "Succeed to list task by worker!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list task by worker!");
        }
    }

}
