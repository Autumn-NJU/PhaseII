package com.utag.phase1.dao.DaoService;

import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.domain.Task;
import com.utag.phase1.domain.User;
import com.utag.phase1.vo.TaskVO;

import java.util.List;
import java.util.Map;

public interface TaskDao {
    /**
     *  发布任务
     * @param name
     * @param reward
     * @param requester
     * @return
     */
    public boolean saveTask(String name, double reward, String requester, int workerLimit,
                            String ddl, String description, String pictureFolder, TagType tagType);
    /**
     * 删除任务
     * @param id
     * @return
     */
    boolean deleteTask(int id);

    /**
     * 待定
     * @return
     */
    boolean updateTask(int id, double reward, int workerLimit, String ddl, String description);

    /**
     * 领取任务
     * @param id
     * @param worker
     * @return
     */
    boolean claimTask(int id, String worker);

    /**
     * 放弃任务
     * @param id
     * @param worker
     * @return
     */
    boolean abandonTask(int id, String worker);

    /**
     * 提交完成的任务
     * @param id
     * @param worker
     * @return
     */
    boolean submitTask(int id, String worker);

    /**
     * 根据发起者名称寻找所有任务
     * @param requester
     * @return
     */
    List<Task> listTaskByRequester(String requester);

    /**
     * 根据工人名称寻找所有任务
     * @param worker
     * @return
     */
    List<Task> listTaskByWorker(String worker);


    /**
     * 更新进度信息
     * @param taskID
     * @param worker
     * @param val
     * @return
     */
    boolean updateProcess(int taskID, String worker, double val);

    /**
     * 返回所有任务
     * @return
     */
    List<TaskVO> listAllTask();

    /**
     *根据任务id返回任务信息
     * @param id
     * @return
     */
    Task getTaskById(int id);

    /**
     * 得到所有任务数
     * @return
     */
    int getTaskNum();

    /**
     * 未超出人数的可领任务
     * @return
     */
    List<Task> listAvailableTask();

    /**
     *
     * @return
     */
    List<Integer> listPartNum();

    /**
     *
     * @return
     */
    int getPartTaskNum();

    /**
     *
     * @return
     */
    int getWholeTaskNum();

    /**
     *
     * @return
     */
    int getRegTaskNum();

}
