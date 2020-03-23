package com.yezi.yezilearn.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.yezi.yezilearn.ipc.aidl.IMediaCallback;
import com.yezi.yezilearn.ipc.aidl.IMediaService;
import com.yezi.yezilearn.ipc.aidl.MediaInfo;

/**
 * @author : yezi
 * @date : 2020/3/21 17:19
 * desc   :
 * version: 1.0
 */
public class MediaServer extends Service {
    final String TAG = "MediaServer";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMediaService;
    }

    int mIndex = 0;
    IBinder mMediaService = new IMediaService.Stub() {
        @Override
        public void setSourceIndex(int index) throws RemoteException {
            Log.d(TAG, "setSourceIndex: ");
            mIndex = index;
        }

        @Override
        public int getSourceIndex() throws RemoteException {
            return mIndex;
        }

        @Override
        public void registerMediaCallback(IMediaCallback cb) throws RemoteException {

        }

        @Override
        public void unregisterMediaCallback(IMediaCallback cb) throws RemoteException {

        }

        @Override
        public void addMediaIn(MediaInfo info) throws RemoteException {
            Log.d(TAG, "addMediaIn: "+info);
            info.setVolumeIndex(100);
        }

        @Override
        public void addMediaOut(MediaInfo info) throws RemoteException {
            Log.d(TAG, "addMediaOut: "+info);
            info.setVolumeIndex(100);
        }

        @Override
        public void addMediaInout(MediaInfo info) throws RemoteException {
            Log.d(TAG, "addMediaInout: "+info);
            info.setVolumeIndex(100);
        }
    };
}
