package com.zijunlin.Zxing.Demo;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.zijunlin.Zxing.Demo.encoding.EncodingUtils;

public class EncodingActivity extends AppCompatActivity {

    private EditText et_text;
    private ImageView iv_qrCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encoding);
        et_text = (EditText) findViewById(R.id.et_text);
        iv_qrCode = (ImageView) findViewById(R.id.iv_QRCode);



    }

    public void generate(View view) {
        String str = et_text.getText().toString();
        Bitmap qrCode = EncodingUtils.createQRCode(str, 500, 500);
        iv_qrCode.setImageBitmap(qrCode);
    }
}
