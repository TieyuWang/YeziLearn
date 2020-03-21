// IMediaService.aidl
package com.yezi.yezilearn.ipc.aidl;
import com.yezi.yezilearn.ipc.aidl.IMediaCallback;
import com.yezi.yezilearn.ipc.aidl.MediaInfo;


interface IMediaService {

    void setSourceIndex(int index);

    int getSourceIndex();

    void registerMediaCallback(IMediaCallback cb);

    void unregisterMediaCallback(IMediaCallback cb);

    void addMediaIn(in MediaInfo info);

    void addMediaOut(out MediaInfo info);

    void addMediaInout(inout MediaInfo info);

}
