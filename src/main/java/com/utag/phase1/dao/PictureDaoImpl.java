package com.utag.phase1.dao;

import com.utag.phase1.dao.DaoService.PictureDao;
import com.utag.phase1.domain.Picture;
import com.utag.phase1.util.FileTool;
import com.utag.phase1.util.GsonTool;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PictureDaoImpl implements PictureDao {

    private static final String FILE_NAME = "pictures.json";
    private static final String FOLDER_NAME = "src/main/resources/static/task/files/";


    private ArrayList<Picture> init(){
        List<String> pictureStrList = FileTool.readFile(FILE_NAME);
        ArrayList<Picture> pictureList = new ArrayList<>();
        for(String str: pictureStrList){
            pictureList.add(GsonTool.getBean(str, Picture.class));
        }
        return pictureList;
    }


    @Override
    public boolean savePicture(int taskId, String worker, String pictureName) {
        Picture p = new Picture(taskId, worker, pictureName);
        String jsonStr = GsonTool.toJson(p);
        return FileTool.writeFile(FILE_NAME, jsonStr);
    }

    @Override
    public boolean tagPicture(String id) {
        ArrayList<Picture> list = init();
        ArrayList<String> pictureStrList = new ArrayList<>();
        int taskID = -1;
        Map<String, Double> map = new HashMap<>();
        for(Picture p: list){
            if(p.getId().equals(id)){
                p.setTagged(true);
                taskID = p.getTaskID();
            }
            String jsonStr = GsonTool.toJson(p);
            pictureStrList.add(jsonStr);
        }

        return taskID != -1 && FileTool.rewriteFile(FILE_NAME, pictureStrList);
    }

    @Override
    public List<String> listUntaggedPicture(int taskID, String worker) {
        List<String> list = new ArrayList<>();
        ArrayList<Picture> pictureList = init();
        for(Picture p: pictureList){
            if(p.getTaskID() == taskID && p.getWorker().equals(worker) && !p.isTagged())
                list.add(FOLDER_NAME + taskID + "/" + p.getImageID());
        }
        return list;
    }

    @Override
    public List<String> listTaggedPicture(int taskId, String worker) {
        List<String> list = new ArrayList<>();
        ArrayList<Picture> pictureList = init();
        for(Picture p: pictureList){
            if(p.getTaskID() == taskId && p.getWorker().equals(worker) && p.isTagged())
                list.add(FOLDER_NAME + taskId + "/" + p.getImageID());
        }
        return list;
    }

    @Override
    public List<String> listPictureName(int taskID) {
        return FileTool.listPictureName(taskID + "");
    }

    @Override
    public boolean savePictureList(int taskID, String worker,List<String> nameList) {
        for(String name: nameList){
            Picture picture = new Picture(taskID, name, worker);
            String str = GsonTool.toJson(picture);
            if(!FileTool.writeFile(FILE_NAME, str))
                return false;
        }
        return true;
    }

    @Override
    public boolean deletePictureList(int taskID, String abandoner) {
        return false;
    }

    @Override
    public double calculateProcess(int taskID, String worker) {
        int sum = 0;
        List<String> list = new ArrayList<>();
        ArrayList<Picture> pictureList = init();
        for(Picture p: pictureList){
            if(p.getTaskID() == taskID && p.getWorker().equals(worker))
                sum++;
        }
        return sum * 1.0 / (listUntaggedPicture(taskID, worker).size() +
                listTaggedPicture(taskID, worker).size());
    }

    @Override
    public boolean isTagged(int taskId, String worker, String imageId) {
        for(Picture p: init()){
            if(p.getWorker().equals(worker) && p.getTaskID() == taskId && p.getImageID().equals(imageId))
                return p.isTagged();
        }
        return false;
    }


}
