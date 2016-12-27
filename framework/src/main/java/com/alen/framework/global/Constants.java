package com.alen.framework.global;

/**
 * Created by Jeff on 2016/5/5.
 */
public class Constants {
    /*项目经理*/
    public static final String[] Work_PM = {"签到", "人员管理", "床位状况",
            "财务状况", "员工考勤", "拍摄巡视", "巡视日志"};

    /*领班*/
    public static final String[] Work_Foreman = {"签到", "人员管理", "床位状况",
            "财务状况", "员工考勤", "拍摄巡视", "巡视日志"};

    /*组长*/
    public static final String[] Work_Leader = {"签到", "人员管理", "床位状况",
            "财务状况", "员工考勤"};

    /**
     * 角色
     */
    public enum Role {
        PM, //项目经理
        Foreman, //领班
        leader; //组长
    }
}
