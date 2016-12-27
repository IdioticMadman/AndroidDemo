package com.example.ablum;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

import com.example.ablum.drawingboard.BimImageUtils;
import com.example.ablum.drawingboard.DrawAttribute;
import com.example.ablum.drawingboard.DrawingBoardView;
import com.example.ablum.drawingboard.ScrawlTools;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: net.ezbim.bimoms.phone.modules.common.ImageEditActivity
 * @author: robert
 * @date: 2016-10-25 16:17
 */

public class ImageEditActivity extends AppCompatActivity {
    @BindView(R.id.drawView)
    DrawingBoardView drawView;
    @BindView(R.id.drawLayout)
    LinearLayout drawLayout;
    @BindView(R.id.rg_actions)
    RadioGroup rg_actions;
    @BindView(R.id.sb_pen_size)
    SeekBar sb_pen_size;
    @BindView(R.id.rb_menu_pen)
    RadioButton rb_menu_pen;
    @BindView(R.id.rb_menu_eraser)
    RadioButton rb_menu_eraser;
    @BindView(R.id.imgbtn_menu_rotate)
    ImageButton imgbtn_menu_rotate;
    @BindView(R.id.rg_color)
    RadioGroup rg_color;
    @BindView(R.id.rb_blue)
    RadioButton rb_blue;
    @BindView(R.id.rb_red)
    RadioButton rb_red;
    @BindView(R.id.rb_yellow)
    RadioButton rb_yellow;
    @BindView(R.id.rb_green)
    RadioButton rb_green;
    @BindView(R.id.rb_pink)
    RadioButton rb_pink;
    ScrawlTools casualWaterUtil = null;
    private String mPath;
    private boolean isfirst = true;
    private ProgressDialog progressDialog;

    private void showProgressDialog(String msg, boolean cancelable, boolean canoutsideclose) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setCancelable(cancelable);
        progressDialog.setCanceledOnTouchOutside(canoutsideclose);
        progressDialog.setMessage(msg);
        progressDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_edit);
        ButterKnife.bind(this);
        mPath = getIntent().getStringExtra("path");
        String tag = getIntent().getStringExtra("from");
        //如果是从模型页面传递过来的话，取消旋转操作
        /*if (!TextUtils.isEmpty(tag) && ModelActivity.TAG.equals(tag)) {
            imgbtn_menu_rotate.setVisibility(View.GONE);
        }*/
        compressed();
        drawLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (isfirst) {
                    reload();
                    isfirst = false;
                }
            }
        });
    }

    private void compressed() {
        reload();
        createpen(DrawAttribute.DrawStatus.PEN_WATER, R.drawable.marker,
                sb_pen_size.getProgress() + 1,
                getResources().getColor(R.color.edit_pen_blue));
        sb_pen_size.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                if (rb_menu_pen.isChecked()) {
                    if (rb_blue.isChecked()) {
                        createpen(DrawAttribute.DrawStatus.PEN_WATER,
                                R.drawable.marker,
                                sb_pen_size.getProgress() + 1, getResources()
                                        .getColor(R.color.edit_pen_blue));
                    } else if (rb_yellow.isChecked()) {
                        createpen(DrawAttribute.DrawStatus.PEN_WATER,
                                R.drawable.marker,
                                sb_pen_size.getProgress() + 1,
                                getResources()
                                        .getColor(R.color.edit_pen_yellow));
                    } else if (rb_red.isChecked()) {
                        createpen(DrawAttribute.DrawStatus.PEN_WATER,
                                R.drawable.marker,
                                sb_pen_size.getProgress() + 1,
                                getResources()
                                        .getColor(R.color.edit_pen_red));
                    } else if (rb_green.isChecked()) {
                        createpen(DrawAttribute.DrawStatus.PEN_WATER,
                                R.drawable.marker,
                                sb_pen_size.getProgress() + 1,
                                getResources()
                                        .getColor(R.color.edit_pen_green));
                    } else if (rb_pink.isChecked()) {
                        createpen(DrawAttribute.DrawStatus.PEN_WATER,
                                R.drawable.marker,
                                sb_pen_size.getProgress() + 1,
                                getResources()
                                        .getColor(R.color.edit_pen_pink));
                    }
                } else {
                    createpen(DrawAttribute.DrawStatus.PEN_ERASER,
                            R.drawable.marker1, sb_pen_size.getProgress() + 1,
                            Color.BLACK);
                }
            }
        });
        rg_actions.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                actionchange(checkedId);
            }
        });

        rg_color.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_blue:
                        if (rb_menu_pen.isChecked()) {
                            createpen(DrawAttribute.DrawStatus.PEN_WATER,
                                    R.drawable.marker,
                                    sb_pen_size.getProgress() + 1, getResources()
                                            .getColor(R.color.edit_pen_blue));
                        }
                        break;
                    case R.id.rb_yellow:
                        if (rb_menu_pen.isChecked()) {
                            createpen(DrawAttribute.DrawStatus.PEN_WATER,
                                    R.drawable.marker,
                                    sb_pen_size.getProgress() + 1,
                                    getResources()
                                            .getColor(R.color.edit_pen_yellow));
                        }
                        break;
                    case R.id.rb_red:
                        if (rb_menu_pen.isChecked()) {
                            createpen(DrawAttribute.DrawStatus.PEN_WATER,
                                    R.drawable.marker,
                                    sb_pen_size.getProgress() + 1,
                                    getResources()
                                            .getColor(R.color.edit_pen_red));
                        }
                        break;
                    case R.id.rb_green:
                        if (rb_menu_pen.isChecked()) {
                            createpen(DrawAttribute.DrawStatus.PEN_WATER,
                                    R.drawable.marker,
                                    sb_pen_size.getProgress() + 1,
                                    getResources()
                                            .getColor(R.color.edit_pen_green));
                        }
                        break;
                    case R.id.rb_pink:
                        if (rb_menu_pen.isChecked()) {
                            createpen(DrawAttribute.DrawStatus.PEN_WATER,
                                    R.drawable.marker,
                                    sb_pen_size.getProgress() + 1,
                                    getResources()
                                            .getColor(R.color.edit_pen_pink));
                        }
                        break;
                    default:
                        break;
                }

            }
        });

    }

    public void actionchange(int checkedId) {
        switch (checkedId) {
            case R.id.rb_menu_pen:
                if (rb_blue.isChecked()) {
                    createpen(DrawAttribute.DrawStatus.PEN_WATER,
                            R.drawable.marker,
                            sb_pen_size.getProgress() + 1, getResources()
                                    .getColor(R.color.edit_pen_blue));
                } else if (rb_yellow.isChecked()) {
                    createpen(DrawAttribute.DrawStatus.PEN_WATER,
                            R.drawable.marker,
                            sb_pen_size.getProgress() + 1,
                            getResources()
                                    .getColor(R.color.edit_pen_yellow));
                } else if (rb_red.isChecked()) {
                    createpen(DrawAttribute.DrawStatus.PEN_WATER,
                            R.drawable.marker,
                            sb_pen_size.getProgress() + 1,
                            getResources()
                                    .getColor(R.color.edit_pen_red));
                } else if (rb_green.isChecked()) {
                    createpen(DrawAttribute.DrawStatus.PEN_WATER,
                            R.drawable.marker,
                            sb_pen_size.getProgress() + 1,
                            getResources()
                                    .getColor(R.color.edit_pen_green));
                } else if (rb_pink.isChecked()) {
                    createpen(DrawAttribute.DrawStatus.PEN_WATER,
                            R.drawable.marker,
                            sb_pen_size.getProgress() + 1,
                            getResources()
                                    .getColor(R.color.edit_pen_pink));
                }
                break;
            case R.id.rb_menu_eraser:
            default:
                createpen(DrawAttribute.DrawStatus.PEN_ERASER,
                        R.drawable.marker1, sb_pen_size.getProgress() + 1,
                        Color.BLACK);
                break;
        }
    }

    void createpen(DrawAttribute.DrawStatus drawStatus, int paintid, int size, int color) {
        BitmapFactory.Options option = new BitmapFactory.Options();
        option.inSampleSize = 3 / size;
        Bitmap pen = BitmapFactory.decodeResource(this.getResources(), paintid,
                option);
        casualWaterUtil.creatDrawPainter(drawStatus, pen, color);
    }

    @OnClick(R.id.btn_cancel)
    void onCancelClick() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @OnClick(R.id.btn_ok)
    void onOKClick() {
        showProgressDialog("正在保存请稍后.", true, false);
        Bitmap bit = casualWaterUtil.getBitmap();
        BimImageUtils.writeImage(bit, mPath, 100);
        Intent intent = new Intent();
        intent.putExtra("darwpath", mPath);
        setResult(RESULT_OK, intent);
        finish();
    }

    @OnClick(R.id.imgbtn_menu_rotate)
    void onRotateClick() {
        Bitmap resizeBmp = casualWaterUtil.getBitmap();
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        float width = metric.widthPixels;
        float height = drawLayout.getHeight();
        resizeBmp = BimImageUtils.rotateImage(resizeBmp, 90, height, width);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                resizeBmp.getWidth(), resizeBmp.getHeight());

        drawView.setLayoutParams(layoutParams);
        casualWaterUtil = new ScrawlTools(this, drawView, resizeBmp);

    }

    @OnClick(R.id.imgbtn_menu_redraw)
    void onRedrawClick() {
        reload();
    }

    void reload() {
        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        float width = metric.widthPixels;
        float height = metric.heightPixels;
        Bitmap resizeBmp = BimImageUtils.compressionFiller(mPath, drawLayout,
                width, height);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                resizeBmp.getWidth(), resizeBmp.getHeight());

        drawView.setLayoutParams(layoutParams);
        casualWaterUtil = new ScrawlTools(this, drawView, resizeBmp);
        drawView.invalidate();
        drawLayout.invalidate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
}
