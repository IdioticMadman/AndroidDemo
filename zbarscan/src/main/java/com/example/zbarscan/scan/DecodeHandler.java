package com.example.zbarscan.scan;


import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.RelativeLayout;

import com.dtr.zbar.build.ZBarDecoder;
import com.example.zbarscan.R;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;

/**
 * 描述: 接受消息后解码
 */
final class DecodeHandler extends Handler {

    CapturedActivity activity = null;
    Rect mCropRect = null;

    DecodeHandler(CapturedActivity activity) {
        this.activity = activity;
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.what) {
            case R.id.zbar_decode:
                initCrop(message.arg1, message.arg2);
                if (mCropRect != null)
                    decode((byte[]) message.obj, message.arg1, message.arg2, mCropRect);
                break;
            case R.id.zbar_quit:
                Looper.myLooper().quit();
                break;
        }
    }

    private void initCrop(int cameraWidth, int cameraHeight) {

        /** 获取布局中扫描框的位置信息 */
        int[] location = new int[2];
        RelativeLayout scanCropView = (RelativeLayout) activity.findViewById(R.id.capture_crop_layout);
        RelativeLayout scanContainer = (RelativeLayout) activity.findViewById(R.id.capture_containter);
        scanCropView.getLocationInWindow(location);

        int cropLeft = location[0];
        int cropTop = location[1] - getStatusBarHeight();

        int cropWidth = scanCropView.getWidth();
        int cropHeight = scanCropView.getHeight();

        /** 获取布局容器的宽高 */
        int containerWidth = scanContainer.getWidth();
        int containerHeight = scanContainer.getHeight();

        /** 计算最终截取的矩形的左上角顶点x坐标 */
        int x = cropLeft * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的左上角顶点y坐标 */
        int y = cropTop * cameraHeight / containerHeight;

        /** 计算最终截取的矩形的宽度 */
        int width = cropWidth * cameraWidth / containerWidth;
        /** 计算最终截取的矩形的高度 */
        int height = cropHeight * cameraHeight / containerHeight;

        /** 生成最终的截取的矩形 */
//        mCropRect = new Rect(x, y, width + x, height + y);
        mCropRect = new Rect(cropLeft, cropTop, cropWidth+cropLeft, cropHeight+cropTop);
    }

    private void decode(byte[] data, int width, int height, Rect rect) {
        byte[] rotatedData = new byte[data.length];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++)
                rotatedData[x * height + height - y - 1] = data[x + y * width];
        }
        int tmp = width;// Here we are swapping, that's the difference to #11
        width = height;
        height = tmp;

        ZBarDecoder manager = new ZBarDecoder();
        String result = manager.decodeCrop(rotatedData, width, height, mCropRect.left, mCropRect.top, mCropRect.width(), mCropRect.height());

        if (result != null) {
            if (activity.isNeedCapture()) {
                // 生成bitmap
                PlanarYUVLuminanceSource source = new PlanarYUVLuminanceSource(rotatedData, width, height, activity.getX(), activity.getY(),
                        activity.getCropWidth(), activity.getCropHeight(), false);
                int[] pixels = source.renderThumbnail();
                int w = source.getThumbnailWidth();
                int h = source.getThumbnailHeight();
                Bitmap bitmap = Bitmap.createBitmap(pixels, 0, w, w, h, Bitmap.Config.ARGB_8888);
                try {
                    String rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Qrcode/";
                    File root = new File(rootPath);
                    if (!root.exists()) {
                        root.mkdirs();
                    }
                    File f = new File(rootPath + "Qrcode.jpg");
                    if (f.exists()) {
                        f.delete();
                    }
                    f.createNewFile();

                    FileOutputStream out = new FileOutputStream(f);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (null != activity.getHandler()) {
                Message msg = new Message();
                msg.obj = result;
                msg.what = R.id.zbar_decode_succeeded;
                activity.getHandler().sendMessage(msg);
            }
        } else {
            if (null != activity.getHandler()) {
                activity.getHandler().sendEmptyMessage(R.id.zbar_decode_failed);
            }
        }
    }

    private int getStatusBarHeight() {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return activity.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
}
