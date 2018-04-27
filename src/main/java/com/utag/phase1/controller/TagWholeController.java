package com.utag.phase1.controller;

import com.utag.phase1.service.TagWholeService;
import com.utag.phase1.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@RequestMapping("/workplace/whole/")
public class TagWholeController {
    @Autowired
    private TagWholeService tagWholeService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Response <Boolean> saveTagWhole(String imageID, String description) throws IOException{
        return tagWholeService.saveTagWhole(imageID, description);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ResponseBody
    public Response<Boolean> deleteTagWhole(String imageID) throws IOException{
        return tagWholeService.deleteTagWhole(imageID);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ResponseBody
    public Response<Boolean> updateTagWhole(String imageID, String description) throws IOException{
        return tagWholeService.updateTagWhole(imageID, description);
    }

    @RequestMapping(value = "/length", method = RequestMethod.GET)
    @ResponseBody
    public Response<Integer> getDescriptionLength(String imageID) throws IOException{
        return tagWholeService.getDescriptionLength(imageID);
    }
}
