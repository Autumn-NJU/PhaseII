package com.utag.phase1.controller;

import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.service.TaskService;
import com.utag.phase1.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @RequestMapping("/save")
    @ResponseBody
    public Response<Boolean> saveTask(String name, double reward, String requester, int workerLimit, String ddl,
                                      String description, String folderName, TagType tagType){
        return taskService.saveTask(name, reward, requester, workerLimit, ddl,
                description, folderName, tagType);

    }
    

}
