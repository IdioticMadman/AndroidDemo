package com.robert.layout.bean;

import android.graphics.Bitmap;

/**
 * Created by robert on 2016/5/26.
 */
public class OtherOrder {
    private long order;
    private String projectName;
    private String projectNo;
    private String projectType;
    private String completeDate;
    private Bitmap result;

    public OtherOrder() {
    }

    public long getOrder() {
        return order;
    }

    public void setOrder(long order) {
        this.order = order;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    public String getCompleteDate() {
        return completeDate;
    }

    public void setCompleteDate(String completeDate) {
        this.completeDate = completeDate;
    }

    public Bitmap getResult() {
        return result;
    }

    public void setResult(Bitmap result) {
        this.result = result;
    }

    public OtherOrder(long order, String projectName, String projectNo, String projectType, String completeDate, Bitmap result) {
        this.order = order;
        this.projectName = projectName;
        this.projectNo = projectNo;
        this.projectType = projectType;
        this.completeDate = completeDate;
        this.result = result;
    }
}
