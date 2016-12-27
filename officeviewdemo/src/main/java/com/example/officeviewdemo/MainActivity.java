package com.example.officeviewdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    public static String fileName = "ArcGIS10.doc";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            InputStream inputStream = getAssets().open(fileName);
            HWPFDocument document = new HWPFDocument(inputStream);
            Range range = document.getRange();
            String text = range.text();
            ((TextView) findViewById(R.id.tv_txt)).setText(text);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
