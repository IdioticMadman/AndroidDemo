package net.pmbim.model.base;

import android.os.Environment;

import java.io.File;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: net.pmbim.model.base.BaseConstant
 * @author: robert
 * @date: 2016-10-12 16:16
 */

public class BaseConstant {
    public static final String FILE_PATH = "http://180.168.170.198:3000/api/v1/files/";
    public static final String LOCAL_FILE_PATH = Environment.getExternalStorageDirectory().getPath() + File.separator + "eops" + File.separator;
}
