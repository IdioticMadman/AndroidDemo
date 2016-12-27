/**
 * Copyright 2015 Erik Jhordan Rey.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robert.dagger2demo.mvp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.robert.dagger2demo.AppComponent;
import com.robert.dagger2demo.R;
import com.robert.dagger2demo.adapter.CategoryAdapter;
import com.robert.dagger2demo.common.BaseActivity;
import com.robert.dagger2demo.mvp.model.Category;
import com.robert.dagger2demo.mvp.model.module.CategoryModule;
import com.robert.dagger2demo.mvp.presenter.CategoryPresenter;
import com.robert.dagger2demo.mvp.ui.component.DaggerCategoryActivityComponent;
import com.robert.dagger2demo.mvp.ui.view.CategoryView;

import java.util.List;

import javax.inject.Inject;

public class CategoryActivity extends BaseActivity
        implements CategoryView, CategoryAdapter.ItemClickListener {

    @Inject
    CategoryPresenter presenter;
    private ProgressBar progressBar;
    private RecyclerView recyclerView;

    @Override
    protected void setupComponent(AppComponent appComponent) {

//        DaggerCategoryActivityComponent
//                .builder()
//                //.appComponent(appComponent)
//                .categoryModule(new CategoryModule(this))
//                .build()
//                .inject(this);

        DaggerCategoryActivityComponent.create().inject(this);

        DaggerCategoryActivityComponent
                .builder()
                .categoryModule(new CategoryModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        initViews();
        setUpToolbar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
        recyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCategories(List<Category> items) {

        CategoryAdapter categoryAdapter = new CategoryAdapter();
        categoryAdapter.setCategories(items);
        categoryAdapter.setItemClickListener(this);
        recyclerView.setAdapter(categoryAdapter);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_categorys);
        progressBar = (ProgressBar) findViewById(R.id.progress);

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void setUpToolbar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowTitleEnabled(true);
        }
    }

    @Override
    public void onItemClick(Category category, int position) {
        presenter.onItemSelected(category, position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catagory, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.setting) {
            startActivity(new Intent(this, SecondActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
