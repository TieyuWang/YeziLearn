package com.yezi.yezilearn.ipc.binder.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author : yezi
 * @date : 2020/3/19 13:10
 * desc   :
 * version: 1.0
 */
public interface IAudioService extends IInterface {
    /**
     * 设置音量
     * @param index
     */
    void setVolume(int index);

    /**
     * 获取音量
     * @return 返回音量
     */
    int getVolume();


    abstract class Stub extends Binder implements IAudioService{

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            return super.onTransact(code, data, reply, flags);
        }

        public static IAudioService asInterface(IBinder binder){
            if(!(binder instanceof  Stub)){
                return null;
            }
            return new IAudioService() {
                @Override
                public void setVolume(int index) {

                }

                @Override
                public int getVolume() {
                    return 0;
                }

                @Override
                public IBinder asBinder() {
                    return null;
                }
            };
        }
    }
}
