package com.utag.phase1.domain;

import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Task implements Serializable{
    /**
     * 任务id
     */
    private int id;

    /**
     * 任务名
     */
    private String name;

    /**
     * 任务中图片路径和对应奖励？待修改
     */
    private double reward;

    /**
     * 发布者
     */
    private String requester;

    /**
     * 接受者
     */
    private String worker;

    /**
     * 日期
     */
    private String date;

    /**
     * 完成进程
     */
    private double process;

    /**
     * 是否完成
     */
    private boolean isFinished;

    /**
     * 标注图片集，仅以图片名称作为list
     */
    private List<String> pictureList;

    public Task() {
    }

    public Task(int id, String name, double reward, String requester, String worker, String date, List<String> pictureList) {
        this.id = id;
        this.name = name;
        this.reward = reward;
        this.requester = requester;
        this.worker = worker;
        this.date = date;
        this.pictureList = pictureList;
    }

    public Task(int id, String name, double reward, String requester, String worker,
                String date, double process, boolean isFinished, List<String> pictureList) {
        this.id = id;
        this.name = name;
        this.reward = reward;
        this.requester = requester;
        this.worker = worker;
        this.date = date;
        this.process = process;
        this.isFinished = isFinished;
        this.pictureList = pictureList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getReward() {
        return reward;
    }

    public void setReward(double reward) {
        this.reward = reward;
    }

    public String getRequester() {
        return requester;
    }

    public void setRequester(String requester) {
        this.requester = requester;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getProcess() {
        return process;
    }

    public void setProcess(double process) {
        this.process = process;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public List<String> getPictureList() {
        return pictureList;
    }

    public void setPictureList(List<String> pictureList) {
        this.pictureList = pictureList;
    }
}
