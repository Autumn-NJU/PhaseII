package com.utag.phase1.dao;

import com.utag.phase1.dao.DaoService.PictureDao;
import com.utag.phase1.dao.DaoService.TaskDao;
import com.utag.phase1.dao.DaoService.UserDao;
import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.domain.Task;
import com.utag.phase1.util.DateHelper;
import com.utag.phase1.util.FileTool;
import com.utag.phase1.util.GsonTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TaskDaoImpl implements TaskDao{

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private UserDao userDao;

    private static final String FILE_NAME = "task.json";


    /**
     * 中途放弃的惩罚，暂定为20
     */
    private static final int PUNISHMENT = 20;

    private ArrayList<Task> init(){
        ArrayList<String> taskList = (ArrayList<String>) FileTool.readFile(FILE_NAME);
        ArrayList<Task> list = new ArrayList<>();
        for(String t: taskList){
            list.add(GsonTool.getBean(t, Task.class));
        }
        return list;
    }


    private boolean isTaskExist(int id){
        ArrayList<Task> list = init();
        for(Task t: list)
            if(t.getId() == id)
                return true;
        return false;
    }


    @Override
    public boolean saveTask(String name, double reward, String requester, int workerLimit,
                            String ddl, String description, List<String> pictureList,  TagType tagType) {
        int size = init().size();
        int id = init().size() == 0 ? 1 : init().get(size - 1).getId() + 1;
        String beginDate = DateHelper.getDate();
        Task task = new Task(id, name, reward, requester, workerLimit, null,
                beginDate, ddl, description, null, null, pictureList, tagType);
        String jsonStr = GsonTool.toJson(task);
        /**
         * 暂定发布任务直接扣除 (单个奖赏 * 人数)
         */
        double property = userDao.getUserByName(requester).getProperty() - reward * workerLimit;
        return FileTool.writeFile(FILE_NAME, jsonStr) && userDao.updateProperty(requester, property);

    }

    @Override
    public boolean deleteTask(int id) {
        if(!isTaskExist(id))
            return false;
        ArrayList<Task> taskList = init();
        ArrayList<String> strList = new ArrayList<>();
        for(Task t: taskList){
            if(t.getId() != id){
                Task task = t;
                String jsonStr = GsonTool.toJson(task);
                strList.add(jsonStr);
            }
        }
        return FileTool.rewriteFile(FILE_NAME, strList);
    }

    @Override
    public boolean updateTask(int id, double reward, int workerLimit, String ddl, String description) {
        ArrayList<Task> list = init();
        ArrayList<String> strList = new ArrayList<>();
        for(Task t: list){
            if(t.getId() == id){
                t.setReward(reward);
                t.setWorkerLimit(workerLimit);
                t.setDdl(ddl);
                t.setDescription(description);
            }
            String jsonStr = GsonTool.toJson(t);
            strList.add(jsonStr);
        }

        return FileTool.rewriteFile(FILE_NAME, strList);
    }

    @Override
    public boolean claimTask(int id, String worker) {
        ArrayList<Task> list = init();
        ArrayList<String> strList = new ArrayList<>();
        ArrayList<String> workerList = new ArrayList<>();
        for(Task t: list){
            if(t.getId() == id){
                if(t.getWorkerList() != null)
                    workerList = (ArrayList<String>) t.getWorkerList();
                for(String w: workerList){
                    if(w.equals(worker))
                        return false;
                }
                workerList.add(worker);
                Map<String, Double> processMap = t.getProcessMap();
                processMap.put(worker, .0);
                Map<String, Boolean> finishMap = t.getIsFinishedMap();
                finishMap.put(worker, false);
                t.setWorkerList(workerList);
                t.setProcessMap(processMap);
                t.setIsFinishedMap(finishMap);
            }
            String jsonStr = GsonTool.toJson(t);
            strList.add(jsonStr);
        }

        return FileTool.rewriteFile(FILE_NAME, strList);
    }

    @Override
    public boolean abandonTask(int id, String worker) {
        ArrayList<Task> list = init();
        ArrayList<String> strList = new ArrayList<>();
        ArrayList<String> workerList = new ArrayList<>();
        for(Task t: list){
            if(t.getId() == id){
                workerList.remove(worker);
                t.setWorkerList(workerList);
                
            }
            String jsonStr = GsonTool.toJson(t);
            strList.add(jsonStr);
        }

        return FileTool.rewriteFile(FILE_NAME, strList) && userDao.bePunished(worker);
    }

    @Override
    public boolean submitTask(int id, String worker) {
        ArrayList<Task> list = init();
        ArrayList<String> strList = new ArrayList<>();
        Map<String, Boolean> map = new HashMap<>();
        for(Task t: list){
            if(t.getId() == id){
               map = t.getIsFinishedMap();
               map.put(worker, true);
            }
            String jsonStr = GsonTool.toJson(t);
            strList.add(jsonStr);
        }

        return FileTool.rewriteFile(FILE_NAME, strList);
    }

    @Override
    public List<Task> listTaskByRequester(String requester) {
        List<Task> list = new ArrayList<>();
        for(Task t: init()){
            if(t.getRequester().equals(requester))
                list.add(t);
        }
        return list;
    }

    @Override
    public List<Task> listTaskByWorker(String worker) {
        List<Task> list = new ArrayList<>();
        for(Task t: init()){
            if(t.getWorkerList() != null){
                for(String workerStr: t.getWorkerList()) {
                    if (workerStr.equals(worker)) {
                        list.add(t);
                    }
                }
            }
        }
        return list;
    }


    @Override
    public double calculateProcess(int id, String worker){
        Task task = new Task();
        for(Task t: init()){
            if(t.getId() == id){
                task = t;
                break;
            }
        }
        int sum = task.getPictureList().size();
        int taggedSize = pictureDao.listUntaggedPicture(id, worker).size();
        return taggedSize * 1.0 / sum;
    }

    @Override
    public boolean updateProcess(int taskID, String worker, double val){
        Map<String, Double> processMap = new HashMap<>();
        ArrayList<String> strList = new ArrayList<>();
        for(Task t: init()){
            if(t.getId() == taskID) {
                processMap = t.getProcessMap();
                for(Map.Entry<String, Double> m: processMap.entrySet()){
                    if(m.getKey().equals(worker)) {
                        m.setValue(val);
                        t.setProcessMap(processMap);
                    }
                }
            }
            String jsonStr = GsonTool.toJson(t);
            strList.add(jsonStr);
        }

        return FileTool.rewriteFile(FILE_NAME, strList);
    }

}
