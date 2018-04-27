package com.utag.phase1.service.Impl;


//import com.sun.source.util.TaskListener;
import com.utag.phase1.dao.DaoService.PictureDao;
import com.utag.phase1.dao.DaoService.TaskDao;
import com.utag.phase1.dao.DaoService.UserDao;
import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.domain.Task;
import com.utag.phase1.domain.User;
import com.utag.phase1.service.TaskService;
import com.utag.phase1.util.*;
import com.utag.phase1.vo.TaskVO;
import com.utag.phase1.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskDao taskDao;
    @Autowired
    UserDao userDao;
    @Autowired
    PictureDao pictureDao;

    private static final String FOLDER_NAME = "images";

    @Override
    public Response<Boolean> saveTask(String name, double reward, String requester, int workerLimit, String ddl,
                             String description, String fileName, TagType tagType) {
        try {
            double property = userDao.getUserByName(requester).getProperty() - reward * workerLimit;
            if(property < 0){
                /**
                 * 不知道写什么为好
                 */
                return new Response<>(false, "Insufficient account balance!");
            }
            userDao.updateProperty(requester, property);
            taskDao.saveTask(name, reward, requester, workerLimit, ddl, description, fileName, tagType);
            return new Response<>(true, "Succeed to save task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to save task!");
        }
    }

    @Override
    public Response<Boolean> deleteTask(int id) {
        try{
            taskDao.deleteTask(id);
            return new Response<>(true, "Succeed to delete task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(true, "Fail to delete task!");
        }

    }

    @Override
    public Response<Boolean> updateTask(int id, double reward, int workerLimit, String ddl,
                                        String description) {
        try{
            taskDao.updateTask(id, reward, workerLimit, ddl, description);
            return new Response<>(true, "Succeed to update task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to update task!");
        }

    }

    @Override
    public Response<Boolean> claimTask(int id, String worker) {
        try{
            taskDao.claimTask(id, worker);
            pictureDao.savePictureList(id, worker, FileTool.listPictureName(FOLDER_NAME));
            return new Response<>(true, "Succeed to claim task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to claim task!");
        }
    }

    @Override
    public Response<Boolean> abandonTask(int id, String worker) {
       try{
           taskDao.abandonTask(id, worker);
           userDao.bePunished(worker);
           return new Response<>(true, "Succeed to abandon task!");
       }catch (Exception ex){
           ex.printStackTrace();
           return new Response<>(false, "Fail to abandon task!");
       }
    }

    @Override
    public Response<List<TaskVO>> listTaskByRequester(String requester) {
        try {
            List<TaskVO> list = new ArrayList<>();
            List<Task> doList = taskDao.listTaskByRequester(requester);
            for (Task t : doList) {
                list.add(TransVO.transTaskVO(t));
            }
            return new Response<>(true, list,
                    "Succeed to list task by requester!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list task by requester!");
        }
    }

    @Override
    public Response<List<TaskVO>> listTaskByWorker(String worker) {
        try{
            List<TaskVO> list = new ArrayList<>();
            List<Task> doList = taskDao.listTaskByWorker(worker);
            for (Task t : doList) {
                list.add(TransVO.transTaskVO(t));
            }
            return new Response<>(true, list,
                    "Succeed to list task by worker!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list task by worker!");
        }
    }

    @Override
    public Response<List<TaskVO>> listAllTask() {
        try{
            return new Response<>(true, taskDao.listAllTask(), "Succeed to list all task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list all task!");
        }
    }

    @Override
    public Response<TaskVO> getTaskById(int id) {
        try {
            TaskVO taskVO = TransVO.transTaskVO(taskDao.getTaskById(id));
            return new Response<>(true, taskVO, "Succeed to get task");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to get task!");
        }    }

    @Override
    public Response<Integer> getTaskNum() {
        try{
            int num = taskDao.getTaskNum();
            return new Response<>(true, num, "Succeed to get task number!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to get task number!");
        }
    }

    @Override
    public Response<List<UserVO>> listTop5Woker() {
        List<User> list = userDao.listUser();
        List<TaskVO> taskList = taskDao.listAllTask();

        List<UserVO> list1 = new ArrayList<>();

        Map<String, Integer> workerMap = new TreeMap<>();
        for(User u: list){
            workerMap.put(u.getUsername(), 0);
        }
        for(TaskVO t: taskList){
            List<String> workerList = t.getWorkerList();
            if(workerList != null) {
                for (String name : workerList) {
                    workerMap.put(name, workerMap.get(name) + 1);
                }
            }
        }
        System.out.println("......");
        Map<String, Integer> sortedMap = QuickSortUtil.sortByValue(workerMap);
        System.out.println("......");
        for(Map.Entry<String, Integer> entry: sortedMap.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("....");

        if(workerMap.size() < 5){
            for(Map.Entry<String, Integer> entry: sortedMap.entrySet()){
                String name = entry.getKey();
                User user = userDao.getUserByName(name);
                list1.add(TransVO.transUserVO(user));
            }
        }else {
            int count = 0;
            for(Map.Entry<String, Integer> entry: sortedMap.entrySet()){
                if(count >= workerMap.size() - 5){
                    String name = entry.getKey();
                    User user = userDao.getUserByName(name);
                    list1.add(TransVO.transUserVO(user));
                }
                count++;
            }
        }
            try {
                return new Response<>(true, list1, "Succeed to list top5 workers");
            }catch (Exception ex){
                ex.printStackTrace();
                return new Response<>(false, "Fail to list top5 workers");
            }
    }

    @Override
    public Response<List<UserVO>> listTop5Requester() {
        List<User> list = userDao.listUser();
        List<TaskVO> taskList = taskDao.listAllTask();

        List<UserVO> list1 = new ArrayList<>();

        Map<String, Integer> requesterMap = new TreeMap<>();
        for(User u: list){
            requesterMap.put(u.getUsername(), 0);
        }
        for(TaskVO t: taskList) {
            String requester = t.getRequester();
            requesterMap.put(requester, requesterMap.get(requester) + 1);
        }

        System.out.println("......");
        Map<String, Integer> sortedMap = QuickSortUtil.sortByValue(requesterMap);
        System.out.println("......");
        for(Map.Entry<String, Integer> entry: sortedMap.entrySet()){
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        System.out.println("....");

        if(requesterMap.size() < 5){
            for(Map.Entry<String, Integer> entry: sortedMap.entrySet()){
                String name = entry.getKey();
                User user = userDao.getUserByName(name);
                list1.add(TransVO.transUserVO(user));
            }
        }else {
            int count = 0;
            for(Map.Entry<String, Integer> entry: sortedMap.entrySet()){
                if(count >= requesterMap.size() - 5){
                    String name = entry.getKey();
                    User user = userDao.getUserByName(name);
                    list1.add(TransVO.transUserVO(user));
                }
                count++;
            }
        }
        try {
            return new Response<>(true, list1, "Succeed to list top5 workers");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list top5 workers");
        }
    }

    @Override
    public Response<List<Integer>> listMonthRequestTask() {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            list.add(0);
        }
        for(TaskVO t: taskDao.listAllTask()){
            String date = t.getBeginDate();
            int m = DateHelper.getMonth(date, 5, 7);
            int idx = m - 1;    // 确认过的眼神, 0 - 11
            int val = list.get(idx) + 1;
            list.set(idx, val);
        }
        try{
            return new Response<>(true, list, "Succeed to list month request task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list month request task!");
        }
    }

    @Override
    public Response<List<Integer>> listMonthFinishTask() {
        List<Integer> list = new ArrayList<>();
        for(int i = 0; i < 12; i++){
            list.add(0);
        }
        for(TaskVO t: taskDao.listAllTask()){
            String date = t.getDdl();
            int m = DateHelper.getMonth(date, 0, 2);
            int idx = m - 1;    // 确认过的眼神, 0 - 11
            int val = list.get(idx) + 1;
            list.set(idx, val);
        }
        try{
            return new Response<>(true, list, "Succeed to list month request task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list month request task!");
        }
    }

    @Override
    public Response<List<TaskVO>> listAvailbleTask() {
        List<TaskVO> list = new ArrayList<>();
        for(Task t: taskDao.listAvailableTask()){
            list.add(TransVO.transTaskVO(t));
        }
        try {
            return new Response<>(true, list, "Succeed to list available task!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list available task!");
        }
    }

    @Override
    public Response<List<Integer>> listPartNum() {
        try{
            return new Response<>(true, taskDao.listPartNum(), "Succeed to list part num!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to list part num!");
        }
    }

    @Override
    public Response<Integer> getPartTaskNum() {
        try {
            return new Response<>(true, taskDao.getPartTaskNum(), "Succeed to get part task number!");
        }catch (Exception ex){
            ex.printStackTrace();
            return new Response<>(false, "Fail to get part task number!");
        }
    }

    @Override
    public Response<Integer> getWholeTaskNum() {
        try {
            return new Response<>(true, taskDao.getWholeTaskNum(), "Succeed to get whole task number!");
        }catch (Exception ex) {
            ex.printStackTrace();
            return new Response<>(false, "Fail to get whole task number!");
        }
    }

    @Override
    public Response<Integer> getRegTaskNum() {
        try {
            return new Response<>(true, taskDao.getRegTaskNum(), "Succeed to get split task number!");
        }catch (Exception ex) {
            ex.printStackTrace();
            return new Response<>(false, "Fail to get split task number!");
        }
    }

}
