package com.utag.phase1.service.Impl;

import com.utag.phase1.dao.DaoService.PictureDao;
import com.utag.phase1.dao.DaoService.TaskDao;
import com.utag.phase1.service.PictureService;
import com.utag.phase1.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    @Autowired
    private PictureDao pictureDao;
    @Autowired
    private TaskDao taskDao;

    @Override
    public Response<Boolean> tagPicture(int taskId, String worker, String pictureName) {
        try{
            String id = taskId + "-" + worker + "-" + pictureName;
            pictureDao.tagPicture(id);
            double process = pictureDao.calculateProcess(taskId, worker);
            taskDao.updateProcess(taskId, worker, process);
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
            return new Response<>(true, strVO,
                    "Succeed to list untagged picture!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list untagged pictures!");
        }
    }

    @Override
    public Response<List<String>> listTaggedPicture(int taskID, String worker) {
        try{
            List<String> strVO = pictureDao.listTaggedPicture(taskID, worker);
            return new Response<>(true, strVO,
                    "Succeed to list tagged picture!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list tagged pictures!");
        }
    }

    @Override
    public Response<Boolean> isTagged(int taskId, String worker, String imageId) {
        try {
            pictureDao.isTagged(taskId, worker, imageId);
            return new Response<>(true, "Succeed to get tag or whether!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail yo tag or whether!");
        }
    }
}
