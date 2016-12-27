package net.pmbim.alarm;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: net.pmbim.alarm.AlarmDialog
 * @author: robert
 * @date: 2016-09-28 10:39
 */

public class AlarmDialog extends Dialog {

    private final Context mContext;
    private CloseListener closeListener;

    public AlarmDialog(Context context) {
        super(context, R.style.Dialog_Fullscreen);
        mContext = context;
    }

    public void setCloseListener(CloseListener closeListener) {
        this.closeListener = closeListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉头
        Window window = getWindow();
        window.setContentView(R.layout.alarm_dialog);
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.gravity = WindowManager.LayoutParams.ALPHA_CHANGED;
        window.setAttributes(attributes);
        ColorDrawable drawable = new ColorDrawable(0x000000);
        window.setBackgroundDrawable(drawable);
        Button btnClose = (Button) findViewById(R.id.btn_close);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (closeListener != null) {
                    closeListener.close();
                }
            }
        });
        Glide.with(mContext).load(new File(mContext.getCacheDir(), "alarm.gif")).asGif().into((ImageView) findViewById(R.id.iv_alarm));
    }

    public interface CloseListener {
        void close();
    }

}
