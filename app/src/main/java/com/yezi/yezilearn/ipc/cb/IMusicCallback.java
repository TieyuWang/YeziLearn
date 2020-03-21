package com.yezi.yezilearn.ipc.cb;

import com.yezi.yezilearn.ipc.bean.MusicInfo;

/**
 * @author : yezi
 * @date : 2020/3/21 14:36
 * desc   :
 * version: 1.0
 */
public interface IMusicCallback {
    /**
     * musicInfo 进行更新
     * @param musicInfo info
     */
    void onMusicInfoUpdate(MusicInfo musicInfo);
}
