package com.yezi.yezilearn.ipc.binder.server;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author : yezi
 * @date : 2020/3/19 13:48
 * desc   :
 * version: 1.0
 */
public abstract class IAudioServiceBinder extends Binder implements IAudioService {

    @Override
    public IBinder asBinder() {
        return this;
    }

    @Override
    protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
        return super.onTransact(code, data, reply, flags);
    }


}
