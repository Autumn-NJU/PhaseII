package com.utag.phase1.service;

import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.domain.Task;
import com.utag.phase1.util.Response;

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
    Response saveTask(String name, double reward, String requester, int workerLimit,
                             String ddl, String description, List<String> pictureList, TagType tagType);
    /**
     * 删除任务
     * @param id
     * @return
     */
    Response deleteTask(int id);

    /**
     * 待定
     * @return
     */
    Response updateTask(int id, double reward, int workerLimit, String ddl, String description);

    /**
     * 领取任务
     * @param id
     * @param worker
     * @return
     */
    Response claimTask(int id, String worker);

    /**
     * 放弃任务
     * @param id
     * @param worker
     * @return
     */
    Response abandonTask(int id, String worker);

    /**
     * 根据发起者名称寻找所有任务
     * @param requester
     * @return
     */
    Response<List<Task>> listTaskByRequester(String requester);

    /**
     * 根据工人名称寻找所有任务
     * @param worker
     * @return
     */
    Response<List<Task>> listTaskByWorker(String worker);
}
