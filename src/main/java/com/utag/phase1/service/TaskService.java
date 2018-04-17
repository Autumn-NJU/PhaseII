package com.utag.phase1.service;

import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.domain.Task;
import com.utag.phase1.util.Response;
import com.utag.phase1.vo.TaskVO;

import java.util.List;

/**
 * 提供任务的服务
 */
public interface TaskService {
    /**
     *  发布任务
     * @param name
     * @param reward
     * @param requester
     * @return
     */
    Response<Boolean> saveTask(String name, double reward, String requester, int workerLimit,
                             String ddl, String description, String folderName, TagType tagType);
    /**
     * 删除任务
     * @param id
     * @return
     */
    Response<Boolean> deleteTask(int id);

    /**
     * 待定
     * @return
     */
    Response<Boolean> updateTask(int id, double reward, int workerLimit, String ddl, String description);

    /**
     * 领取任务
     * @param id
     * @param worker
     * @return
     */
    Response<Boolean> claimTask(int id, String worker);

    /**
     * 放弃任务
     * @param id
     * @param worker
     * @return
     */
    Response<Boolean> abandonTask(int id, String worker);

    /**
     * 根据发起者名称寻找所有任务
     * @param requester
     * @return
     */
    Response<List<TaskVO>> listTaskByRequester(String requester);

    /**
     * 根据工人名称寻找所有任务
     * @param worker
     * @return
     */
    Response<List<TaskVO>> listTaskByWorker(String worker);


}
