/*
 * Copyright 2018-2019 KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kunminx.puremusic.ui.manager;

import androidx.annotation.NonNull;
import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.LifecycleOwner;

import com.kunminx.puremusic.bridge.callback.SharedViewModel;
import com.kunminx.puremusic.ui.base.BaseFragment;

/**
 * TODO tip：通过 LifeCycler 来解决抽屉侧滑禁用与否的判断的 一致性问题，
 * <p>
 * 每个需要注册和监听生命周期来判断的视图控制器，无需在各自内部手动书写解绑等操作。
 * 如果这样说还不理解，详见 https://xiaozhuanlan.com/topic/3684721950
 * <p>
 * Create by KunMinX at 19/11/3
 */
public class DrawerCoordinateHelper implements DefaultLifecycleObserver {

    private static DrawerCoordinateHelper sHelper = new DrawerCoordinateHelper();

    public static DrawerCoordinateHelper getInstance() {
        return sHelper;
    }

    private DrawerCoordinateHelper() {
    }

    @Override
    public void onCreate(@NonNull LifecycleOwner owner) {

        SharedViewModel.tagOfSecondaryPages.add(owner.getClass().getSimpleName());

        ((BaseFragment) owner).getSharedViewModel()
                .enableSwipeDrawer.setValue(SharedViewModel.tagOfSecondaryPages.size() == 0);
    }

    @Override
    public void onDestroy(@NonNull LifecycleOwner owner) {

        SharedViewModel.tagOfSecondaryPages.remove(owner.getClass().getSimpleName());

        ((BaseFragment) owner).getSharedViewModel()
                .enableSwipeDrawer.setValue(SharedViewModel.tagOfSecondaryPages.size() == 0);
    }
}
