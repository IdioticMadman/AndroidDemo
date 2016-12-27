package com.robert.layout.bean;

import android.graphics.Bitmap;

/**
 * Created by robert on 2016/5/26.
 */
public class Order {
    private long order;
    private String no;
    private String date;
    private String projectName;
    private String completeDate;
    private Bitmap reuslt;
    private Bitmap sync;

    public Order() {

    }

    public Order(long order, String no, String date, String projectName, String completeDate, Bitmap reuslt, Bitmap sync) {
        this.order = order;
        this.no = no;
        this.date = date;
        this.projectName = projectName;
        this.completeDate = completeDate;
        this.reuslt = reuslt;
        this.sync = sync;
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public Bitmap getReuslt() {
        return reuslt;
    }

    public void setReuslt(Bitmap reuslt) {
        this.reuslt = reuslt;
    }

    public Bitmap getSync() {
        return sync;
    }

    public void setSync(Bitmap sync) {
        this.sync = sync;
    }
}
