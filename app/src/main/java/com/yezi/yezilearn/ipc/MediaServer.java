package com.yezi.yezilearn.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

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
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mMediaService;
    }

    IBinder mMediaService = new IMediaService.Stub() {
        @Override
        public void setSourceIndex(int index) throws RemoteException {

        }

        @Override
        public int getSourceIndex() throws RemoteException {
            return 0;
        }

        @Override
        public void registerMediaCallback(IMediaCallback cb) throws RemoteException {

        }

        @Override
        public void unregisterMediaCallback(IMediaCallback cb) throws RemoteException {

        }

        @Override
        public void addMediaIn(MediaInfo info) throws RemoteException {

        }

        @Override
        public void addMediaOut(MediaInfo info) throws RemoteException {

        }

        @Override
        public void addMediaInout(MediaInfo info) throws RemoteException {

        }
    };
}
