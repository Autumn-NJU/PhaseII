package com.utag.phase1.dao;


import com.utag.phase1.dao.DaoService.TaskDao;
import com.utag.phase1.dao.DaoService.UserDao;
import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.domain.Task;

import com.utag.phase1.util.*;
import com.utag.phase1.vo.TaskVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class TaskDaoImpl implements TaskDao{
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
                            String ddl, String description, String fileName, TagType tagType) {
        String fullFileName = "src/main/resources/static/task/zip/" + fileName;
        System.out.println(fileName);
        System.out.println("Full: " + fullFileName);
        int id = getId() + 1;
        UnZipUtil.Unzip(fileName, id);

        String beginDate = DateHelper.getDate();
        Task task = new Task(id, name, reward, requester, workerLimit, null,
                beginDate, ddl, description, null, null,
                FileTool.listPictureName(id + ""), tagType);
        String jsonStr = GsonTool.toJson(task);
        /**
         * 暂定发布任务直接扣除 (单个奖赏 * 人数)
         */

        return FileTool.writeFile(FILE_NAME, jsonStr);

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

        return FileTool.rewriteFile(FILE_NAME, strList);
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
    public boolean updateProcess(int taskID, String worker, double val){
        Map<String, Double> processMap;
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

    @Override
    public List<TaskVO> listAllTask() {
        List<TaskVO> list = new ArrayList<>();
        for(Task t: init()){
            list.add(TransVO.transTaskVO(t));
        }
        return list;
    }

    @Override
    public Task getTaskById(int id) {
        for(Task t: init()){
            if(t.getId() == id){
               return t;
            }
        }
        return null;
    }

    @Override
    public int getTaskNum() {
        return init().size();
    }

    @Override
    public List<Task> listAvailableTask() {
        List<Task> list = new ArrayList<>();
        for(Task t: init()){
            if(t.getWorkerList() == null)
                list.add(t);
            else if(t.getWorkerLimit() >= t.getWorkerList().size())
                list.add(t);
        }
        return list;
    }

    @Override
    public List<Integer> listPartNum() {
        List<Integer> list = new ArrayList<>();
        list.add(init().size());
        for(int i = 0; i < 3; i++){
            list.add(0);
        }


        for(Task t: init()){
            if(t.getTagType().equals(TagType.Part))
                list.set(1, list.get(1) + 1);
            else if(t.getTagType().equals(TagType.Whole))
                list.set(2, list.get(2) + 1);
            else
                list.set(3, list.get(3) + 1);
        }
        return list;
    }

    @Override
    public int getPartTaskNum() {
       return getTaskSepNum(TagType.Part);
    }

    @Override
    public int getWholeTaskNum() {
        return getTaskSepNum(TagType.Whole);
    }

    @Override
    public int getRegTaskNum() {
        return getTaskSepNum(TagType.Split);
    }

    @Override
    public TagType getTagType(int taskId) {
        for(Task t: init()){
            if (t.getId() == taskId)
                return t.getTagType();
        }
        return null;
    }

    private int getTaskSepNum(TagType tagType){
        int count = 0;
        for(Task t: init())
            if(t.getTagType().equals(tagType))
                count++;
        return count;
    }

    @Override
    public int getId(){
        int size = init().size();
        int id = init().size() == 0 ? 1 : init().get(size - 1).getId();
        return id;
    }


}
