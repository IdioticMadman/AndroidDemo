package com.pacific.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.pacific.adapter.ExpandableAdapter;
import com.pacific.adapter.ExpandableAdapterHelper;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.observers.Observers;
import rx.schedulers.Schedulers;


public class ExpandableListViewFragment extends RxFragment {

    private ExpandableListView listView;
    private ExpandableAdapter<MenuBean, ExploreBean> adapter;

    public ExpandableListViewFragment() {
    }

    public static ExpandableListViewFragment newInstance() {
        ExpandableListViewFragment fragment = new ExpandableListViewFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new ExpandableAdapter<MenuBean, ExploreBean>(getContext(), R.layout.item, R.layout.item_child) {
            @Override
            protected List<ExploreBean> getChildren(int groupPosition) {
                return get(groupPosition).getExploreBeanList();
            }

            @Override
            protected void convertGroupView(final boolean isExpanded, final ExpandableAdapterHelper helper, MenuBean item) {
                helper.setImageResource(R.id.img_explore_icon, item.getIconResId())
                        .setText(R.id.tv_explore_name, item.getDescription())
                        .getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (isExpanded) {
                            listView.collapseGroup(helper.getGroupPosition());
                        } else {
                            listView.expandGroup(helper.getGroupPosition());
                        }
                    }
                });
                helper.getItemView().setTag("hello world");
            }

            @Override
            protected void convertChildView(boolean isLastChild, final ExpandableAdapterHelper helper, ExploreBean item) {
                helper.setText(R.id.tv_explore_name, item.getDescription())
                        .getItemView().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        clickSnack(helper.getGroupPosition(), helper.getChildPosition());
                    }
                });
                helper.getItemView().setTag("hello world");
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_expandable_list_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ExpandableListView) view.findViewById(R.id.elv_list);
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return false;
            }
        });
        listView.setAdapter(adapter);
        Observable
                .just(null)
                .compose(this.bindUntilEvent(FragmentEvent.DESTROY))
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<Object, List<MenuBean>>() {
                    @Override
                    public List<MenuBean> call(Object o) {
                        return load();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<List<MenuBean>>() {
                    @Override
                    public void call(List<MenuBean> list0) {
                        adapter.addAll(list0);
                    }
                });
    }

    public void clickSnack(int g, int c) {
        String str = "click group " + String.valueOf(g) + " child " + String.valueOf(c);
        Snackbar.make(listView, str, Snackbar.LENGTH_SHORT)
                .setAction(R.string.close, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    public List<MenuBean> load() {
        List<MenuBean> list0 = new ArrayList<>();

        list0.add(new MenuBean(R.drawable.smart_ticket, getString(R.string.smart_ticket)));
        list0.add(new MenuBean(R.drawable.auto_lock, getString(R.string.auto_lock)));
        list0.add(new MenuBean(R.drawable.check_device, getString(R.string.check_device)));
        list0.add(new MenuBean(R.drawable.e_key, getString(R.string.e_key)));
        list0.add(new MenuBean(R.drawable.helper, getString(R.string.helper)));
        list0.add(new MenuBean(R.drawable.security_monitor, getString(R.string.security_monitor)));
        list0.add(new MenuBean(R.drawable.check_state, getString(R.string.check_state)));
        list0.add(new MenuBean(R.drawable.work_instruction, getString(R.string.work_instruction)));
        list0.add(new MenuBean(R.drawable.web, getString(R.string.web)));
        list0.add(new MenuBean(R.drawable.repair_manage, getString(R.string.repair_manage)));

        for (MenuBean menuBean : list0) {
            List<ExploreBean> list = new ArrayList<>();
            list.add(new ExploreBean(R.drawable.web, "web work", "start：2016.01.01，end: 2016.02.01"));
            list.add(new ExploreBean(R.drawable.web, "web work", "start：2016.01.01，end: 2016.02.01"));
            list.add(new ExploreBean(R.drawable.web, "web work", "start：2016.01.01，end: 2016.02.01"));
            list.add(new ExploreBean(R.drawable.web, "web work", "start：2016.01.01，end: 2016.02.01"));
            list.add(new ExploreBean(R.drawable.web, "web work", "start：2016.01.01，end: 2016.02.01"));
            list.add(new ExploreBean(R.drawable.web, "web work", "start：2016.01.01，end: 2016.02.01"));
            list.add(new ExploreBean(R.drawable.web, "web work", "start：2016.01.01，end: 2016.02.01"));
            list.add(new ExploreBean(R.drawable.web, "web work", "start：2016.01.01，end: 2016.02.01"));
            menuBean.setExploreBeanList(list);
        }
        return list0;
    }
}
