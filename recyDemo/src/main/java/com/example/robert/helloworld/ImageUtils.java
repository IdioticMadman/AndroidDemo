package com.example.robert.helloworld;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by robert on 2016/7/26.
 */

public class ImageUtils {
    /**
     * 通过图片路径获得bitmap（无压缩形式）
     * @param pathName 图片路径
     * @return Bitmap
     */
    public static Bitmap getBitmapFromLocal(String pathName){
        Bitmap bitmap = BitmapFactory.decodeFile(pathName);
        return bitmap;
    }

    /**
     * 通过bitmap得到输出流（无压缩形式）
     * @param bitmap bitmap对象
     * @return OutputStream
     */
    public static ByteArrayOutputStream getOutStreamFromBitmap(Bitmap bitmap){
        return getOutStreamFromBitmap( bitmap,100);
    }
    /**
     * 通过bitmap得到输出流（质量压缩）
     * @param bitmap bitmap对象
     * @param quality 要压缩到的质量（0-100）
     * @return OutputStream
     */
    public static ByteArrayOutputStream getOutStreamFromBitmap(Bitmap bitmap,int quality){
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, bos);
        if(bos != null){
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bos;
    }
    /**
     * 通过流获得bitmap
     * @param os 输出流
     * @return Bitmap
     */
    public static Bitmap getBitmapFromOutStream(ByteArrayOutputStream os){
        ByteArrayInputStream bis = new ByteArrayInputStream(os.toByteArray());
        if(bis !=null){
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return  BitmapFactory.decodeStream(bis);
    }
    /**
     * 通过路径得到图片并对图片进行压缩，并再生成图片（质量压缩）
     * @param imagePath 图片的路径
     * @param savePath 新图片的保存路径
     * @param quality 要压缩到的质量
     * @return Boolean true 成功false失败
     */
    public static boolean writeCompressImage2File(String imagePath ,String savePath,int quality){
        if(TextUtils.isEmpty(imagePath)){
            return false;
        }
        String imageName  = imagePath.substring(imagePath.lastIndexOf("/")+1,imagePath.lastIndexOf("."));

        Bitmap bitmapFromLocal = getBitmapFromLocal(imagePath);

        ByteArrayOutputStream outStreamFromBitmap = getOutStreamFromBitmap(bitmapFromLocal, quality);

        return writeImage2File(outStreamFromBitmap,savePath ,imageName);
    }
    /**
     * 把bitmap写入指定目录下，重新生成图片
     * @param bitmap bitmap对象
     * @param savePath 新图片保存路径
     * @param imageName 新图片的名字，会根据时间来命名
     * @return Boolean true 成功false失败
     */
    public static boolean writeImage2File(Bitmap bitmap ,String savePath,String imageName){
        return writeImage2File(getOutStreamFromBitmap(bitmap),savePath ,imageName);
    }
    /**
     * 通过输出流，重组图片，并保存带指定目录下
     * @param bos 图片输入流
     * @param savePath 新图片的保存路径
     * @param imageName 新图片的名字，字段为空时，会根据时间来命名
     * @return Boolean true 成功false失败
     */
    public static boolean writeImage2File(ByteArrayOutputStream bos,String savePath,String imageName){
        if(TextUtils.isEmpty(savePath)){
            return false;
        }
        File file =new File(savePath);
        if(!file.exists()){
            file.mkdirs();
        }
        FileOutputStream fos;
        try {
            if(TextUtils.isEmpty(imageName)){
                imageName = System.currentTimeMillis()+"";
            }
            File f = new File(file,imageName+".jpg");
            fos = new FileOutputStream(f);
            fos.write(bos.toByteArray());
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}