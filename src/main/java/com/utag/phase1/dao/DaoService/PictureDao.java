package com.utag.phase1.dao.DaoService;

import com.utag.phase1.domain.Picture;

import java.util.List;

public interface PictureDao {
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
    List<Picture> listUntaggedPicture(int taskID, String worker);

}
