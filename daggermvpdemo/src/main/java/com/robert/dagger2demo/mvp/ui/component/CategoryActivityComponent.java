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

package com.robert.dagger2demo.mvp.ui.component;

import com.robert.dagger2demo.dagger.scope.ActivityScope;
import com.robert.dagger2demo.interactors.InteractorsModule;
import com.robert.dagger2demo.mvp.model.module.CategoryModule;
import com.robert.dagger2demo.mvp.ui.CategoryActivity;

import dagger.Component;
import dagger.Subcomponent;

@ActivityScope
//@Component(dependencies = AppComponent.class, modules = {CategoryModule.class, InteractorsModule.class})
@Subcomponent
@Component(modules = {CategoryModule.class, InteractorsModule.class})
public interface CategoryActivityComponent {
    void inject(CategoryActivity categoryActivity);
}
