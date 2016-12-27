package com.example.robert.helloworld;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @version V1.0 <描述当前版本功能>
 * @FileName: com.example.robert.helloworld.TestFragment
 * @author: robert
 * @date: 2016-10-18 11:45
 */

public class TestFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(TestFragment.class.getSimpleName());
        return textView;
    }
}
