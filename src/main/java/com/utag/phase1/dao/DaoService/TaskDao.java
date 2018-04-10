package com.utag.phase1.dao.DaoService;

import com.utag.phase1.domain.Task;

import java.util.Date;
import java.util.List;

public interface TaskDao {
    /**
     *  发布任务
     * @param name
     * @param reward
     * @param requester
     * @return
     */
    public boolean saveTask(String name, double reward, String requester, String worker, List<String> pictures);

    /**
     * 删除任务
     * @param id
     * @return
     */
    public boolean deleteTask(int id);

    /**
     * 待定
     * @return
     */
    public boolean updateTask();

    /**
     * 根据名称寻找所有任务
     * @param user
     * @return
     */
    public List<Task> listTask(String user);


}
