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
package com.robert.dagger2demo;

import com.robert.dagger2demo.mvp.ui.component.CategoryActivityComponent;
import com.robert.greendaolib.DaoMaster;
import com.robert.greendaolib.DaoSession;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    /**
     * 取得App对象
     * @param app
     */
    void inject(App app);

    CategoryActivityComponent categoryActivityComponent();

    /**
     * 取得DaoMaster
     */
    DaoMaster getDaoMaster();

    /**
     * 取得DaoSession
     */
    DaoSession getDaoSession();
}
