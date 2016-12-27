package com.example.pdfviewdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.ScrollBar;
import com.github.barteksc.pdfviewer.listener.OnErrorListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements OnPageChangeListener {
    private static final String TAG = "MainActivity";
    private String fileName = "sample.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PDFView pdfView = (PDFView) findViewById(R.id.pdfView);
        ScrollBar scrollBar = (ScrollBar) findViewById(R.id.scrollBar);

        //ScrollBarPageIndicator scrollBarPageIndicator = (ScrollBarPageIndicator) findViewById(R.id.scrollIndicator);

        setTitle(fileName);
        try {
            String[] strings = getAssets().list("");
            for (String str :
                    strings) {
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        pdfView.fromAsset(fileName)
                .defaultPage(1)
                .onPageChange(this)
                .onError(new OnErrorListener() {
                    @Override
                    public void onError(Throwable t) {
                        Log.e(TAG, "onError: " + t.toString());
                    }
                })
                .load();

    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        setTitle(String.format("%s %s / %s", fileName, page, pageCount));
    }
}
