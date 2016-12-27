package com.example.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by robert on 2016/11/25.
 */

public class ScoreView extends View {
    /**
     * 数据个数
     */
    private int dataCount;

    /**
     * 雷达半径
     */
    private float radius;
    /**
     * 中心坐标点X
     */
    private int centerX;
    /**
     * 中心坐标点Y
     */
    private int centerY;
    /**
     * 边框画笔
     */
    private Paint mainPaint;
    /**
     * 值区域画笔
     */
    private Paint valuePaint;
    /**
     * 角度
     */
    private double radian;
    /**
     * 各维度的分值
     */
    private int score[];
    /**
     * 维度的最大分值
     */
    private int maxScore;

    public ScoreView(Context context) {
        this(context, null);

    }

    public ScoreView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScoreView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        score = new int[]{170, 150, 160, 110, 180};
        maxScore = 190;
        dataCount = score.length;
        radian = Math.PI * 2 / dataCount;

        mainPaint = new Paint();
        mainPaint.setColor(Color.WHITE);
        mainPaint.setAntiAlias(true);
        mainPaint.setStrokeWidth(1f);
        mainPaint.setStyle(Paint.Style.STROKE);

        valuePaint = new Paint();
        valuePaint.setAntiAlias(true);
        valuePaint.setColor(Color.WHITE);
        valuePaint.setAlpha(120);
        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        radius = Math.min(w, h) / 2 * 0.75f;
        centerX = w / 2;
        centerY = h / 2;
        postInvalidate();
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPolygon(canvas);
        drawLines(canvas);
        drawRegion(canvas);
    }

    /**
     * 绘制覆盖区域
     *
     * @param canvas
     */
    private void drawRegion(Canvas canvas) {
        Path path = new Path();
        for (int i = 1; i <= dataCount; i++) {
            Point point = getPoint(i, 0, (double) score[i - 1] / maxScore);
            if (i == 1) {
                path.moveTo(point.x, point.y);
            } else {
                path.lineTo(point.x, point.y);
            }
        }
        path.close();
        /*valuePaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(path, valuePaint);*/

        valuePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawPath(path, valuePaint);
    }

    /**
     * 绘制连接线
     *
     * @param canvas canvas对象
     */
    private void drawLines(Canvas canvas) {
        Path path = new Path();
        for (int i = 1; i <= dataCount; i++) {
            path.reset();
            path.moveTo(centerX, centerY);
            Point point = getPoint(i);
            if (point != null) {
                path.lineTo(point.x, point.y);
            }
            canvas.drawPath(path, mainPaint);
        }
    }

    /**
     * 绘制多边形
     *
     * @param canvas canvas对象
     */
    private void drawPolygon(Canvas canvas) {
        Path path = new Path();
        for (int i = 1; i <= dataCount; i++) {
            Point point = getPoint(i);
            if (point != null) {
                if (i == 1) {
                    path.moveTo(point.x, point.y);
                } else {
                    path.lineTo(point.x, point.y);
                }
            }
        }
        path.close();
        canvas.drawPath(path, mainPaint);
    }

    private Point getPoint(int position, int radarMargin, double percent) {
        int x = (int) (centerX + (radius + radarMargin) * Math.sin(radian * position) * percent);
        int y = (int) (centerY + (radius + radarMargin) * Math.cos(radian * position) * (-1) * percent);
        return new Point(x, y);
    }

    private Point getPoint(int position) {
        return getPoint(position, 0, 1);
    }
}
