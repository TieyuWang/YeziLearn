package com.yezi.yezilearn.ipc.binder.server;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * @author : yezi
 * @date : 2020/3/19 10:56
 * desc   :
 * version: 1.0
 */
public class Server extends Service {
    final String TAG = "Server";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mServiceBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    int mVolumeIndex = 0;
    Binder mServiceBinder = new IAudioService.Stub() {
        @Override
        public void setVolume(int index) {

        }

        @Override
        public int getVolume() {
            return 0;
        }
    };
/*    IAudioService mServiceBinder = new IAudioServiceBinder() {
        @Override
        public void setVolume(int index) {
            mVolumeIndex = index;
            Log.d(TAG, "setVolume: "+index);
        }

        @Override
        public int getVolume() {
            Log.d(TAG, "getVolume: "+mVolumeIndex);
            return mVolumeIndex;
        }
    };*/

}
