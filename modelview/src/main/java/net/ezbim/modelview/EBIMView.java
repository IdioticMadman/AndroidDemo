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
import javax.microedition.khronos.egl.EGLSurface;


/**
 * Created by dev on 16/1/27.
 */
public class EBIMView extends MyGLSurfaceView implements GestureDetector.OnGestureListener, ScaleGestureDetector.OnScaleGestureListener, GestureDetector.OnDoubleTapListener {

    private static final String TAG = "EBIMView";
    private static final boolean DEBUG = true;
    private static final boolean LOG_EGL = false;
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
        init(context, true, 16, 8);
    }

    public EBIMView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, true, 16, 8);
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
        }
        if (translucent) {
            this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
        }
        setEGLContextFactory(new ContextFactory());
        setEGLWindowSurfaceFactory(new SurfaceFactory());
        setWillNotCacheDrawing(true);
        setDrawingCacheEnabled(false);
        setEGLConfigChooser(translucent ?
                new ConfigChooser(8, 8, 8, 8, depth, stencil) :
                new ConfigChooser(5, 6, 5, 0, depth, stencil));
        setPreserveEGLContextOnPause(true);
        this.setOnTouchListener(new View.OnTouchListener() {
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
        cachedThreadPool = Executors.newCachedThreadPool();
        threadCallers = new ArrayList<Callable<Object>>();
        threadCallers.add(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                if (!isRendering && ModelView.needRenderNow()) {
                    isRendering = true;
                    requestRender();
                    isRendering = false;
                }
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
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
            int[] attrib_list = {EGL_CONTEXT_CLIENT_VERSION, GLES_VERSION, EGL10.EGL_NONE};
            eglContext = egl.eglCreateContext(display, eglConfig, EGL10.EGL_NO_CONTEXT, attrib_list);
            return eglContext;
        }

        public void destroyContext(EGL10 egl, EGLDisplay display, EGLContext context) {
            egl.eglDestroyContext(display, context);
        }
    }

    private static class SurfaceFactory implements EGLWindowSurfaceFactory {
        @Override
        public EGLSurface createWindowSurface(EGL10 egl, EGLDisplay display, EGLConfig config, Object nativeWindow) {
            EGLSurface eglSurface = egl.eglCreateWindowSurface(display, config, nativeWindow, null);
            return eglSurface;
        }

        @Override
        public void destroySurface(EGL10 egl, EGLDisplay display, EGLSurface surface) {
            egl.eglDestroySurface(display, surface);
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

            int[] num_config = new int[1];
            egl.eglChooseConfig(display, s_configAttribs2, null, 0, num_config);

            int numConfigs = num_config[0];

            if (numConfigs <= 0) {
                throw new IllegalArgumentException("No configs match configSpec");
            }

            EGLConfig[] configs = new EGLConfig[numConfigs];
            egl.eglChooseConfig(display, s_configAttribs2, configs, numConfigs, num_config);

            if (DEBUG) {
                printConfigs(egl, display, configs);
            }
            return chooseConfig(egl, display, configs);
        }

        public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display,
                                      EGLConfig[] configs) {
            for (EGLConfig config : configs) {
                int d = findConfigAttrib(egl, display, config,
                        EGL10.EGL_DEPTH_SIZE, 0);
                int s = findConfigAttrib(egl, display, config,
                        EGL10.EGL_STENCIL_SIZE, 0);

                if (d < mDepthSize || s < mStencilSize)
                    continue;
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
            for (int i = 0; i < numConfigs; i++) {
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
                } else {
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
        super.surfaceCreated(holder);
    }

    private int w = 0;
    private int h = 0;

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        super.surfaceChanged(holder, format, w, h);
        if (ebimViewListener != null) {
            ebimViewListener.onSurfaceCreated();
        }
    }

    @Override
    public void onResume() {
        synchronized (isLoop) {
            isLoop = true;
        }
        mHandler.sendEmptyMessage(MSG_RENDER);
        super.onResume();
    }

    @Override
    public void onPause() {
        synchronized (isLoop) {
            isLoop = false;
        }
        super.onPause();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        ebimViewListener.onSurfaceDestroyed();
        super.surfaceDestroyed(holder);
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
            ModelView.beginCamera();
            ModelControl.getInstance().updateModelHide(true);
            firstScroll = false;
        }
        if (e2.getPointerCount() == 1) {
            float finalX = -distanceX / getWidth();
            float finalY = distanceY / getHeight();
            ModelView.mouseMoveEvent(finalX, finalY);
            return true;
        }
        if (e2.getPointerCount() >= 2) {
            float finalX = -distanceX / getWidth();
            float finalY = distanceY / getHeight();
            ModelView.onPanModel(0, finalX, finalY);
            return true;
        }
        return false;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //单点
        float floatX = e.getX(0);
        float floatY = e.getY(0);
        double dx = floatX / (getWidth() / 2) - 1;
        double dy = 1 - floatY / (getHeight() / 2);
        ModelView.setFrameIgnore(true);
        ModelView.setCanceling(true);
        ModelView.pickNavHudFace((float) dx, (float) dy);
        String checkValue2 = ModelView.clickSelection(dx, dy);
        ModelView.setCameraMoveAndRotateCenterPos((float) dx, (float) dy);
        if (!TextUtils.isEmpty(checkValue)) {
            ModelControl.getInstance().ctrlHighList(checkValue, false);
        }
        if (checkValue2 != null && !checkValue2.equals("")) {
            ModelControl.getInstance().ctrlHighList(checkValue2, true);
        }
        checkValue = checkValue2;
        if (ebimViewListener != null) {
            ebimViewListener.onSingleTapUp(floatX, floatY, checkValue2);
        }
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public void onLongPress(MotionEvent e) {
        //长按，长按后震动30ms
    }

    //非常快速的滑动
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    //按下
    @Override
    public boolean onDown(MotionEvent e) {
        ebimViewListener.onSingleTapCancel();
        return false;
    }


    @Override
    public boolean onSingleTapConfirmed(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent e) {
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
        ModelView.zoomModelOLD(span, true);
        return true;
    }

    @Override
    public boolean onScaleBegin(ScaleGestureDetector detector) {
        ModelView.beginCamera();
        ModelControl.getInstance().updateModelHide(true);
        return true;
    }

    @Override
    public void onScaleEnd(ScaleGestureDetector detector) {
    }

    public interface onEBIMViewListener {
        void onSingleTapCancel();

        void onSingleTapUp(float dx, float dy, String entityId);

        void onSurfaceDestroyed();

        void onSurfaceCreated();
    }
}
