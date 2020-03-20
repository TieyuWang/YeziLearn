package com.yezi.yezilearn.ipc.binder.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

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
        public static final int TRANSACTION_SET_VOLUME = IBinder.FIRST_CALL_TRANSACTION +1;
        public static final int TRANSACTION_GET_VOLUME = IBinder.FIRST_CALL_TRANSACTION +2;
        final String TAG = "IAudioService.Stub";

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            switch (code){
                case INTERFACE_TRANSACTION:
                    Log.d(TAG, "onTransact: INTERFACE_TRANSACTION");
                    return true;
                case TRANSACTION_SET_VOLUME:
                    Log.d(TAG, "onTransact: TRANSACTION_SET_VOLUME");
                    setVolume(data.readInt());
                    return true;
                case TRANSACTION_GET_VOLUME:
                    Log.d(TAG, "onTransact: TRANSACTION_GET_VOLUME");
                    if(reply != null) {
                        Log.d(TAG, "onTransact: != null "+getVolume());
                    //    reply.writeNoException();
                        reply.writeInt(getVolume());
                    }
                    return true;

                default:
                    Log.d(TAG, "onTransact: no match code");
            }

            return super.onTransact(code, data, reply, flags);
        }

        public static IAudioService asInterface(IBinder binder){

            return new Proxy(binder);
        }
    }
}
