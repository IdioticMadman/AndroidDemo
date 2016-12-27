package com.example.modelviewdemo;

import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.liulishuo.filedownloader.BaseDownloadTask;
import com.liulishuo.filedownloader.FileDownloadSampleListener;
import com.liulishuo.filedownloader.FileDownloader;

import net.ezbim.modelview.ModelView;

import java.io.File;
import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description:
 * Created by xk on 16/12/15.13.42
 */

public class DownFragment extends DialogFragment {

    public static final String DOWN_SOURCE_URL_LIST = "DOWN_SOURCE_URL_LIST";
    public static final String DOWN_SOURCE_URL = "DOWN_SOURCE_URL";
    @BindView(R.id.frag_down_pb)
    ProgressBar fragDownPb;
    @BindView(R.id.frag_down_tv)
    TextView fragDownTv;
    @BindView(R.id.frag_detail_tv)
    TextView fragDetailTv;
    @BindView(R.id.frag_filename_tv)
    TextView fragFilenameTv;
    //    private List<String> downSourceUrlList;
    private String downSourceUrl;
    private String downPath;
    private int downloadId1;
    private String zipPath;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        downSourceUrlList = getArguments().getStringArrayList(DOWN_SOURCE_URL_LIST);
        downSourceUrl = getArguments().getString(DOWN_SOURCE_URL);
        downPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "test" + File.separator + "model";
        zipPath = Environment.getExternalStorageDirectory().getPath() + File.separator + "test" + File.separator + "modelzip" + File.separator;
        File file = new File(downSourceUrl);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(zipPath);
        if (!file2.exists()) {
            file2.mkdirs();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_downdialog, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        goDown();
    }

    private void goDown() {
        if (!TextUtils.isEmpty(downSourceUrl)) {
            downloadId1 = createDownloadTask().start();
        } else {

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        FileDownloader.getImpl().pause(downloadId1);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FileDownloader.getImpl().pause(downloadId1);
    }

    private BaseDownloadTask createDownloadTask() {
        final ViewHolder tag;
        boolean isDir = false;
        tag = new ViewHolder(new WeakReference<>(this), fragDownPb, fragDetailTv, fragDownTv, fragFilenameTv, 1, downPath, zipPath);
        return FileDownloader.getImpl().create(downSourceUrl)
                .setPath(downPath, isDir)
                .setCallbackProgressTimes(300)
                .setMinIntervalUpdateSpeed(400)
                .setTag(tag)
                .setListener(new FileDownloadSampleListener() {
                    @Override
                    protected void pending(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.pending(task, soFarBytes, totalBytes);
                        ((ViewHolder) task.getTag()).updatePending(task);
                    }

                    @Override
                    protected void progress(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.progress(task, soFarBytes, totalBytes);
                        ((ViewHolder) task.getTag()).updateProgress(soFarBytes, totalBytes,
                                task.getSpeed());
                    }

                    @Override
                    protected void error(BaseDownloadTask task, Throwable e) {
                        super.error(task, e);
                        ((ViewHolder) task.getTag()).updateError(e, task.getSpeed());
                    }

                    @Override
                    protected void connected(BaseDownloadTask task, String etag, boolean isContinue, int soFarBytes, int totalBytes) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes);
                        ((ViewHolder) task.getTag()).updateConnected(etag, task.getFilename());
                    }

                    @Override
                    protected void paused(BaseDownloadTask task, int soFarBytes, int totalBytes) {
                        super.paused(task, soFarBytes, totalBytes);
                        ((ViewHolder) task.getTag()).updatePaused(task.getSpeed());
                    }

                    @Override
                    protected void completed(BaseDownloadTask task) {
                        super.completed(task);
                        ((ViewHolder) task.getTag()).updateCompleted(task);
//                        boolean bo = AndUn7z.extract7z(downPath, zipPath);
                        boolean bo = ZipUtils.unZipFiles(downPath, zipPath);
                        if (bo) {
                            ((ViewHolder) task.getTag()).zipDone();
                            int value = ModelView.preProcessBIMFile(zipPath, true);
                            if (value == 1) {
                                ((ViewHolder) task.getTag()).processDone();
                            } else {
                                ((ViewHolder) task.getTag()).processError();
                            }
                        } else {
                            File file = new File(zipPath + "model.yz");
                            if (file.exists()) {
                                int value = ModelView.preProcessBIMFile(zipPath, true);
                                if (value == 1) {
                                    ((ViewHolder) task.getTag()).processDone();
                                } else {
                                    ((ViewHolder) task.getTag()).processError();
                                }
                            }
                        }
                    }

                    @Override
                    protected void warn(BaseDownloadTask task) {
                        super.warn(task);
                        ((ViewHolder) task.getTag()).updateWarn();
                        //
                    }
                });
    }

    private static class ViewHolder {
        private ProgressBar pb;
        private TextView detailTv;
        private TextView speedTv;
        private int position;
        private TextView filenameTv;
        private String downPath;
        private String zipPath;

        private WeakReference<DownFragment> weakReferenceContext;

        public ViewHolder(WeakReference<DownFragment> weakReferenceContext,
                          final ProgressBar pb,
                          final TextView detailTv,
                          final TextView speedTv,
                          final TextView filenameTv,
                          final int position,
                          String downPath,
                          String zipPath

        ) {
            this.weakReferenceContext = weakReferenceContext;
            this.pb = pb;
            this.detailTv = detailTv;
            this.position = position;
            this.speedTv = speedTv;
            this.filenameTv = filenameTv;
            this.downPath = downPath;
            this.zipPath = zipPath;
        }

        private void updateSpeed(int speed) {
            speedTv.setText(String.format("%dKB/s", speed));
        }

        public void updateProgress(final int sofar, final int total, final int speed) {
            if (total == -1) {
                // chunked transfer encoding data
                pb.setIndeterminate(true);
            } else {
                pb.setMax(total);
                pb.setProgress(sofar);
            }

            updateSpeed(speed);

            if (detailTv != null) {
                detailTv.setText(String.format("总计: %d 已下载: %d", sofar, total));
            }
        }

        public void updatePending(BaseDownloadTask task) {
            if (filenameTv != null) {
                filenameTv.setText(task.getFilename());
            }
        }

        public void updatePaused(final int speed) {
//            toast(String.format("paused %d", position));
            updateSpeed(speed);
            pb.setIndeterminate(false);
        }

        public void updateConnected(String etag, String filename) {
            if (filenameTv != null) {
                filenameTv.setText(filename);
            }
        }

        public void updateWarn() {
            toast(String.format("warn %d", position));
            pb.setIndeterminate(false);
        }

        public void updateError(final Throwable ex, final int speed) {
            toast(String.format("error %d %s", position, ex));
            updateSpeed(speed);
            pb.setIndeterminate(false);
            ex.printStackTrace();
        }

        public void updateCompleted(final BaseDownloadTask task) {
            if (detailTv != null) {
                detailTv.setText(String.format("总计: %d 已下载: %d",
                        task.getSmallFileSoFarBytes(), task.getSmallFileTotalBytes()));
            }
            filenameTv.setText("正在解压缩...");
            updateSpeed(task.getSpeed());
            pb.setIndeterminate(false);
            pb.setMax(task.getSmallFileTotalBytes());
            pb.setProgress(task.getSmallFileSoFarBytes());
        }

        public void zipDone() {
            filenameTv.setText("正在进行模型预处理...");
        }

        public void processDone() {
            /*MyApp.getInstance().getAppBaseCache().setModelDown(true);*/
            ((DownActivity) this.weakReferenceContext.get().getActivity()).toModelAty();
        }

        public void processError() {
            filenameTv.setText("预处理错误...");
        }

        private void toast(final String msg) {
            if (this.weakReferenceContext != null && this.weakReferenceContext.get() != null) {
                Toast.makeText(this.weakReferenceContext.get().getActivity(), msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

}
