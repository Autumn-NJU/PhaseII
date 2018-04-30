package com.utag.phase1.service;

import com.utag.phase1.util.Response;

import java.util.List;

public interface PictureService {

    /**
     * 图片被标注，在标注的同时更新进度信息
     * @param
     * @return
     */
    Response<Boolean> tagPicture(int taskId, String worker, String pictureName);

    /**
     * 给出所有未标注的图片
     * @param taskID
     * @param worker
     * @return
     */
    Response<List<String>> listUntaggedPicture(int taskID, String worker);

    /**
     * 给出所有标注的图片
     * @param taskID
     * @param worker
     * @return
     */
    Response<List<String>> listTaggedPicture(int taskID, String worker);



    Response<Boolean> isTagged(int taskId, String worker, String imageId);
}
