package com.yezi.yezilearn.ipc.binder;

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
    void setVolume(int index) throws RemoteException;

    /**
     * 获取音量
     * @return 返回音量
     */
    int getVolume() throws RemoteException;


    abstract class Stub extends Binder implements IAudioService{
        public static final int TRANSACTION_SET_VOLUME = IBinder.FIRST_CALL_TRANSACTION +1;
        public static final int TRANSACTION_GET_VOLUME = IBinder.FIRST_CALL_TRANSACTION +2;

        public static final String DESCRIPTOR = "com.yezi.yezilearn.ipc.binder.IAudioService";

        final String TAG = "IAudioService.Stub";

        public Stub(){
            attachInterface(this,DESCRIPTOR);
        }

        @Override
        public IBinder asBinder() {
            return this;
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @NonNull Parcel reply, int flags) throws RemoteException {
            switch (code){
                case INTERFACE_TRANSACTION:
                    Log.d(TAG, "onTransact: INTERFACE_TRANSACTION");
                    reply.writeString(DESCRIPTOR);
                    return true;
                case TRANSACTION_SET_VOLUME:
                    Log.d(TAG, "onTransact: TRANSACTION_SET_VOLUME");
                    data.enforceInterface(DESCRIPTOR);
                    setVolume(data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_GET_VOLUME:
                    Log.d(TAG, "onTransact: TRANSACTION_GET_VOLUME");
                    data.enforceInterface(DESCRIPTOR);
                    int result = getVolume();
                    reply.writeNoException();
                    reply.writeInt(result);
                    return true;

                default:
                    Log.d(TAG, "onTransact: no match code");
            }

            return super.onTransact(code, data, reply, flags);
        }

        public static IAudioService asInterface(IBinder binder){
            if(binder == null){
                return null;
            }

            IInterface iInterface = binder.queryLocalInterface(DESCRIPTOR);
            if(iInterface instanceof IAudioService){
                return (IAudioService) iInterface;
            }

            return new Proxy(binder);
        }
    }

    /**
     * @author : yezi
     * @date : 2020/3/20 13:28
     * desc   :
     * version: 1.0
     */
    class Proxy implements IAudioService {
        final String TAG = "IAudioService.Proxy";

        private IBinder mRemote;

        public Proxy(IBinder remote){
            mRemote = remote;
        }

        @Override
        public void setVolume(int index) throws RemoteException{
            Log.d(TAG, "setVolume: "+index);
            Parcel data = Parcel.obtain();
            Parcel replay = Parcel.obtain();
            try {
                data.writeInterfaceToken(Stub.DESCRIPTOR);
                data.writeInt(index);
                mRemote.transact(Stub.TRANSACTION_SET_VOLUME,data,replay,0);
                replay.readException();
                Log.d(TAG, "setVolume: transact over");
            }finally {
                data.recycle();
                replay.recycle();
            }
        }

        @Override
        public int getVolume() throws RemoteException {
            Log.d(TAG, "getVolume: ");
            Parcel data = Parcel.obtain();
            Parcel replay = Parcel.obtain();
            int result = 0;
            try {
                data.writeInterfaceToken(Stub.DESCRIPTOR);
                mRemote.transact(Stub.TRANSACTION_GET_VOLUME,data,replay,0);
              //  replay.readException();
                replay.readException();
                result = replay.readInt();
                Log.d(TAG, "getVolume: "+result+" "+data.readInt());
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
}
