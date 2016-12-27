package net.pmbim.model.activity;

import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import net.ezbim.modelview.ControlTree;
import net.ezbim.modelview.EBIMRenderer;
import net.ezbim.modelview.EBIMView;
import net.ezbim.modelview.ModelView;
import net.ezbim.modelview.modelnode.ModelControl;
import net.pmbim.model.R;
import net.pmbim.model.base.BaseConstant;
import net.pmbim.model.entity.EntityProperty;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements EBIMView.onEBIMViewListener, EBIMRenderer.ModelViewListener, View.OnClickListener {
    private static final String TAG = "MainActivity";
    public static final int REQ_DOWNLOAD_MODEL_FILE = 10;

    private static final int ACTION_LOAD_MODEL_SUCCESS = 1;
    private static final int ACTION_LOAD_MODEL_HAS_NO_FLOOR = 2;
    private static final int ACTION_MODEL_FILE_DB_HIGH = 3;
    private static final int ACTION_MODEL_FILE_DB_LOW = 4;
    private static final int ACTION_MODEL_FILE_LOSS_FILE = 5;
    private static final int ACTION_MODEL_FILE_NO_WHY = 6;

    private int windowWidth;
    private int windowHeight;

    private boolean isFull = false;
    private EBIMRenderer ebimRenderer;
    private EBIMView ebimView;
    /**
     * 是否已加载模型
     */
    private boolean isLoadModel = false;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case ACTION_LOAD_MODEL_SUCCESS:
                    btnDownload.setVisibility(View.GONE);
                    ebimView.setVisibility(View.VISIBLE);
                    ebimView.requestRender();
                    ModelControl.getModelControl().getDCTTreeRoot();
                    ModelView.unTransParentAll();
                    ModelControl.getModelControl().onHome();
                    ModelView.mouseMoveEvent(-0.785f, -0.785f);
                    Log.e(TAG, "handleMessage: " + "ACTION_LOAD_MODEL_SUCCESS");
                    break;
                case ACTION_LOAD_MODEL_HAS_NO_FLOOR:
                    Log.e(TAG, "handleMessage: " + "ACTION_LOAD_MODEL_HAS_NO_FLOOR");
                    break;
                case ACTION_MODEL_FILE_DB_HIGH:
                    Log.e(TAG, "handleMessage: " + "ACTION_MODEL_FILE_DB_HIGH");
                    break;
                case ACTION_MODEL_FILE_DB_LOW:
                    Log.e(TAG, "handleMessage: " + "ACTION_MODEL_FILE_DB_LOW");
                    break;
                case ACTION_MODEL_FILE_LOSS_FILE:
                    Log.e(TAG, "handleMessage: " + "ACTION_MODEL_FILE_LOSS_FILE");
                    break;
                case ACTION_MODEL_FILE_NO_WHY:
                    Log.e(TAG, "handleMessage: " + "ACTION_MODEL_FILE_NO_WHY");
                    break;
                default:
                    break;
            }
        }
    };
    private String modelId;
    private Button btnDownload;
    private ImageView btnEntityDetail;
    private String checkedEntityId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int statusHeight = getStatusBarHeight();
        windowWidth = dm.widthPixels;
        windowHeight = dm.heightPixels - statusHeight;
        ModelView.init(windowWidth, windowHeight);
        ModelControl.getModelControl().initSixCube(getApplicationContext());
        ModelControl.getModelControl().createSixCube(getApplicationContext());
        resetSixCube();
        ModelView.updateSeveralTimes(6);
        ModelControl.resetModelControl();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
        initData();
    }

    private void initListener() {
        btnEntityDetail.setOnClickListener(this);
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.model);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ebimView = (EBIMView) findViewById(R.id.model_view);
        btnDownload = (Button) findViewById(R.id.btn_download);
        btnEntityDetail = (ImageView) findViewById(R.id.btn_entity_property);
    }

    private void initData() {
        modelId = "57e3b35a9034bf874b84ae5b";
        ebimRenderer = new EBIMRenderer(this);
        ebimView.setRenderer(ebimRenderer);
        ebimView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        ebimView.setEbimViewListener(this);
//        ebimView.requestRender();

    }

    private void resetSixCube() {
        ModelControl.getModelControl().setSixCubePosition(this, windowWidth, windowHeight, !isFull);
        ModelView.updateSeveralTimes(6);
    }

    @Override
    public void onModelViewResume() {
        Log.e(TAG, "onModelViewResume");
    }

    @Override
    public void onSingleTapCancel() {

    }

    @Override
    public void onSingleTapUp(String entityId) {
        checkedEntityId = entityId;
        Log.e(TAG, "onSingleTapUp: " + "被选中的entityId为：" + entityId);
    }

    @Override
    public void onSurfaceDestroyed() {

    }

    @Override
    public void onSurfaceCreated() {

    }


    @Override
    protected void onResume() {
        super.onResume();
        ebimView.onResume();
    }

    @Override
    protected void onPause() {
        ebimView.onPause();
        super.onPause();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void download(View view) {
        File file = new File(BaseConstant.LOCAL_FILE_PATH + modelId);
        if (file.exists()) {
            Toast.makeText(this, "模型已下载", Toast.LENGTH_SHORT).show();
            loadModel(file.getPath());
        } else {
            Intent intent = new Intent(this, DownloadActivity.class);
            intent.putExtra("tag", TAG);
            intent.putExtra("modelId", modelId);
            startActivityForResult(intent, REQ_DOWNLOAD_MODEL_FILE);
        }
        /*int glesversion = ((ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE))
                .getDeviceConfigurationInfo().reqGlEsVersion;
        if (glesversion == 0x00030000 || glesversion == 0x00030001) {
            ModelView.preProcessBIMFile(BaseConstant.LOCAL_FILE_PATH + "model", false);
        } else {
            ModelView.preProcessBIMFile(BaseConstant.LOCAL_FILE_PATH + "model", true);
        }*/
//        loadModel(BaseConstant.LOCAL_FILE_PATH + "model");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_DOWNLOAD_MODEL_FILE && resultCode == DownloadActivity.RES_DOWNLOAD_MODEL_FILE_SUCCESS) {
            final String address = BaseConstant.LOCAL_FILE_PATH + modelId;
            Log.e(TAG, "initData: " + address);
            loadModel(address);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void loadModel(final String address) {
        new Thread() {
            @Override
            public void run() {
                int i = ModelView.loadObject(address);
                switch (i) {
                    case 0:
                        Log.e(TAG, "run: " + "ACTION_MODEL_FILE_FAIL");
                        break;
                    case 1:
                        ArrayList<ControlTree> floorNames = ModelControl.getModelControl().getFloorsTreeRoot().getM_children();
                        if (floorNames != null && floorNames.size() > 0) {
                            ModelControl.getModelControl().loadByFloorName(floorNames.get(0).getM_name());
                            floorNames.get(0).setVisiable(true, true);
                            for (ControlTree floorName : floorNames) {
                                Log.e(TAG, "run: " + "楼层名:" + floorName.getM_name());
                            }
                            mHandler.sendEmptyMessage(ACTION_LOAD_MODEL_SUCCESS);
                            isLoadModel = true;
                        } else {
                            mHandler.sendEmptyMessage(ACTION_LOAD_MODEL_HAS_NO_FLOOR);
                        }

                        break;
                    case 2:
                        mHandler.sendEmptyMessage(ACTION_MODEL_FILE_DB_HIGH);
                        break;
                    case 3:
                        mHandler.sendEmptyMessage(ACTION_MODEL_FILE_DB_LOW);
                        break;
                    case 4:
                        mHandler.sendEmptyMessage(ACTION_MODEL_FILE_LOSS_FILE);
                        break;
                    case 5:
                        Log.e("测试", ACTION_MODEL_FILE_NO_WHY + "");
                        mHandler.sendEmptyMessage(ACTION_MODEL_FILE_NO_WHY);
                        break;
                }
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_model, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.model_scan:
                return true;
            case R.id.model_full_screen:
                return true;
            case R.id.model_choice_floor:
                if (isLoadModel) {
                    startActivity(new Intent(MainActivity.this, ModelFloorActivity.class));
                } else {
                    Toast.makeText(this, "请先加载模型", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_entity_property:
                if (TextUtils.isEmpty(checkedEntityId)) {
                    Toast.makeText(this, "暂未选中构件", Toast.LENGTH_SHORT).show();
                } else {
                    HashMap<String, HashMap<String, String>> entityInfo = ModelView.getEntityInfo(checkedEntityId);
                    if (entityInfo != null && entityInfo.size() > 0) {
                        for (String group : entityInfo.keySet()) {
                            HashMap<String, String> properties = entityInfo.get(group);
                            for (String propertykey : properties.keySet()) {
                                EntityProperty entityProperty = new EntityProperty();
                                entityProperty.setPGroupName(group);
                                entityProperty.setPropertiesKey(propertykey);
                                entityProperty.setPropertiesValue(properties.get(propertykey));
                                Log.e(TAG, "onClick: " + entityProperty.toString());
                            }
                        }
                    }
                }
                break;
            case R.id.btn_entity_hide_others:
                ModelControl.getModelControl().ctrlOnlyShowList(checkedEntityId);
                break;
            case R.id.btn_entity_hide_self:
                ModelControl.getModelControl().ctrlHideList(checkedEntityId, true);
                break;
            case R.id.btn_entity_cancel:
                ModelView.unTransParentAll();
                ModelControl.getModelControl().onHome();
                ModelView.mouseMoveEvent(-0.785f, -0.785f);
                break;
            default:
                break;
        }
    }
}
