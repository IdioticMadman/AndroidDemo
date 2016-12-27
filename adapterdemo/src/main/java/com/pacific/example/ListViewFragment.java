package com.pacific.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pacific.adapter.Adapter;
import com.pacific.adapter.AdapterHelper;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class ListViewFragment extends RxFragment {

    private ListView listView;
    private Adapter<ExploreBean> adapter;

    public ListViewFragment() {
    }

    public static ListViewFragment newInstance() {
        ListViewFragment fragment = new ListViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new Adapter<ExploreBean>(getContext(), R.layout.item, R.layout.item0, R.layout.item1) {
            @Override
            protected void convert(final AdapterHelper helper, ExploreBean exploreBean) {
                final int position = helper.getPosition();
                if (position % 3 == 0) {
                    helper.setImageResource(R.id.img_explore_icon, exploreBean.getIconResId())
                            .setText(R.id.tv_explore_name, "__Index: " + String.valueOf(position))
                            .setText(R.id.tv_explore_desc, exploreBean.getDescription())
                            .getItemView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickSnack(position);
                        }
                    });
                } else if (position % 3 == 1) {
                    helper.setImageResource(R.id.img_explore_icon, exploreBean.getIconResId())
                            .setText(R.id.tv_explore_name, "__Index: " + String.valueOf(position))
                            .setText(R.id.tv_explore_desc, exploreBean.getDescription())
                            .getItemView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickSnack(position);
                        }
                    });
                } else {
                    helper.setImageResource(R.id.img_explore_icon, exploreBean.getIconResId())
                            .setImageResource(R.id.img_explore_icon0, exploreBean.getIconResId())
                            .setText(R.id.tv_explore_name, "__Index: " + String.valueOf(position))
                            .setText(R.id.tv_explore_desc, exploreBean.getDescription())
                            .getItemView().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            clickSnack(position);
                        }
                    });
                }
                helper.getItemView().setTag("hello world");
            }

            /**
             * Must be overridden , when you have more than one item layout.
             * No need to be overridden , when you only have one item layout.
             */
            @Override
            public int getItemViewType(int position) {
                if (position % 3 == 0) {
                    return 0;
                } else if (position % 3 == 1) {
                    return 1;
                } else {
                    return 2;
                }
            }

            /**
             * Get layoutResId from view type  @see #getItemViewType(int position) return value.
             * Must be overridden , when you have more than one item layout.
             * No need to be overridden , when you only have one item layout.
             */
            @Override
            public int getLayoutResId(int viewType) {
                if (viewType == 0) {
                    return R.layout.item;
                } else if (viewType == 1) {
                    return R.layout.item0;
                } else {
                    return R.layout.item1;
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) view.findViewById(R.id.lv_list);
        listView.setAdapter(adapter);
        Observable
                .just(null)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<Object, List<ExploreBean>>() {
                    @Override
                    public List<ExploreBean> call(Object o) {
                        return load();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<ExploreBean>>() {
                    @Override
                    public void call(List<ExploreBean> list0) {
                        adapter.addAll(list0);
                    }
                });
    }

    public List<ExploreBean> load() {
        List<ExploreBean> list = new ArrayList<>();
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));
        list.add(new ExploreBean(R.drawable.head, "web work", "start：2016.01.01，end: 2016.02.01"));

        return list;
    }

    public void clickSnack(int position) {
        Snackbar.make(listView, "click item " + String.valueOf(position), Snackbar.LENGTH_SHORT)
                .setAction(R.string.close, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }
}
