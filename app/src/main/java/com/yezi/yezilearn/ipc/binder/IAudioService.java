package com.yezi.yezilearn.ipc.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

import com.yezi.yezilearn.ipc.bean.MusicInfo;

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
     * @throws RemoteException
     */
    void setVolume(int index) throws RemoteException;

    /**
     * 获取音量
     * @return 返回音量
     * @throws RemoteException
     */
    int getVolume() throws RemoteException;

    /**
     * 设置Music info
     * @param info info
     * @throws RemoteException
     */
    void setMusic(MusicInfo info) throws RemoteException;

    /**
     * 获取 musicInfo
     * @return MusicInfo
     * @throws RemoteException
     */
    MusicInfo getMusic() throws RemoteException;

    abstract class Stub extends Binder implements IAudioService{
        public static final int TRANSACTION_SET_VOLUME = IBinder.FIRST_CALL_TRANSACTION +1;
        public static final int TRANSACTION_GET_VOLUME = IBinder.FIRST_CALL_TRANSACTION +2;

        public static final int TRANSACTION_SET_MUSIC = IBinder.FIRST_CALL_TRANSACTION +3;
        public static final int TRANSACTION_GET_MUSIC = IBinder.FIRST_CALL_TRANSACTION +4;

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
                case TRANSACTION_SET_MUSIC:
                    Log.d(TAG, "onTransact: TRANSACTION_GET_VOLUME");
                    data.enforceInterface(DESCRIPTOR);
                  //  setMusic(data.readTypedObject(MusicInfo.CREATOR));
                    setMusic((MusicInfo) data.readParcelable(MusicInfo.class.getClassLoader()));

                    reply.writeNoException();
                    return true;
                case TRANSACTION_GET_MUSIC:
                    Log.d(TAG, "onTransact: TRANSACTION_GET_VOLUME");
                    data.enforceInterface(DESCRIPTOR);
                    MusicInfo music = getMusic();
                    reply.writeNoException();
                  //  reply.writeTypedObject(music,0);
                    reply.writeParcelable(music,0);
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
            // 寻找本地的接口
            IInterface iInterface = binder.queryLocalInterface(DESCRIPTOR);
            if(iInterface instanceof IAudioService){
                return (IAudioService) iInterface;
            }
            // 返回代理
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
            }catch (SecurityException e){
                e.printStackTrace();
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
                mRemote.transact(Stub.TRANSACTION_GET_VOLUME, data, replay, 0);
                //  replay.readException();
                replay.readException();
                result = replay.readInt();
                Log.d(TAG, "getVolume: " + result + " " + data.readInt());
            }catch (SecurityException e){
                e.printStackTrace();
            }finally {
                data.recycle();
                replay.recycle();
            }

            return result;
        }

        @Override
        public void setMusic(MusicInfo info) throws RemoteException {
            Log.d(TAG, "setMusic: "+info);
            Parcel data = Parcel.obtain();
            Parcel replay = Parcel.obtain();
            try {
                data.writeInterfaceToken(Stub.DESCRIPTOR);
                data.writeParcelable(info,0);
           //     data.writeTypedObject(info,0);
                mRemote.transact(Stub.TRANSACTION_SET_MUSIC,data,replay,0);
                replay.readException();
                Log.d(TAG, "setMusic: transact over");
            }catch (SecurityException e){
                e.printStackTrace();
            }finally {
                data.recycle();
                replay.recycle();
            }
        }

        @Override
        public MusicInfo getMusic() throws RemoteException {
            Log.d(TAG, "getVolume: ");
            Parcel data = Parcel.obtain();
            Parcel replay = Parcel.obtain();
            MusicInfo result = null;
            try {
                data.writeInterfaceToken(Stub.DESCRIPTOR);
                mRemote.transact(Stub.TRANSACTION_GET_MUSIC, data, replay, 0);
                //  replay.readException();
                replay.readException();
            //    result = replay.readTypedObject(MusicInfo.CREATOR);
                result = replay.readParcelable(MusicInfo.class.getClassLoader());
                Log.d(TAG, "getVolume: " + result + " " + data.readInt());
            }catch (SecurityException e){
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
