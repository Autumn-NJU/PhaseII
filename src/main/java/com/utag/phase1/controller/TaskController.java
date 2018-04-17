package com.utag.phase1.controller;
/**
 * 任务的controller，控制复杂逻辑，http request
 */

import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.service.TaskService;
import com.utag.phase1.util.Response;
import com.utag.phase1.vo.TaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


@Controller
@RequestMapping("/task/")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @RequestMapping(value = "save", method = RequestMethod.POST)
    @ResponseBody
    public Response<Boolean> saveTask(String name, double reward, String requester, int workerLimit, String ddl,
                                      String description, String folderName, TagType tagType){
        return taskService.saveTask(name, reward, requester, workerLimit, ddl,
                description, folderName, tagType);

    }

    @RequestMapping(value = "delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response<Boolean> deleteTask(int id){
        return taskService.deleteTask(id);
    }

    @RequestMapping(value = "update", method = RequestMethod.PUT)
    @ResponseBody
    public Response<Boolean> updateTask(int id, double reward, int workerLimit, String ddl, String description){
        return taskService.updateTask(id, reward, workerLimit, ddl, description);
    }

    @RequestMapping(value = "claim", method = RequestMethod.POST)
    @ResponseBody
    public Response<Boolean> claimTask(int id, String worker){
        return taskService.claimTask(id, worker);
    }

    @RequestMapping(value = "abandon", method = RequestMethod.POST)
    @ResponseBody
    public Response<Boolean> abandonTask(int id, String worker){
        return taskService.abandonTask(id, worker);
    }

    @RequestMapping(value = "find/requester", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<TaskVO>> listTaskByRequester(String requester){
        return taskService.listTaskByRequester(requester);
    }

    @RequestMapping(value = "find/worker", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<TaskVO>> listTaskByWorker(String worker){
        return taskService.listTaskByWorker(worker);
    }


}
