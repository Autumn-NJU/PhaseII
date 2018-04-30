package com.utag.phase1.service;

import com.utag.phase1.dao.enumeration.TagType;
import com.utag.phase1.domain.Task;
import com.utag.phase1.util.Response;
import com.utag.phase1.vo.TaskVO;
import com.utag.phase1.vo.UserVO;

import java.util.List;

/**
 * 提供任务的服务
 */
public interface TaskService {
    /**
     * 发布任务
     *
     * @param name
     * @param reward
     * @param requester
     * @return
     */
    Response<Boolean> saveTask(String name, double reward, String requester, int workerLimit,
                               String ddl, String description, String folderName, TagType tagType);

    /**
     * 删除任务
     *
     * @param id
     * @return
     */
    Response<Boolean> deleteTask(int id);

    /**
     * 待定
     *
     * @return
     */
    Response<Boolean> updateTask(int id, double reward, int workerLimit, String ddl, String description);

    /**
     * 领取任务
     *
     * @param id
     * @param worker
     * @return
     */
    Response<Boolean> claimTask(int id, String worker);

    /**
     * 放弃任务
     *
     * @param id
     * @param worker
     * @return
     */
    Response<Boolean> abandonTask(int id, String worker);

    /**
     * 根据发起者名称寻找所有任务
     *
     * @param requester
     * @return
     */
    Response<List<TaskVO>> listTaskByRequester(String requester);

    /**
     * 根据工人名称寻找所有任务
     *
     * @param worker
     * @return
     */
    Response<List<TaskVO>> listTaskByWorker(String worker);

    /**
     * 查询所有任务
     *
     * @return
     */
    Response<List<TaskVO>> listAllTask();

    /**
     * 根据id查询任务
     *
     * @return
     */
    Response<TaskVO> getTaskById(int id);

    /**
     * 得到任务数
     *
     * @return
     */
    Response<Integer> getTaskNum();

    /**
     * top5工人
     * @return
     */
    Response<List<UserVO>> listTop5Woker();

    /**
     * top5发布者
     * @return
     */
    Response<List<UserVO>> listTop5Requester();

    /**
     * 每月发布的任务
     * @return
     */
    Response<List<Integer>> listMonthRequestTask();


    /**
     * 每月完成的任务
     * @return
     */
    Response<List<Integer>> listMonthFinishTask();

    /**
     *
     * @return
     */
    Response<List<TaskVO>> listAvailbleTask();

    /**
     * 给出不同分类任务数
     * @return
     */
    Response<List<Integer>> listPartNum();

    /**
     *
     * @return
     */
    Response<Integer> getPartTaskNum();

    /**
     *
     * @return
     */
    Response<Integer> getWholeTaskNum();

    /**
     *
     * @return
     */
    Response<Integer> getRegTaskNum();

    /**
     * 给出标注类型
     * @param taskId
     * @return
     */
    Response<TagType> getTagType(int taskId);
}