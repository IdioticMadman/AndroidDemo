package com.example.robert.helloworld;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.Field;

public class SecondActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 20;
    private Context mContext;
    private ImageView iv;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mContext = this;
        iv = (ImageView) findViewById(R.id.iv_photo);
    }

    public void openFile(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CODE);
    }

    public void getIp(View view) {
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo connectionInfo = wifiManager.getConnectionInfo();
        int ipAddress = connectionInfo.getIpAddress();
        String ipString = "";
        if (ipAddress != 0) {
            ipString = ((ipAddress & 0xff) + "." + (ipAddress >> 8 & 0xff) + "."
                    + (ipAddress >> 16 & 0xff) + "." + (ipAddress >> 24 & 0xff));
        }
        Toast.makeText(mContext, ipString, Toast.LENGTH_LONG).show();
    }

    public void show(View v) {
        AlertDialog dialog = new AlertDialog.Builder(mContext).create();
        dialog.setMessage("...........");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "确定被点击", Toast.LENGTH_SHORT).show();
                try {
                    Field mShowing = dialog.getClass().getSuperclass().getSuperclass().getDeclaredField("mShowing");
                    mShowing.setAccessible(true);
                    mShowing.set(dialog, false);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mContext, "取消被点击", Toast.LENGTH_SHORT).show();
                try {
                    Field mShowing = dialog.getClass().getSuperclass().getSuperclass().getDeclaredField("mShowing");
                    mShowing.setAccessible(true);
                    mShowing.set(dialog, true);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });

        dialog.show();
    }

    public void openCamera(View view) {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues values = new ContentValues();
        uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(cameraIntent, 10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10 && resultCode == RESULT_OK) {
            String filePath = FileUtils.getFilePath(SecondActivity.this, uri);
            String targetFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "Pictures/wo.jpg";
            try {
                compressImage(filePath, targetFilePath, 40);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            Bitmap bitmap = BitmapFactory.decodeFile(targetFilePath);
            iv.setImageBitmap(bitmap);
        } else if (requestCode == 20 && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            String filePath = FileUtils.getFilePath(mContext, uri);
            File sourceFile = new File(filePath);
            String sourceFileSize = Formatter.formatFileSize(mContext, sourceFile.length());

            String xin = sourceFile.getAbsolutePath().replace(sourceFile.getName(), "xin");
            ImageUtils.writeCompressImage2File(filePath, xin, 40);
            String targetFileSize = Formatter.formatFileSize(mContext, (new File(xin).length()));

            Toast.makeText(mContext, "原文件大小:" + sourceFileSize + ",转换后文件大小：" + targetFileSize, Toast.LENGTH_LONG).show();
        }
    }

    @Nullable
    public static Intent getDwgFileIntent(@NonNull String param, @NonNull String suffix) {
        if (TextUtils.isEmpty(param) && TextUtils.isEmpty(suffix)) {
            return null;
        }
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-" + suffix.toLowerCase());
        return intent;
    }

    public String compressImage(String sourceFilePath, String targetFilePath, int quality) throws FileNotFoundException {

        Bitmap bm = getSmallBitmap(sourceFilePath);
        File outputFile = new File(targetFilePath);
        FileOutputStream out = new FileOutputStream(outputFile);
        bm.compress(Bitmap.CompressFormat.JPEG, quality, out);
        return outputFile.getPath();
    }


    public Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        //设置成true时，不生成bitmap对象，但是可以获取到bitmap的宽高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        options.inSampleSize = calculateInSampleSize(options, 480, 800);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }


    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }
}
