package com.utag.phase1.dao.DaoService;

import java.util.List;

public interface PictureDao {
    /**
     *
     * @param taskId
     * @param worker
     * @param pictureName
     * @return
     */
    boolean savePicture(int taskId, String worker, String pictureName);


    /**
     * 图片被标注，在标注的同时更新进度信息
     * @param id
     * @return
     */
    boolean tagPicture(String id);

    /**
     * 给出所有未标注的图片
     * @param taskID
     * @param worker
     * @return
     */
    List<String> listUntaggedPicture(int taskID, String worker);

    /**
     * 给出标注的图片
     * @param taskId
     * @param worker
     * @return
     */
    List<String> listTaggedPicture(int taskId, String worker);

    /**
     * 初始化标注图片，返回图片名的list
     * @return
     */
    List<String> listPictureName(int taskId);

    /**
     * 发布任务的同时保存图片名
     * @return
     */
    boolean savePictureList(int taskID, String worker, List<String> nameList);

    /**
     * 放弃任务时删除图片信息，需要判断是发布者还是工人
     * @param taskID
     * @return
     */
    boolean deletePictureList(int taskID, String abandoner);

    /**
     * 计算对应个人的单个任务标注进程
     * @param taskID
     * @param worker
     * @return
     */
    double calculateProcess(int taskID, String worker);

    /**
     *
     * @param taskId
     * @param worker
     * @param imageId
     * @return
     */
    boolean isTagged(int taskId, String worker, String imageId);


}
