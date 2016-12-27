package com.robert.dagger2demo.common;

import android.os.Environment;

import java.io.File;

/**
 * 描述：存放基础常量
 *
 * @author xk
 * @version 1.00
 * @created 2015年6月23日 上午11:47:28
 */
public class BaseConstants {

    public static final int DB_VERSION = 6;

    public static final String SD_CARD = Environment.getExternalStorageDirectory().getPath() + File.separator;

    // 基础路径
    public static final String BASE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "ebim" + File.separator;
    // 数据库路径以及名字
    public static final String DB_PATH = BASE_PATH + "database" + File.separator + "ebim.db";
    // 缩略图路径
    public static final String SMALLIMAGE_PATH = BASE_PATH + "smallimage" + File.separator;
    // 缓存路径
    public static final String CATCH_PATH = BASE_PATH + "filecatch" + File.separator;
    // ImageLoader的缓存路径
    public static final String IMAGE_CATCH_PATH = BASE_PATH + "imgcatch" + File.separator;
    // db数据库压缩文件存放路径
    public static final String ENTITY_DB_BASE_PATH = BASE_PATH + "database" + File.separator;

    public static final String OKHTTP_CATCH = BaseConstants.BASE_PATH + "okhttpcatch";
    //下拉刷新的加载数量
    public static final int LIMIT = 10;
    //分页更新数据的数据总量
    public static final int DBLIMIT = 500;
    /**
     * 基础常量名
     */
    public static final String SESSION_ID = "sessionid";
    public static final String USER_ID = "userid";
    public static final String USER_NAME = "username";
    public static final String PROJECT_ID = "projectid";
    public static final String PROJECT_NAME = "projectname";
    public static final String BASE_URL = "baseurl";
    public static final String BASE_LOCAL_URL = "baselocalurl";
    public static final String LOGON_ACTION = "LOGIN_ACTION";
    public static final String FIRST_OPEN="first_open";
    // 下载图片的URL
    public static final String PIC_URL = "api/pictures" + File.separator;
    public static final String FILE_PATH = "api/files" + File.separator;

}
