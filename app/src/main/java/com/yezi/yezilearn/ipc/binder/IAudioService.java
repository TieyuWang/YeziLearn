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
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            switch (code){
                case INTERFACE_TRANSACTION:
                    Log.d(TAG, "onTransact: INTERFACE_TRANSACTION");
                    reply.writeInterfaceToken(DESCRIPTOR);
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
}
