package com.alen.framework.fragment;

import android.content.Context;
import android.view.View;
import android.widget.ListView;

import com.alen.framework.R;
import com.alen.framework.adapter.MyBaseAdapter;
import com.alen.framework.fragment.base.BaseFragment;
import com.alen.framework.holder.BaseHolder;

/**
 * Created by Jeff on 2016/5/5.
 */
public class MessageFragment extends BaseFragment {

    private ListView lvMessage;

    @Override
    protected View initView() {
        View view = View.inflate(getActivity(), R.layout.fragment_message, null);
        lvMessage = (ListView) view.findViewById(R.id.lv_message);
        return view;
    }

    @Override
    protected void initData() {

        // TODO: 2016/5/6  获取后台数据并设置Adapter
        MyBaseAdapter adapter = new MyBaseAdapter() {
            @Override
            public BaseHolder getViewHolder() {
                return new MsgHolder(getActivity());
            }
        };
        //adapter.setData();
        //lvMessage.setAdapter(adapter);
    }

    // TODO: 2016/5/6 消息页面的实现
    static class MsgHolder extends BaseHolder {

        public MsgHolder(Context context) {
            super(context);
        }

        @Override
        protected View initView() {
            return null;
        }

        @Override
        protected void refreshUI(Object data) {

        }
    }
}
