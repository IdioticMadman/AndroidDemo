package net.ezbim.modelview;

import android.app.ActivityManager;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.os.SystemClock;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.SurfaceHolder;
import android.view.View;

import net.ezbim.modelview.modelnode.ModelControl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;


/**
 * Created by dev on 16/1/27.
 */
public class EBIMView extends GLSurfaceView implements GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String TAG = "EBIMView";
    private static final boolean DEBUG = true;
    private static final boolean LOG_EGL = true;
    private static final int MODEL_HIDE_LEVEL = 6;
    private static final int MODEL_HIDE_THRESHOLD = 5000;
    private static int GLES_VERSION = 2;
    private GestureDetector gestureDetector;
    private ScaleGestureDetector scaleGestureDetector;
    private String checkValue;
    private static final int MSG_RENDER = 0x0001;
    private static Handler mHandler;
    private boolean firstScroll = true;
    private onEBIMViewListener ebimViewListener;
    private boolean isRendering = false;
    private Boolean isLoop = true;
    private ExecutorService cachedThreadPool;
    private List<Callable<Object>> threadCallers;
    private HandlerThread handlerThread;
    private static EGLContext eglContext;
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

    public void setEbimViewListener(onEBIMViewListener ebimViewListener) {
        this.ebimViewListener = ebimViewListener;
    }

    public boolean isFirstScroll() {
        return firstScroll;
    }

    public void setFirstScroll(boolean firstScroll) {
        this.firstScroll = firstScroll;
    }

    public EBIMView(Context context) {
        super(context);
        // Pick an EGLConfig with RGB8 color, 16-bit depth, no stencil,
        // supporting OpenGL ES 3.0 or later backwards-compatible versions.
        init(context, false, 16, 8);
    }

    public EBIMView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, false, 16, 8);
        Log.d(TAG, "init EBIMView");
    }


    private void init(Context context, boolean translucent, int depth, int stencil) {
        if (LOG_EGL) {
            setDebugFlags(GLSurfaceView.DEBUG_LOG_GL_CALLS);
        }
        gestureDetector = new GestureDetector(context, this);
        scaleGestureDetector = new ScaleGestureDetector(context, this);
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        int reqGlEsVersion = activityManager.getDeviceConfigurationInfo().reqGlEsVersion;
        if (reqGlEsVersion == 0x00030000 || reqGlEsVersion == 0x00030001) {
            GLES_VERSION = 3;
            Log.e("GLES_VERSION", "3");
        }
        /* By default, GLSurfaceView() creates a RGB_565 opaque surface.
         * If we want a translucent one, we should change the surface's
	     * format here, using PixelFormat.TRANSLUCENT for GL Surfaces
	     * is interpreted as any 32-bit surface with alpha by SurfaceFlinger.
	     */
        if (translucent) {
            this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        }

	    /* Setup the context factory for 2.0 rendering.
         * See ContextFactory class definition below
	     */
        setEGLContextFactory(new ContextFactory());

	    /* We need to choose an EGLConfig that matches the format of
         * our surface exactly. This is going to be done in our
	     * custom config chooser. See ConfigChooser class definition
	     * below.
	     */
        setWillNotCacheDrawing(true);
        setDrawingCacheEnabled(false);
        setEGLConfigChooser(translucent ?
                new ConfigChooser(8, 8, 8, 8, depth, stencil) :
                new ConfigChooser(5, 6, 5, 0, depth, stencil));
        setPreserveEGLContextOnPause(true);
        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (!firstScroll) {
                        firstScroll = true;
                    }
                }
                return false;
            }
        });
//        ControlTree tree = BimApplication.getInstance().getModelControl().getFloorsTreeRoot();
        cachedThreadPool = Executors.newCachedThreadPool();
        threadCallers = new ArrayList<Callable<Object>>();
        threadCallers.add(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                if (!isRendering && ModelView.needRenderNow()) {
                    isRendering = true;
                    requestRender();
//                    double scalValue = ModelView.getScalValue();
//                    if(scalHisValue != scalValue){
//                        scaleChangeListener.onChange(scalValue);
//                        scalHisValue = scalValue;
//                    }
                    isRendering = false;
                }
//                    else {  //201604 8d43287版本最后一帧构件刷不出
//                        isRendering = true;
//                        ModelView.step();
//                        isRendering = false;
//                        if(!ebimView.isFirstScroll()) {
//                            ebimView.setFirstScroll(true);
//                        }
//                    }
                return null;
            }
        });
        threadCallers.add(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                SystemClock.sleep(33);
                return null;
            }
        });
        handlerThread = new HandlerThread("RenderThread");
        handlerThread.start();
        mHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                synchronized (this) {
                    try {
                        cachedThreadPool.invokeAll(threadCallers);
                        //                            Log.e(TAG, "loopHandler");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                        Log.e(TAG,"isLOOP == "+isLoop);
                    if (isLoop) {
                        mHandler.sendEmptyMessage(MSG_RENDER);
                    }
                }
            }
        };
    }

    private static class ContextFactory implements EGLContextFactory {
        private static int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

        public EGLContext createContext(EGL10 egl, EGLDisplay display, EGLConfig eglConfig) {
            Log.e(TAG, "creating OpenGL ES " + GLES_VERSION + " context");
            if (eglContext != null) {
                return eglContext;
            }
            int[] attrib_list = {EGL_CONTEXT_CLIENT_VERSION, GLES_VERSION, EGL10.EGL_NONE};
            eglContext = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list);
            return eglContext;
        }

        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
//            egl.eglDestroyContext(display, context);
        }
    }

    private static class ConfigChooser implements EGLConfigChooser {

        public ConfigChooser(int r, int g, int b, int a, int depth, int stencil) {
            mRedSize = r;
            mGreenSize = g;
            mBlueSize = b;
            mAlphaSize = a;
            mDepthSize = depth;
            mStencilSize = stencil;
        }

        /* This EGL config specification is used to specify 1.x rendering.
         * We use a minimum size of 4 bits for red/green/blue, but will
         * perform actual matching in chooseConfig() below.
         */
        private static int EGL_OPENGL_ES_BIT = 4;
        private static int[] s_configAttribs2 =
                {
                        EGL10.EGL_RED_SIZE, 4,
                        EGL10.EGL_GREEN_SIZE, 4,
                        EGL10.EGL_BLUE_SIZE, 4,
                        EGL10.EGL_RENDERABLE_TYPE, EGL_OPENGL_ES_BIT,
                        EGL10.EGL_NONE
                };

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display) {

	        /* Get the number of minimally matching EGL configurations
             */
            int[] num_config = new int[1];
            egl.eglChooseConfig(display, s_configAttribs2, null, 0, num_config);

            int numConfigs = num_config[0];

            if (numConfigs <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }

	        /* Allocate then read the array of minimally matching EGL configs
             */
            EGLConfig[] configs = new EGLConfig[numConfigs];
            egl.eglChooseConfig(display, s_configAttribs2, configs, numConfigs, num_config);

            if (DEBUG) {
                printConfigs(egl, display, configs);
            }
            /* Now return the "best" one
             */
            return chooseConfig(egl, display, configs);
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display,
                                      EGLConfig[] configs) {
            for (EGLConfig config : configs) {
                int d = findConfigAttrib(egl, display, config,
                        EGL10.EGL_DEPTH_SIZE, 0);
                int s = findConfigAttrib(egl, display, config,
                        EGL10.EGL_STENCIL_SIZE, 0);

                // We need at least mDepthSize and mStencilSize bits
                if (d < mDepthSize || s < mStencilSize)
                    continue;

                // We want an *exact* match for red/green/blue/alpha
                int r = findConfigAttrib(egl, display, config,
                        EGL10.EGL_RED_SIZE, 0);
                int g = findConfigAttrib(egl, display, config,
                        EGL10.EGL_GREEN_SIZE, 0);
                int b = findConfigAttrib(egl, display, config,
                        EGL10.EGL_BLUE_SIZE, 0);
                int a = findConfigAttrib(egl, display, config,
                        EGL10.EGL_ALPHA_SIZE, 0);

                if (r == mRedSize && g == mGreenSize && b == mBlueSize && a == mAlphaSize)
                    return config;
            }
            return null;
        }

        private int findConfigAttrib(EGL10 egl, EGLDisplay display,
                                     EGLConfig config, int attribute, int defaultValue) {

            if (egl.eglGetConfigAttrib(display, config, attribute, mValue)) {
                return mValue[0];
            }
            return defaultValue;
        }

        private void printConfigs(EGL10 egl, EGLDisplay display,
                                  EGLConfig[] configs) {
            int numConfigs = configs.length;
            Log.w(TAG, String.format("%d configurations", numConfigs));
            for (int i = 0; i < numConfigs; i++) {
                Log.w(TAG, String.format("Configuration %d:\n", i));
                printConfig(egl, display, configs[i]);
            }
        }

        private void printConfig(EGL10 egl, EGLDisplay display,
                                 EGLConfig config) {
            int[] attributes = {
                    EGL10.EGL_BUFFER_SIZE,
                    EGL10.EGL_ALPHA_SIZE,
                    EGL10.EGL_BLUE_SIZE,
                    EGL10.EGL_GREEN_SIZE,
                    EGL10.EGL_RED_SIZE,
                    EGL10.EGL_DEPTH_SIZE,
                    EGL10.EGL_STENCIL_SIZE,
                    EGL10.EGL_CONFIG_CAVEAT,
                    EGL10.EGL_CONFIG_ID,
                    EGL10.EGL_LEVEL,
                    EGL10.EGL_MAX_PBUFFER_HEIGHT,
                    EGL10.EGL_MAX_PBUFFER_PIXELS,
                    EGL10.EGL_MAX_PBUFFER_WIDTH,
                    EGL10.EGL_NATIVE_RENDERABLE,
                    EGL10.EGL_NATIVE_VISUAL_ID,
                    EGL10.EGL_NATIVE_VISUAL_TYPE,
                    0x3030, // EGL10.EGL_PRESERVED_RESOURCES,
                    EGL10.EGL_SAMPLES,
                    EGL10.EGL_SAMPLE_BUFFERS,
                    EGL10.EGL_SURFACE_TYPE,
                    EGL10.EGL_TRANSPARENT_TYPE,
                    EGL10.EGL_TRANSPARENT_RED_VALUE,
                    EGL10.EGL_TRANSPARENT_GREEN_VALUE,
                    EGL10.EGL_TRANSPARENT_BLUE_VALUE,
                    0x3039, //EGL10.EGL_BIND_TO_TEXTURE_RGB,
                    0x303A, // EGL10.EGL_BIND_TO_TEXTURE_RGBA,
                    0x303B, // EGL10.EGL_MIN_SWAP_INTERVAL,
                    0x303C, // EGL10.EGL_MAX_SWAP_INTERVAL,
                    EGL10.EGL_LUMINANCE_SIZE,
                    EGL10.EGL_ALPHA_MASK_SIZE,
                    EGL10.EGL_COLOR_BUFFER_TYPE,
                    EGL10.EGL_RENDERABLE_TYPE,
                    0x3042 // EGL10.EGL_CONFORMANT
            };
            String[] names = {
                    "EGL_BUFFER_SIZE",
                    "EGL_ALPHA_SIZE",
                    "EGL_BLUE_SIZE",
                    "EGL_GREEN_SIZE",
                    "EGL_RED_SIZE",
                    "EGL_DEPTH_SIZE",
                    "EGL_STENCIL_SIZE",
                    "EGL_CONFIG_CAVEAT",
                    "EGL_CONFIG_ID",
                    "EGL_LEVEL",
                    "EGL_LEVEL",
                    "EGL_MAX_PBUFFER_HEIGHT",
                    "EGL_MAX_PBUFFER_PIXELS",
                    "EGL_MAX_PBUFFER_WIDTH",
                    "EGL_NATIVE_RENDERABLE",
                    "EGL_NATIVE_VISUAL_ID",
                    "EGL_NATIVE_VISUAL_TYPE",
                    "EGL_PRESERVED_RESOURCES",
                    "EGL_SAMPLES",
                    "EGL_SAMPLE_BUFFERS",
                    "EGL_SURFACE_TYPE",
                    "EGL_TRANSPARENT_TYPE",
                    "EGL_TRANSPARENT_RED_VALUE",
                    "EGL_TRANSPARENT_GREEN_VALUE",
                    "EGL_TRANSPARENT_BLUE_VALUE",
                    "EGL_BIND_TO_TEXTURE_RGB",
                    "EGL_BIND_TO_TEXTURE_RGBA",
                    "EGL_MIN_SWAP_INTERVAL",
                    "EGL_MAX_SWAP_INTERVAL",
                    "EGL_LUMINANCE_SIZE",
                    "EGL_ALPHA_MASK_SIZE",
                    "EGL_COLOR_BUFFER_TYPE",
                    "EGL_RENDERABLE_TYPE",
                    "EGL_CONFORMANT"
            };
            int[] value = new int[1];
            for (int i = 0; i < attributes.length; i++) {
                int attribute = attributes[i];
                String name = names[i];
                if (egl.eglGetConfigAttrib(display, config, attribute, value)) {
                    Log.w(TAG, String.format("  %s: %d\n", name, value[0]));
                } else {
                    // Log.w(TAG, String.format("  %s: failed\n", name));
                    while (egl.eglGetError() != EGL10.EGL_SUCCESS) ;
                }
            }
        }

        // Subclasses can adjust these values:
        protected int mRedSize;
        protected int mGreenSize;
        protected int mBlueSize;
        protected int mAlphaSize;
        protected int mDepthSize;
        protected int mStencilSize;
        private int[] mValue = new int[1];
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
//        Log.e(TAG, "start-surfaceCreated");
        super.surfaceCreated(holder);
//        Log.e(TAG, "end-surfaceCreated");
    }

    private int w = 0;
    private int h = 0;

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
//        Log.e(TAG, "start-surfaceChanged");
//        if (this.w == 0 && this.h == 0) {
//            this.w = w;
//            this.h = h;
//            Log.e(TAG, "super-surfaceChanged");
        super.surfaceChanged(holder, format, w, h);
//        } else {
//            ModelView.updateSeveralTimes(6);
//            requestRender();
//            Log.e(TAG, "request-surfaceChanged");
//        }
        if (ebimViewListener != null) {
            ebimViewListener.onSurfaceCreated();
        }
//        Log.e(TAG, "end-surfaceChanged");
    }

    @Override
    public void onResume() {
//        Log.e(TAG, "start-onResume");
        super.onResume();
        ModelView.updateSeveralTimes(6);
        requestRender();
        synchronized (isLoop) {
            isLoop = true;
        }
        mHandler.sendEmptyMessage(MSG_RENDER);
//        Log.e(TAG, "end-onResume");
    }

    @Override
    public void onPause() {
//        Log.e(TAG, "start-onPause");
        synchronized (isLoop) {
            isLoop = false;
        }
        super.onPause();
//        Log.e(TAG, "end-onPause");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        Log.e(TAG, "start-surfaceDestroyed");
        ebimViewListener.onSurfaceDestroyed();
        super.surfaceDestroyed(holder);
//        Log.e(TAG, "end-surfaceDestroyed");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (!firstScroll) {
                firstScroll = true;
            }
        }
        return gestureDetector.onTouchEvent(event) | scaleGestureDetector.onTouchEvent(event);
    }

    //初始化时获得高度跟宽度
    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
    }

    //在横竖屏切换时重新获得高跟宽
    @Override
    protected void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * 手势操作
     */
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //滑动
        if (firstScroll) {
//            if(ModelView.getComponentsCount() > MODEL_HIDE_THRESHOLD) {
//            }
            ModelView.beginCamera();
            firstScroll = false;
        }
        if (e2.getPointerCount() == 1) {
            float finalX = -distanceX / getWidth();
            float finalY = distanceY / getHeight();
            ModelView.mouseMoveEvent(finalX, finalY);
//            ModelView.rotationModelViewPos(finalX,finalY);
//            ModelView.updateSeveralTimes(3);
//            Log.d(TAG, "onScroll distanceX:" + distanceX + " distanceY:" + distanceY);
            return true;
        }
        if (e2.getPointerCount() >= 2) {
            float finalX = -distanceX / getWidth();
            float finalY = distanceY / getHeight();
            ModelView.onPanModel(0, finalX, finalY);
//            ModelView.panModelViewPos(finalX,finalY);
//            ModelView.updateSeveralTimes(3);
//            Log.d(TAG, "onPan distanceX:" + distanceX + " distanceY:" + distanceY);
            return true;
        }
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //单点
        Log.d(TAG, "onSingleTapUp");
        float floatX = e.getX(0);
        float floatY = e.getY(0);
        double dx = floatX / (getWidth() / 2) - 1;
        double dy = 1 - floatY / (getHeight() / 2);
        ModelView.setFrameIgnore(true);
        ModelView.setCanceling(true);
        ModelView.pickNavHudFace((float) dx, (float) dy);
        String checkValue2 = ModelView.clickSelection(dx, dy);
        ModelView.setCameraMoveAndRotateCenterPos((float) dx, (float) dy);
        Log.e(TAG, checkValue2);
        if (!TextUtils.isEmpty(checkValue)) {
            ModelControl.getModelControl().ctrlHighList(checkValue, false);
        }
        if (checkValue2 != null && !checkValue2.equals("")) {
            ModelControl.getModelControl().ctrlHighList(checkValue2, true);
        }
        checkValue = checkValue2;
        if (ebimViewListener != null) {
            ebimViewListener.onSingleTapUp(checkValue2);
        }
        ModelView.updateSeveralTimes(6);
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
        Log.d(TAG, "onShowPress");
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //长按，长按后震动30ms
        Log.d(TAG, "onLongPress");
    }

    //非常快速的滑动
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        Log.d(TAG, "onFling");
        return false;
    }

    //按下
    @Override
    public boolean onDown(MotionEvent e) {
        Log.d(TAG, "onDown");
        ebimViewListener.onSingleTapCancel();
        return false;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        Log.d(TAG, "onSingleTapConfirmed");
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        Log.d(TAG, "onDoubleTap");
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
        Log.d(TAG, "onDoubleTapEvent");
        return false;
    }

    /**
     * 缩放
     */
    @Override
    public boolean onScale(ScaleGestureDetector detector) {
        //差值
        float maxscale = (float) Math.sqrt(getHeight() * getWidth());
        float span = (detector.getPreviousSpan() - detector.getCurrentSpan()) / maxscale;
        Log.e(TAG, "" + (detector.getPreviousSpan() - detector.getCurrentSpan()));
//        Log.d(TAG, "onScale(p:" + detector.getPreviousSpan() + " c:" + detector.getCurrentSpan() + " 比值:" + (detector.getCurrentSpan() - detector.getPreviousSpan()) / maxscale + ")");
//        float spanx = (detector.getPreviousSpanX() - detector.getCurrentSpanX()) / maxscale;
//        float spany = (detector.getPreviousSpanY() - detector.getCurrentSpanY()) / maxscale;
        ModelView.zoomModelOLD(span, true);
//        ModelView.zoomModelViewPos(span);
//        ModelView.updateSeveralTimes(3);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        Log.d(TAG, "onScaleBegin");
//        if(ModelView.getComponentsCount() > MODEL_HIDE_THRESHOLD) {
//        }
        ModelView.beginCamera();
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
        Log.d(TAG, "onScaleEnd");
    }

    public interface onEBIMViewListener {
        void onSingleTapCancel();

        void onSingleTapUp(String entityId);

        void onSurfaceDestroyed();

        void onSurfaceCreated();
    }
}
