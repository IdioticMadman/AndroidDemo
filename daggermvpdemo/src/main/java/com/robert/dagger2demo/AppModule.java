/**
 * Copyright 2015 Erik Jhordan Rey.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robert.dagger2demo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.robert.dagger2demo.common.BaseConstants;
import com.robert.greendaolib.DaoMaster;
import com.robert.greendaolib.DaoSession;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }

    /**
     * 取得DaoMaster
     *
     * @return
     */
    @Provides
    @Singleton
    DaoMaster provideDaoMaster() {
        DaoMaster.OpenHelper helper = new DaoMaster.DevOpenHelper(app.getApplicationContext(),
                BaseConstants.DB_PATH, null);
        SQLiteDatabase sdb = helper.getWritableDatabase();
        // 这里的dbversion不起作用,且如果这里设置version，下次启动App会把数据库里的的数据全部清空。
        // sdb.setVersion(BaseConstants.DB_VERSION);
        DaoMaster daoMaster = new DaoMaster(sdb);
        return daoMaster;
    }

    /**
     * 取得DaoSession
     *
     * @return
     */
    @Provides
    @Singleton
    DaoSession provideDaoSession(DaoMaster daoMaster) {
        return daoMaster.newSession();
    }

}
