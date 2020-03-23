package com.yezi.yezilearn.ipc.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.yezi.yezilearn.ipc.bean.AudioInfo;


/**
 * @author : yezi
 * @date : 2020/3/23 14:50
 * desc   :
 * version: 1.0
 */
public interface IAudioInfoCallback extends IInterface{

    /**
     * info update
     * @param info
     * @throws RemoteException
     */
    void onAudioInfoUpdate(AudioInfo info) throws RemoteException;

     abstract class Stub extends Binder implements IAudioInfoCallback{
        final String TAG = "IAudioInfoCallback.Stub";
        final static String DESCRIPTOR = "com.yezi.yezilearn.ipc.binder.IAudioInfoCallback";
        public static final int TRANSACT_onAudioInfoUpdate = FIRST_CALL_TRANSACTION + 1;

        public Stub(){
            attachInterface(this,DESCRIPTOR);
        }

        static IAudioInfoCallback asInterface(IBinder iBinder){
            if(iBinder == null){
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if(iInterface instanceof IAudioInfoCallback){
                return (IAudioInfoCallback) iInterface;
            }
            return new Proxy(iBinder);
        }

        @Override
        protected boolean onTransact(int code, @NonNull Parcel data, @Nullable Parcel reply, int flags) throws RemoteException {
            switch (code){
                case TRANSACT_onAudioInfoUpdate:
                    data.enforceInterface(DESCRIPTOR);
                    AudioInfo info = AudioInfo.CREATOR.createFromParcel(data);
                    onAudioInfoUpdate(info);
                    if(reply != null) {
                        reply.writeNoException();
                        info.writeToParcel(reply, 0);
                    }
                    return true;
                default:
                    Log.d(TAG, "onTransact: ");
            }
            return super.onTransact(code, data, reply, flags);
        }

         @Override
         public IBinder asBinder() {
             return this;
         }
     }

    class Proxy implements IAudioInfoCallback{
        IBinder mRemote;

        public Proxy(IBinder binder){
            mRemote = binder;
        }

        @Override
        public void onAudioInfoUpdate(AudioInfo info) throws RemoteException {
            Parcel data = Parcel.obtain();
            Parcel replay = Parcel.obtain();
            try{
                data.writeInterfaceToken(Stub.DESCRIPTOR);
                info.writeToParcel(data,0);
                boolean state = mRemote.transact(Stub.TRANSACT_onAudioInfoUpdate,data,replay,0);
                replay.readException();
                info.readFormParcel(replay);
            }catch (SecurityException e){
                e.printStackTrace();
            }finally {
                data.recycle();
                replay.recycle();
            }
        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }
    }
}
