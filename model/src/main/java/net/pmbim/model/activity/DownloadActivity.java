package net.pmbim.model.activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadListener;
import com.liulishuo.filedownloader.FileDownloader;

import net.ezbim.modelview.ModelView;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.pmbim.model.R;
import net.pmbim.model.base.BaseConstant;
import net.pmbim.model.widget.CircleProgressBar;

import java.io.File;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: net.pmbim.model.activity.DownloadActivity
 * @author: robert
 * @date: 2016-10-12 15:37
 */

public class DownloadActivity extends Activity {
    private static final String TAG = "DownloadActivity";

    public static final int RES_DOWNLOAD_MODEL_FILE_SUCCESS = 11;

    private static final int DOWNLOAD_COMPLETE = 1;
    private static final int UNZIP_COMPLETE = 2;
    private static final int PRE_PROCESS_COMPLETE = 3;
    private TextView tvDownloadDetail;
    private CircleProgressBar progressBar;
    private Context mContext;

    private String modelId;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_COMPLETE:
                    tvDownloadMessage.setText("下载完成，正在解压");
                    break;
                case UNZIP_COMPLETE:
                    tvDownloadMessage.setText("解压完成，正在进行预处理");
                    break;
                case PRE_PROCESS_COMPLETE:
                    tvDownloadMessage.setText("预处理完成，进入模型");
                    SystemClock.sleep(800);
                    setResult(RES_DOWNLOAD_MODEL_FILE_SUCCESS);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
    private String saveLocalPath;
    private TextView tvDownloadMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_download);
        mContext = this;
        tvDownloadDetail = (TextView) findViewById(R.id.tv_download_detail);
        progressBar = (CircleProgressBar) findViewById(R.id.pb_download_progress);
        tvDownloadMessage = (TextView) findViewById(R.id.tv_download_message);
        String tag = getIntent().getStringExtra("tag");
        modelId = getIntent().getStringExtra("modelId");
        tvDownloadMessage.setText("正在下载");
        startDownload();
    }

    private void startDownload() {
        saveLocalPath = BaseConstant.LOCAL_FILE_PATH + modelId + ".zip";
        FileDownloader.getImpl().create("http://192.168.1.236:8080/57e3b35a9034bf874b84ae5b.zip").setPath(saveLocalPath)
                .setCallbackProgressTimes(300).setAutoRetryTimes(5)
                .setMinIntervalUpdateSpeed(400).setListener(new FileDownloadListener() {
            @Override
            protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.e(TAG, "pending: ");
                setProgress(soFarBytes, totalBytes);
            }

            @Override
            protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.e(TAG, "progress: ");
                setProgress(soFarBytes, totalBytes);
            }

            @Override
            protected void completed(BaseDownloadTask task) {
                Log.e(TAG, "completed: "+"下载完成");
                progressBar.setProgress(100);
                tvDownloadDetail.setVisibility(View.INVISIBLE);
                mHandler.sendEmptyMessage(DOWNLOAD_COMPLETE);
                try {
                    unZipFileWithProgress(new File(saveLocalPath), BaseConstant.LOCAL_FILE_PATH + modelId);
                    Log.e(TAG, "completed: "+"解压完成");
                    mHandler.sendEmptyMessage(UNZIP_COMPLETE);
                    SystemClock.sleep(800);
                } catch (ZipException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "解压出错，请尝试重新下载", Toast.LENGTH_SHORT).show();
                    fileDelete();
                    finish();
                }
                int glesversion = ((ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE))
                        .getDeviceConfigurationInfo().reqGlEsVersion;
                int i;
                if (glesversion == 0x00030000 || glesversion == 0x00030001) {
                    i = ModelView.preProcessBIMFile(BaseConstant.LOCAL_FILE_PATH + modelId, false);
                } else {
                    i = ModelView.preProcessBIMFile(BaseConstant.LOCAL_FILE_PATH + modelId, true);
                }
                Log.e(TAG, "completed: "+"预处理完成");
                mHandler.sendEmptyMessage(PRE_PROCESS_COMPLETE);
            }

            @Override
            protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                Log.e(TAG, "paused: ");
            }

            @Override
            protected void error(BaseDownloadTask task, Throwable e) {
                Log.e(TAG, "error: " + e.getMessage());
                Toast.makeText(mContext, "下载出错，请重试", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            protected void warn(BaseDownloadTask task) {
                Log.e(TAG, "warn: ");
            }
        }).start();
    }

    private void setProgress(int soFarBytes, int totalBytes) {
        float progress = (soFarBytes * 1.f) / totalBytes;
        progress *= 100;
        progressBar.setProgress((int) progress);
        String text = Formatter.formatFileSize(mContext, soFarBytes) + " / " + Formatter.formatFileSize(mContext, totalBytes);
        tvDownloadDetail.setText(text);
        Log.e(TAG, "setProgress: " + text);
    }

    private void unZipFileWithProgress(File zipFile, String filePath) throws ZipException {
        ZipFile zFile = new ZipFile(zipFile);
        zFile.setFileNameCharset("UTF-8");
        if (!zFile.isValidZipFile()) {
            Toast.makeText(mContext, "文件下载错误，请从新下载", Toast.LENGTH_SHORT).show();
            fileDelete();
            return;
        }
        File destDir = new File(filePath);
        if (destDir.isDirectory() && !destDir.exists()) {
            destDir.mkdir();
        }
        zFile.setRunInThread(false); // true 在子线程中进行解压 , false主线程中解压
        zFile.extractAll(filePath); // 将压缩文件解压到filePath中
    }

    void fileDelete() {
        File db = null;
        if (saveLocalPath != null) {
            db = new File(saveLocalPath);
        }
        if (db != null && db.exists()) {
            db.delete();
        }
    }
}
