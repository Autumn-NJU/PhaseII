package com.utag.phase1.service.Impl;

import com.utag.phase1.dao.DaoService.PictureDao;
import com.utag.phase1.dao.DaoService.TaskDao;
import com.utag.phase1.service.PictureService;
import com.utag.phase1.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private TaskDao taskDao;

    @Override
    public Response<Boolean> tagPicture(String id) {
        try{
            pictureDao.tagPicture(id);
            String[] arr = id.split("-");
            int taskID = Integer.parseInt(arr[0]);
            String worker = arr[1];
            double process = taskDao.calculateProcess(taskID, worker);
            taskDao.updateProcess(taskID, worker, process);
            return new Response<>(true, "Succeed to tag picture!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to tag picture!");
        }
    }

    @Override
    public Response<List<String>> listUntaggedPicture(int taskID, String worker) {
        try{
            List<String> strVO = pictureDao.listUntaggedPicture(taskID, worker);
            return new Response<List<String>>(true,  strVO,
                    "Succeed to list untagged picture!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list untagged pictures!");
        }
    }
}
