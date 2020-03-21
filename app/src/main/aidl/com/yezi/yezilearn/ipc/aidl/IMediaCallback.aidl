// IMediaCallback.aidl
package com.yezi.yezilearn.ipc.aidl;
import com.yezi.yezilearn.ipc.aidl.MediaInfo;

// Declare any non-default types here with import statements

interface IMediaCallback {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void onMediaInfoupdate(in MediaInfo info);
}
