package com.utag.phase1.domain;

import java.io.Serializable;

/**
 * 单个图片的领域类模型
 */
public class Picture implements Serializable{
    /**
     * 任务id
     */
    private int taskID;

    /**
     * 图片名称
     */
    private String imageID;

    /**
     * 工人
     */
    private String worker;

    /**
     * id
     */
    private String id;

    /**
     * 是否标注
     */
    private boolean isTagged;

    public Picture() {
    }

    public Picture(int taskID, String imageID, String worker) {
        this.taskID = taskID;
        this.imageID = imageID;
        this.id = taskID + worker + imageID;
        this.isTagged = false;
    }


    public int getTaskID() {
        return taskID;
    }

    public void setTaskID(int taskID) {
        this.taskID = taskID;
    }

    public String getImageID() {
        return imageID;
    }

    public void setImageID(String imageID) {
        this.imageID = imageID;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isTagged() {
        return isTagged;
    }

    public void setTagged(boolean tagged) {
        isTagged = tagged;
    }
}
