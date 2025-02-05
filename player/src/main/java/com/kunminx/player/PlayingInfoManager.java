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

package com.kunminx.player;

import android.content.Context;

import com.kunminx.player.bean.base.BaseAlbumItem;
import com.kunminx.player.bean.base.BaseMusicItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Create by KunMinX at 18/9/24
 */
public class PlayingInfoManager<B extends BaseAlbumItem, M extends BaseMusicItem> {

    private static final String SP_NAME = "KunMinX_Music";
    private static final String REPEAT_MODE = "REPEAT_MODE";
    private static final String LAST_CHAPTER_INDEX = "LAST_CHAPTER_INDEX";
    private static final String LAST_BOOK_DETAIL = "LAST_BOOK_DETAIL";

/*    //单曲循环
    public static final int ONE_LOOP = 1 << 1;
    //列表循环
    public static final int LIST_LOOP = 1 << 2;
    //随机播放
    public static final int RANDOM = 1 << 3;*/

    //播放使用的当前章节 index
    private int mPlayIndex = 0;
    //列表展示的当前章节 index
    private int mAlbumIndex = 0;

    //循环模式
    private Enum mRepeatMode;

    public enum RepeatMode {
        ONE_LOOP, LIST_LOOP, RANDOM
    }

    //原始列表
    private List<M> mOriginPlayingList = new ArrayList<>();

    //随机播放列表
    private List<M> mShufflePlayingList = new ArrayList<>();

    //专辑详情
    private B mMusicAlbum;


    public void init(Context context) {
        /*SharedPreferences sp = context.getSharedPreferences(
                SP_NAME, Context.MODE_PRIVATE);

        String musicAlbum = sp.getString(LAST_BOOK_DETAIL, "");
        mPlayIndex = sp.getInt(LAST_CHAPTER_INDEX, 0);
        mRepeatMode = sp.getInt(REPEAT_MODE, LIST_LOOP);
        if (!TextUtils.isEmpty(musicAlbum)) {
            Gson gson = new Gson();
            Type genType = getClass().getSuperclass();
            Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            M data = gson.fromJson(musicAlbum, type);
            setMusicAlbum(data);
            mAlbumIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
        }*/
    }

    public boolean isInited() {
        return mMusicAlbum != null;
    }

    private void fitShuffle() {
        mShufflePlayingList.clear();
        mShufflePlayingList.addAll(mOriginPlayingList);
        Collections.shuffle(mShufflePlayingList);
    }

    public Enum changeMode() {
        if (mRepeatMode == RepeatMode.LIST_LOOP) {
            mRepeatMode = RepeatMode.ONE_LOOP;
        } else if (mRepeatMode == RepeatMode.ONE_LOOP) {
            mRepeatMode = RepeatMode.RANDOM;
        } else {
            mRepeatMode = RepeatMode.LIST_LOOP;
        }
        return mRepeatMode;
    }

    public B getMusicAlbum() {
        return mMusicAlbum;
    }

    public void setMusicAlbum(B musicAlbum) {
        this.mMusicAlbum = musicAlbum;
        mOriginPlayingList.clear();
        mOriginPlayingList.addAll(mMusicAlbum.getMusics());
        fitShuffle();
    }

    public List<M> getPlayingList() {
        if (mRepeatMode == RepeatMode.RANDOM) {
            return mShufflePlayingList;
        } else {
            return mOriginPlayingList;
        }
    }

    public List<M> getOriginPlayingList() {
        return mOriginPlayingList;
    }

    public M getCurrentPlayingMusic() {
        return getPlayingList().get(mPlayIndex);
    }

    public Enum getRepeatMode() {
        return mRepeatMode;
    }

    public void countPreviousIndex() {
        if (mPlayIndex == 0) {
            mPlayIndex = (getPlayingList().size() - 1);
        } else {
            --mPlayIndex;
        }
        mAlbumIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
    }

    public void countNextIndex() {
        if (mPlayIndex == (getPlayingList().size() - 1)) {
            mPlayIndex = 0;
        } else {
            ++mPlayIndex;
        }
        mAlbumIndex = mOriginPlayingList.indexOf(getCurrentPlayingMusic());
    }

    public int getAlbumIndex() {
        return mAlbumIndex;
    }

    public void setAlbumIndex(int albumIndex) {
        mAlbumIndex = albumIndex;
        mPlayIndex = getPlayingList().indexOf(mOriginPlayingList.get(mAlbumIndex));
    }

    public void clear(Context context) {
        saveRecords(context);
    }

    public void saveRecords(Context context) {
        /*Gson gson = new Gson();

        SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);

        sp.edit().putString(LAST_BOOK_DETAIL, gson.toJson(mMusicAlbum)).apply();
        sp.edit().putInt(LAST_CHAPTER_INDEX, mPlayIndex).apply();
        sp.edit().putInt(REPEAT_MODE, mRepeatMode).apply();*/
    }
}
