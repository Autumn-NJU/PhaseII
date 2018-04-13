package com.utag.phase1.dao;

import com.utag.phase1.dao.DaoService.PictureDao;
import com.utag.phase1.dao.DaoService.TaskDao;
import com.utag.phase1.domain.Picture;
import com.utag.phase1.util.FileTool;
import com.utag.phase1.util.GsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PictureDaoImpl implements PictureDao {

    private static final String FILE_NAME = "pictures.json";

    @Autowired
    private TaskDao taskDao;

    private ArrayList<Picture> init(){
        List<String> pictureStrList = FileTool.readFile(FILE_NAME);
        ArrayList<Picture> pictureList = new ArrayList<>();
        for(String str: pictureStrList){
            pictureList.add(GsonTool.getBean(str, Picture.class));
        }
        return pictureList;
    }


    @Override
    public boolean tagPicture(String id) {
        ArrayList<Picture> list = init();
        ArrayList<String> pictureStrList = new ArrayList<>();
        int taskID = -1;
        String worker = null;
        Map<String, Double> map = new HashMap<>();
        for(Picture p: list){
            if(p.getId().equals(id)){
                p.setTagged(true);
                taskID = p.getTaskID();
                worker = p.getWorker();
            }
            String jsonStr = GsonTool.toJson(p);
            pictureStrList.add(jsonStr);
        }
        double process = taskDao.calculateProcess(taskID, worker);
        return FileTool.rewriteFile(FILE_NAME, pictureStrList) && taskDao.updateProcess(, worker, process);
    }

    @Override
    public List<Picture> listUntaggedPicture(int taskID, String worker) {
        List<Picture> list = new ArrayList<>();
        ArrayList<Picture> pictureList = init();
        for(Picture p: pictureList){
            if(p.getTaskID() == taskID && p.getWorker().equals(worker) && !p.isTagged())
                list.add(p);
        }
        return list;
    }

}
