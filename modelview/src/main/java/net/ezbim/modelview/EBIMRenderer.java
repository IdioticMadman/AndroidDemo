package net.ezbim.modelview;

import android.graphics.Bitmap;
import android.opengl.GLES20;

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
public class EBIMRenderer implements MyGLSurfaceView.Renderer {
    public static final String TAG = "EBIMRenderer";
    private ModelViewListener modelViewListener;
    private int width = 0;
    private int height = 0;
    private boolean shouldTakePic = false;
    private int takePicdaley = 0;
    private File tempDir;
    private String imageCachePath;

    public interface SaveImageDonListener{
        void saveImageDone(String tempPicFile);
    }

    private SaveImageDonListener saveImageDonListener;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setShouldTakePic(boolean shouldTakePic) {
        this.shouldTakePic = shouldTakePic;
    }

    public EBIMRenderer(ModelViewListener modelViewListener,SaveImageDonListener saveImageDonListener,String imageCachePath) {
        this.modelViewListener = modelViewListener;
        this.saveImageDonListener = saveImageDonListener;
        this.imageCachePath = imageCachePath;
    }

    public EBIMRenderer() {
    }

    public void onDrawFrame(GL10 gl) {
        ModelView.step();
        ModelView.syncSixCube();
        if (shouldTakePic) {
            shouldTakePic = false;
            if (takePicdaley == 0) {
                takePicdaley++;
            } else {
                shouldTakePic = false;
                takePicdaley = 0;
                screenShotGlView();
            }
        }
    }

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        if (width != this.width || height != this.height) {
            this.width = width;
            this.height = height;
            modelViewListener.onModelViewResume();
        }
    }


    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    }

    public interface ModelViewListener {
        void onModelViewResume();
    }

    private void screenShotGlView() {
        if(saveImageDonListener != null) {
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
            }
            inBitmap.copyPixelsFromBuffer(buffer);
            inBitmap = Bitmap.createBitmap(bt, w, h, Bitmap.Config.RGB_565);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            inBitmap.compress(Bitmap.CompressFormat.JPEG, 40, bos);
            byte[] bitmapData = bos.toByteArray();
            ByteArrayInputStream fis = new ByteArrayInputStream(bitmapData);
            String tempPicFile = "temp_" + System.currentTimeMillis() + ".jpeg";
            if (tempDir == null) {
                tempDir = new File(imageCachePath);
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
                saveImageDonListener.saveImageDone(tempPicFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
