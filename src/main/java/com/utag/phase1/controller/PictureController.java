package com.utag.phase1.controller;

import com.utag.phase1.service.PictureService;
import com.utag.phase1.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 控制单个图片的controller, URL命名以/workplace/picture起始
 */
@Controller
@RequestMapping("/tag")
public class PictureController {
    @Autowired
    PictureService pictureService;

    @RequestMapping("tagoprd")
    @ResponseBody
    public Response<Boolean> tagPicture(String id){
        return pictureService.tagPicture(id);
    }

    @RequestMapping("/show")
    @ResponseBody
    public Response<List<String>> listUntaggedPicture(int taskID, String worker){
        return pictureService.listUntaggedPicture(taskID, worker);
    }

}
