package com.example.modelviewdemo;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import net.ezbim.modelview.simple.SimpleModelActivity;

import java.io.File;

/**
 * Description:
 * Created by xk on 16/12/15.14.26
 */

public class DownActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag);
//        getSupportActionBar().hide();
//        getActionBar().hide();
        if (true) {
            Fragment downFragment = new DownFragment();
            // BaseConstants.DOWN_SERVER_PATH  == http://cloud.ezbim.net/api/v1/files/
            String downFileUrl = "http://cloud.ezbim.net/api/v1/files/" + "5853535a4ad1b3088f224820";
            Bundle bundle = new Bundle();
            bundle.putString(DownFragment.DOWN_SOURCE_URL, downFileUrl);
            downFragment.setArguments(bundle);
            addFragment(R.id.aty_frag_container, downFragment);
        } else {
            //显示模型
            toModelAty();
        }
    }

    protected void addFragment(int containerViewId, Fragment fragment) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment);
        fragmentTransaction.commit();
    }


    public void toModelAty() {
        //Environment.getExternalStorageDirectory().getPath() + File.separator + "ebim"
        String zipPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "test" + File.separator + "modelzip";
        Intent intent = new Intent(DownActivity.this, SimpleModelActivity.class);
        intent.putExtra(SimpleModelActivity.MODEL_FILE_PATH, zipPath);
/*        SimpleModelActivity.setOnOpinionListener(new SimpleModelActivity.OnOpinionListener() {
            @Override
            public void onAddOpinion(String uuid) {
                Intent intent1 = new Intent(DownActivity.this, OpinionAddActivity.class);
                intent1.putExtra(OpinionAddActivity.MODEL_ENTITY_UUID, uuid);
                startActivity(intent1);
                DownActivity.this.finish();
            }
        });*/
        finish();
        startActivity(intent);
    }

}
