package com.yezi.yezilearn.ipc;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.yezi.yezilearn.ipc.bean.AudioInfo;
import com.yezi.yezilearn.ipc.bean.MusicInfo;
import com.yezi.yezilearn.ipc.binder.IAudioInfoCallback;
import com.yezi.yezilearn.ipc.binder.IAudioService;

/**
 * @author : yezi
 * @date : 2020/3/19 10:56
 * desc   :
 * version: 1.0
 */
public class AudioServer extends Service {
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
    MusicInfo mMusicInfo = null;
    IAudioInfoCallback mAudioInfoCallback;

    Binder mServiceBinder = new IAudioService.Stub() {
        @Override
        public void setVolume(int index) {
            Log.d(TAG, "setVolume: "+index);
            mVolumeIndex = index;
            if(mAudioInfoCallback != null){
                try {
                    mAudioInfoCallback.onAudioInfoUpdate(new AudioInfo(index,0,"callback"));
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public int getVolume() {
            Log.d(TAG, "getVolume: "+mVolumeIndex);
            return mVolumeIndex;
        }

        @Override
        public void setMusic(MusicInfo info) throws RemoteException {
            Log.d(TAG, "setMusic: "+info);
            mMusicInfo = info;
        }

        @Override
        public MusicInfo getMusic() throws RemoteException {
            Log.d(TAG, "getMusic: "+mMusicInfo);
            return mMusicInfo;
        }

        @Override
        public void addIn(AudioInfo info) throws RemoteException {
            Log.d(TAG, "addIn: "+info);
            info.setVolumeIndex(200);
        }

        @Override
        public void addOut(AudioInfo info) throws RemoteException {
            Log.d(TAG, "addOut: "+info);
            info.setVolumeIndex(200);
        }

        @Override
        public void addInout(AudioInfo info) throws RemoteException {
            Log.d(TAG, "addInout: "+info);
            info.setVolumeIndex(200);
        }

        @Override
        public void registerCallback(IAudioInfoCallback callback) throws RemoteException {

            Log.d(TAG, "registerCallback: "+callback+" pid = "+getCallingPid()+" pid = "+getCallingUid());
            mAudioInfoCallback = callback;
        }

        @Override
        public void unregisterCallback(IAudioInfoCallback callback) throws RemoteException {

        }
    };

}
