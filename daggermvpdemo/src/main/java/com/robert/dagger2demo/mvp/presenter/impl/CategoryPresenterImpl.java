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

package com.robert.dagger2demo.mvp.presenter.impl;

import com.robert.dagger2demo.callback.Callback;
import com.robert.dagger2demo.interactors.CategoryInteractor;
import com.robert.dagger2demo.mvp.model.Category;
import com.robert.dagger2demo.mvp.presenter.CategoryPresenter;
import com.robert.dagger2demo.mvp.ui.view.CategoryView;

import java.util.List;

public class CategoryPresenterImpl implements CategoryPresenter, Callback {

    private CategoryView categoryView;
    private CategoryInteractor categoryInteractor;

    public CategoryPresenterImpl(CategoryView categoryView, CategoryInteractor categoryInteractor) {
        this.categoryView = categoryView;
        this.categoryInteractor = categoryInteractor;
    }

    @Override
    public void onResume() {
        categoryView.showProgress();
        categoryInteractor.loadCategories(this);
    }

    @Override
    public void onItemSelected(Category category, int position) {
        categoryView.showMessage(String.format(category.getName() + " ->" + " Position %d clicked", position));
    }

    @Override
    public void onLoadCategories(List<Category> categories) {
        categoryView.showCategories(categories);
        categoryView.hideProgress();
    }
}
