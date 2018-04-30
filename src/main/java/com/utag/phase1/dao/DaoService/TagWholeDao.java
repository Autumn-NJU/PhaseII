package com.utag.phase1.dao.DaoService;

import com.utag.phase1.domain.TagWhole;

import java.io.IOException;
import java.util.List;

/**
 * 整张图片标注的数据层服务
 */
public interface TagWholeDao {

    /**
     *保存整张图片标注的结果
     * @param imageID
     * @param description
     * @return
     */
    boolean saveTagWhole(String imageID, String description) throws IOException;

    /**
     * 删除整张图片保存
     * @param imageID
     * @param
     * @return
     */
   boolean deleteTagWhole(String imageID) throws IOException;

    /**
     *更新整张图片保存
     * @param imageID
     * @param description
     * @return
     */
    boolean updateTagWhole(String imageID, String description) throws IOException;

    /**
     * 根据照片id获取标注字符串长度
     * @param imageID
     * @return
     */
    int getDescriptionLength(String imageID) throws IOException;

    /**
     *
     * @param imageId
     * @return
     */
    String listWholeTag(String imageId);

}
