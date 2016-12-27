package com.example.zbarscan;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zbarscan.scan.CapturedActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private EditText editText;
    private Intent scanIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = (EditText) findViewById(R.id.et_text);

    }

    private String getRunningActivityName(){
        ActivityManager activityManager=(ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        String runningActivity=activityManager.getRunningTasks(1).get(0).topActivity.getClassName();
        return runningActivity;
    }


    public void scan(View view) {
        scanIntent = new Intent(MainActivity.this, CapturedActivity.class);
        scanIntent.putExtra("SCANMODE", true);
//        rxPermissions
//
//                .request(Manifest.permission.CAMERA)
//
//                .subscribe(new Action1<Boolean>() {
//                    @Override
//                    public void call(Boolean aBoolean) {
//                        if (aBoolean) {
//                            startActivityForResult(scanIntent, 102);
//                        } else {
//                            Toast.makeText(MainActivity.this, "您拒绝了使用摄像头", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                })
//        ;

       /* if (permission.granted) {
            Toast.makeText(MainActivity.this, "同意", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, "不同意", Toast.LENGTH_SHORT).show();
        }*/
        /*RxPermissions.getInstance(this)
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Action1<Boolean>() {
                    @Override
                    public void call(Boolean aBoolean) {
                        if (aBoolean) {
                            Toast.makeText(MainActivity.this, "同意", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "不同意", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

        /*if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    CAMERA_REQUEST_CODE);
        } else {
            startActivityForResult(scanIntent, 102);
        }*/


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startActivityForResult(scanIntent, 102);
            } else {
                Toast.makeText(this, "您拒绝了使用摄像头", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == 102 && resultCode == RESULT_OK) {
                String value = data.getStringExtra("value");
                if (!TextUtils.isEmpty(value)) {
                    if (editText.getText().toString().trim().equals("")) {
                        editText.append("扫描到的数据有：\n");
                    }
                    editText.append(value + "\n");
                }
            } else if (requestCode == 102 && resultCode == 106) {
                ArrayList<String> alList = data.getStringArrayListExtra("valueList");
                if (alList != null && alList.size() > 0) {
                    for (String str : alList) {
                        if (editText.getText().toString().trim().equals("")) {
                            editText.append("扫描到的数据有：\n");
                        }
                        editText.append(str + "\n");
                    }
                }
            }
        }
    }
}
