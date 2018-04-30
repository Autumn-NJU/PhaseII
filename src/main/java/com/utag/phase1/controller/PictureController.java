package com.utag.phase1.controller;

import com.utag.phase1.service.PictureService;
import com.utag.phase1.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 控制单个图片的controller, URL命名以/picture起始
 */
@Controller
@RequestMapping("/picture/")
public class PictureController {
    @Autowired
    PictureService pictureService;

    @RequestMapping(value = "/tag", method = RequestMethod.POST)
    @ResponseBody
    public Response<Boolean> tagPicture(int taskId, String worker, String pictureName){
        return pictureService.tagPicture(taskId, worker, pictureName);
    }


    /**
     * 返回的是 "src/main/resources/static/task/files/" + taskId + "/" + pictureName
     * 如 src/main/resources/static/task/files/1/1.jpg
     *
     * @param taskID
     * @param worker
     * @return
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<String>> listUntaggedPicture(int taskID, String worker){
        return pictureService.listUntaggedPicture(taskID, worker);
    }

    @RequestMapping(value = "/showTagged", method = RequestMethod.GET)
    @ResponseBody
    public Response<List<String>> listTaggedPicture(int taskID, String worker){
        return pictureService.listTaggedPicture(taskID, worker);
    }

    @RequestMapping(value = "/isTagged", method = RequestMethod.GET)
    @ResponseBody
    public Response<Boolean> isTagged(int taskId, String worker, String imageId){
        return pictureService.isTagged(taskId, worker, imageId);
    }


}
