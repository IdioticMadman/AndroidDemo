package com.example.greendao3demo;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.greendao3demo.bean.Department;
import com.example.greendao3demo.bean.DepartmentDao;
import com.example.greendao3demo.bean.GreenDaoManager;
import com.example.greendao3demo.bean.User;
import com.example.greendao3demo.bean.UserDao;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private UserDao mUserDao;
    private DepartmentDao mDepartmentDao;
    private List<User> mUsers;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGreenDao();
        mTextView = (TextView) findViewById(R.id.tv);
        mTextView.setText(getGreenText());
    }

    private String getGreenText() {
        StringBuilder stringBuffer = new StringBuilder();
        List<User> users = mUserDao.loadAll();
        for (User user : users) {
            stringBuffer.append(user.toString()).append("\n");
        }
        List<Department> departments = mDepartmentDao.loadAll();
        for (Department department : departments) {
            stringBuffer.append(department.toString()).append("\n");
            List<User> users1 = department.getUsers();
            for (User user : users1) {
                stringBuffer.append(user.toString()).append("\n");
            }
        }
        KLog.e(TAG, "getGreenText: " + stringBuffer.toString());
        return stringBuffer.toString();
    }

    @NeedsPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
    public void initGreenDao() {
        mUserDao = GreenDaoManager.getInstance().getDaoSession().getUserDao();
        mDepartmentDao = GreenDaoManager.getInstance().getDaoSession().getDepartmentDao();
        addUser();
        addDepartment();
    }

    private void addUser() {
        mUsers = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            User user = new User(null, String.valueOf(i), "张三");
            mUserDao.insertOrReplace(user);
            mUsers.add(user);
        }
    }

    private void addDepartment() {
        for (int i = 0; i < 5; i++) {
            Department department = new Department(null, "1", "开发部", String.valueOf(i));
            mDepartmentDao.insertOrReplace(department);
        }
    }


}
