package com.yezi.yezilearn.ipc.binder.server;

import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

/**
 * @author : yezi
 * @date : 2020/3/20 13:28
 * desc   :
 * version: 1.0
 */
public class Proxy implements IAudioService {
    final String TAG = "IAudioService.Proxy";

    private IBinder mRemote;

    public Proxy(IBinder remote){
        mRemote = remote;
    }

    @Override
    public void setVolume(int index) {
        Log.d(TAG, "setVolume: "+index);
        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();

        try {
         //   data.writeInterfaceToken();
            data.writeInt(index);
            mRemote.transact(Stub.TRANSACTION_SET_VOLUME,data,replay,0);
            Log.d(TAG, "setVolume: transact over");
        } catch (RemoteException e) {
            e.printStackTrace();
        }finally {
            data.recycle();
            replay.recycle();
        }
    }

    @Override
    public int getVolume() {
        Log.d(TAG, "getVolume: ");
        Parcel data = Parcel.obtain();
        Parcel replay = Parcel.obtain();
        int result = 0;
        try {
            mRemote.transact(Stub.TRANSACTION_GET_VOLUME,data,replay,0);
          //  replay.readException();
            result = replay.readInt();
            Log.d(TAG, "getVolume: "+result+" "+data.readInt());
        } catch (RemoteException e) {
            e.printStackTrace();
        }finally {
            data.recycle();
            replay.recycle();
        }

        return result;
    }

    @Override
    public IBinder asBinder() {
        return mRemote;
    }
}
