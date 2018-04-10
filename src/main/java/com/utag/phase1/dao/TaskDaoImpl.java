package com.utag.phase1.dao;

import com.utag.phase1.dao.DaoService.TaskDao;
import com.utag.phase1.domain.Task;
import com.utag.phase1.util.DateHelper;
import com.utag.phase1.util.FileTool;
import com.utag.phase1.util.GsonTool;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class TaskDaoImpl implements TaskDao{
    private static final String FILE_NAME = "task.json";

    private ArrayList<Task> init(){
        ArrayList<String> taskList = (ArrayList<String>) FileTool.readFile(FILE_NAME);
        ArrayList<Task> list = new ArrayList<>();
        for(String t: taskList){
            list.add(GsonTool.getBean(t, Task.class));
        }
        return list;
    }


    @Override
    public boolean saveTask(String name, double reward, String requester, String worker, List<String> pictures) {
        int id = 0;
        int size = init().size();

        if(size == 0)
            id = 1;
        else
            id = init().get(size - 1).getId() + 1;

        String date = DateHelper.getDate();
        Task task = new Task(id, name, reward, requester, worker, date, pictures);
        String jsonStr = GsonTool.toJson(task);
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
    public boolean updateTask() {
        return false;
    }

    @Override
    public List<Task> listTask(String user) {
        return null;
    }

    private boolean isTaskExist(int id){
        ArrayList<Task> list = init();
        for(Task t: list)
            if(t.getId() == id)
                return true;
        return false;
    }
}
