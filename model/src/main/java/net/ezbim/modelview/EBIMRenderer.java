package net.ezbim.modelview;

import android.graphics.Bitmap;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Environment;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hdk on 2016/1/28.
 */
public class EBIMRenderer implements GLSurfaceView.Renderer {
    public static final String TAG = "EBIMRenderer";

    private ModelViewListener modelViewListener;
    private int width = 0;
    private int height = 0;
    private boolean shouldTakePic = false;
    private File tempDir;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setShouldTakePic(boolean shouldTakePic) {
        this.shouldTakePic = shouldTakePic;
    }

//    private ScaleChangeListener scaleChangeListener;
//    private double scalHisValue;
//
//    public interface ScaleChangeListener{
//        void onChange(double b);
//    }
//
//    public void setScaleChangeListener(ScaleChangeListener scaleChangeListener) {
//        this.scaleChangeListener = scaleChangeListener;
//    }
    //
//    public void setIsreload(boolean isreload) {
//        this.isreload = isreload;
//    }


    //    public EBIMRenderer(int width, int height, ModelViewListener modelViewListener) {
//        this.width = width;
//        this.height = height;
//        this.modelViewListener = modelViewListener;
//    }
    public EBIMRenderer(ModelViewListener modelViewListener) {
        this.modelViewListener = modelViewListener;
    }

    public EBIMRenderer() {
    }

//    public void setIsinit(boolean isinit) {
//        this.isinit = isinit;
//    }


    public void onDrawFrame(GL10 gl) {
        ModelView.step();
        ModelView.syncSixCube();
        if (shouldTakePic) {
            shouldTakePic = false;
            screenShotGlView();
        }
//        double scalValue = ModelView.getScalValue();
//        if(scalHisValue != scalValue){
//            scaleChangeListener.onChange(scalValue);
//            scalHisValue = scalValue;
//        }
//        Thread thread = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ModelView.step();
//            }
//        });
//        thread.start();
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
//        BLog.e(TAG,"onSurfaceChanged");
        if (width != this.width || height != this.height) {
            this.width = width;
            this.height = height;
            modelViewListener.onModelViewResume();
//            ModelView.resizeView(0, 0, width, height);
        } else {
//            ModelView.updateSeveralTimes(6);
        }
//        ModelView.resizeView(0, 0, width, height);
//        else {
//            ModelView.step();
//            BLog.e(TAG, "resizeView:" + width + "+" + height);
//        }
//        ModelView.updateSeveralTimes(1);
    }


    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//        BLog.e(TAG,"onSurfaceCreated");
    }

    public interface ModelViewListener {
        void onModelViewResume();
    }

    private void screenShotGlView() {
        int w = width;
        int h = height;
        int b[] = new int[w * h];
        int bt[] = new int[w * h];
        IntBuffer buffer = IntBuffer.wrap(b);
        buffer.position(0);
        GLES20.glReadPixels(0, 0, w, h, GLES20.GL_RGBA, GLES20.GL_UNSIGNED_BYTE, buffer);
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                int pix = b[i * w + j];
                int pb = (pix >> 16) & 0xff;
                int pr = (pix << 16) & 0x00ff0000;
                int pix1 = (pix & 0xff00ff00) | pr | pb;
                bt[(h - i - 1) * w + j] = pix1;
            }
        }
        Bitmap inBitmap = null;
        if (inBitmap == null || !inBitmap.isMutable()
                || inBitmap.getWidth() != w || inBitmap.getHeight() != h) {
            inBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
            //为了图像能小一点，使用了RGB_565而不是ARGB_8888
        }
        inBitmap.copyPixelsFromBuffer(buffer);
        inBitmap = Bitmap.createBitmap(bt, w, h, Bitmap.Config.RGB_565);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        inBitmap = ImageUtils.compresseImageByProportion(inBitmap);
        inBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
        byte[] bitmapData = bos.toByteArray();
        ByteArrayInputStream fis = new ByteArrayInputStream(bitmapData);
        String tempPicFile = "temp_" + System.currentTimeMillis() + ".jpeg";
        if (tempDir == null) {
            tempDir = new File(String.valueOf(Environment.getExternalStorageDirectory()) + File.separator + "model");
            tempDir.mkdirs();
        }
        try {
            File tmpFile = new File(tempDir, tempPicFile);
            FileOutputStream fos = new FileOutputStream(tmpFile);
            byte[] buf = new byte[1024];
            int len;
            while ((len = fis.read(buf)) > 0) {
                fos.write(buf, 0, len);
            }
            fis.close();
            fos.close();
            inBitmap.recycle();
//            EventBus.getDefault().post(new SimpleEvent(MainModelFragment.TAG, TAG, tempPicFile));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
