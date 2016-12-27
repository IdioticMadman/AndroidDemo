package com.example.ablum.drawingboard;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;

import java.io.IOException;
import java.io.InputStream;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: net.ezbim.bimoms.phone.widget.drawingboard.DrawingBoardView
 * @author: robert
 * @date: 2016-10-25 19:22
 */
public class DrawAttribute {
    public static enum Corner {
        LEFTTOP, RIGHTTOP, LEFTBOTTOM, RIGHTBOTTOM, ERROR
    }

    ;

    public static enum DrawStatus {
        PEN_NORMAL, PEN_WATER, PEN_CRAYON, PEN_COLOR_BIG, PEN_ERASER, PEN_STAMP
    }

    ;

    public final static int backgroundOnClickColor = 0xfff08d1e;
    public static int screenHeight;
    public static int screenWidth;
    public static Paint paint = new Paint();

    public static Bitmap getImageFromAssetsFile(Context context,
                                                String fileName, boolean isBackground) {
        Bitmap image = null;
        AssetManager am = context.getResources().getAssets();
        try {
            InputStream is = am.open(fileName);
            image = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (isBackground)
            image = Bitmap.createScaledBitmap(image, DrawAttribute.screenWidth,
                    DrawAttribute.screenHeight, false);
        return image;

    }
}
