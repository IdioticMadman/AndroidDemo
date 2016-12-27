package net.ezbim.modelview.simple;

import android.app.ProgressDialog;
import android.content.Intent;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.Toast;

import net.ezbim.modelview.EBIMRenderer;
import net.ezbim.modelview.EBIMView;
import net.ezbim.modelview.ModelView;
import net.ezbim.modelview.R;
import net.ezbim.modelview.modelnode.ModelControl;
import net.ezbim.scan.simple.SimpleScanActivity;

/**
 * 描述：
 * Created by xingkai on 16/5/9.
 */
public class SimpleModelActivity extends AppCompatActivity implements EBIMRenderer.ModelViewListener,
        EBIMRenderer.SaveImageDonListener, EBIMView.onEBIMViewListener {

    private static final int MODEL_FLOOR_REQUEST = 1000;
    private ProgressDialog progressDialog;
    public static final String MODEL_FILE_PATH = "MODEL_FILE_PATH";
    private EBIMView ebimView;
    private EBIMRenderer ebimRenderer;
    private String modelFilePath;
    private PopupWindow popupWindow;
    private String tempEntityId;
    private int loadResult = 0;

    public interface OnOpinionListener {
        void onAddOpinion(String uuid);
    }

    public static OnOpinionListener onOpinionListener;

    public static void setOnOpinionListener(OnOpinionListener onOpinionListenerIn) {
        SimpleModelActivity.onOpinionListener = onOpinionListenerIn;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        modelFilePath = getIntent().getStringExtra(MODEL_FILE_PATH);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.aty_simple_model);
        ebimView = (EBIMView) findViewById(R.id.aty_simple_model_view);
        ebimRenderer = new EBIMRenderer(this, this, this.getCacheDir().getAbsolutePath());
        ebimView.setRenderer(ebimRenderer);
        ebimView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
        ebimView.setEbimViewListener(this);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        ModelControl.getInstance().initModelViewAndSixCube(this, displayMetrics.widthPixels, displayMetrics.heightPixels);
        ebimView.requestRender();
        loadResult = ModelView.loadObject(modelFilePath);
        if (loadResult == 1) {
            ModelControl.getInstance().reloadFloorsFromThis();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ebimView.onResume();
        ModelView.updateSeveralTimes(3);
    }

    @Override
    protected void onPause() {
        ebimView.onPause();
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onModelViewResume() {

    }

    @Override
    public void saveImageDone(String tempPicFile) {

    }

    @Override
    public void onSingleTapCancel() {

    }

    @Override
    public void onSingleTapUp(float dx, float dy, String entityId) {
        if (!TextUtils.isEmpty(entityId)) {
            tempEntityId = entityId;
            showPopupWindow((int) dx, (int) dy);
        } else {
            tempEntityId = null;
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
        }
    }

    @Override
    public void onSurfaceDestroyed() {

    }

    @Override
    public void onSurfaceCreated() {

    }

    public void onResetModel(View view) {
        ModelControl.getInstance().onHome();
        ModelView.updateSeveralTimes(3);
    }

    public void onChoiceMajor(View view) {
        startActivity(new Intent(this, EntityShowActivity.class));
    }

    public void onChoiceFloor(View view) {
        startActivityForResult(new Intent(this, ModelFloorActivity.class), MODEL_FLOOR_REQUEST);
    }

    public void onCancel(View view) {
        ModelControl.getInstance().clearAllStatus();
    }

    public void onScan(View view) {
        startActivityForResult(new Intent(this, SimpleScanActivity.class), SimpleScanActivity.SIMPLE_SCAN_REQUEST);
    }

    public void onSingleHidden(View view) {
        if (checkEntityId()) {
            ModelControl.getInstance().ctrlHideList(tempEntityId, true);
        }
    }

    public void onSingleDisplay(View view) {
        if (checkEntityId()) {
            ModelControl.getInstance().ctrlOnlyShowList(tempEntityId, true);
        }
    }

    public void onShowAttribute(View view) {
        if (checkEntityId()) {
            Intent intent = new Intent(this, EntityPropertiesActivity.class);
            intent.putExtra(EntityPropertiesActivity.ENTITY_PROP_UUID, tempEntityId);
            startActivity(intent);
        }
    }

    public void onAddOpinion(View view) {
        if (checkEntityId()) {
            doOnAddOpinion();
        }
    }

    private void doOnAddOpinion() {
        if (onOpinionListener != null) {
            onOpinionListener.onAddOpinion(tempEntityId);
        }
    }

    private boolean checkEntityId() {
        if (TextUtils.isEmpty(tempEntityId)) {
            Toast.makeText(this, "选中的构件没有ID", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }

    private void showPopupWindow(int ixx, int iyy) {
        if (popupWindow == null) {
            View contentView = LayoutInflater.from(this).inflate(R.layout.pop_window, null);
            popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            popupWindow.setTouchable(true);
            popupWindow.setTouchInterceptor(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    return false;
                }
            });
            popupWindow.setBackgroundDrawable(this.getResources().getDrawable(R.drawable.white));
            popupWindow.setAnimationStyle(R.style.PopupAnimation);
        }
        popupWindow.showAtLocation(ebimView, Gravity.NO_GRAVITY, ixx, iyy);
    }

    private void showPopupWindowInCenter() {
        showPopupWindow(ebimView.getWidth() / 2, ebimView.getHeight() / 2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MODEL_FLOOR_REQUEST && resultCode == RESULT_OK) {
            showProgressDialog("正在重新加载", true, false);
            ModelControl.getInstance().loadFloorFromFloorTree(ModelFloorActivity.floorRootTree);
            dimissProgressDialog();
        } else if (requestCode == SimpleScanActivity.SIMPLE_SCAN_REQUEST && resultCode == RESULT_OK) {
            if (data != null) {
                String qrCode = data.getStringExtra(SimpleScanActivity.SIMPLE_SCAN_RESULT);
                if (!TextUtils.isEmpty(qrCode)) {
                    //测试，直接定位
                    if (ModelControl.getInstance().zoomToEntity(qrCode)) {
//                        doOnAddOpinion();
                        tempEntityId = qrCode;
                        showPopupWindowInCenter();
                    }
                }
            }
        }
    }

    private void showProgressDialog(String msg, boolean cancelable, boolean canoutsideclose) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setCancelable(cancelable);
        progressDialog.setCanceledOnTouchOutside(canoutsideclose);
        progressDialog.setMessage(msg);
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void dimissProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

}
