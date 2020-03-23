package com.yezi.yezilearn.ipc.binder;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;

import com.yezi.yezilearn.ipc.bean.AudioInfo;
import com.yezi.yezilearn.ipc.bean.MusicInfo;

import java.util.concurrent.BlockingDeque;

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
     * @throws RemoteException
     */
    MusicInfo getMusic() throws RemoteException;

    /**
     * in
     * @param info
     * @throws RemoteException
     */
    void addIn(AudioInfo info) throws RemoteException;

    /**
     * out
     * @param info
     * @throws RemoteException
     */
    void addOut(AudioInfo info) throws RemoteException;

    /**
     * inout
     * @param info
     * @return
     * @throws RemoteException
     */
    void addInout(AudioInfo info) throws RemoteException;

    /**
     * 注册callback
     * @param callback
     * @throws RemoteException
     */
    void registerCallback(IAudioInfoCallback callback) throws RemoteException;

    /**
     * 注销callback
     * @param callback
     * @throws RemoteException
     */
    void unregisterCallback(IAudioInfoCallback callback) throws RemoteException;

    abstract class Stub extends Binder implements IAudioService{
        public static final int TRANSACTION_SET_VOLUME = IBinder.FIRST_CALL_TRANSACTION +1;
        public static final int TRANSACTION_GET_VOLUME = IBinder.FIRST_CALL_TRANSACTION +2;

        public static final int TRANSACTION_SET_MUSIC = IBinder.FIRST_CALL_TRANSACTION +3;
        public static final int TRANSACTION_GET_MUSIC = IBinder.FIRST_CALL_TRANSACTION +4;
        public static final int TRANSACTION_ADD_IN = IBinder.FIRST_CALL_TRANSACTION +5;
        public static final int TRANSACTION_ADD_OUT = IBinder.FIRST_CALL_TRANSACTION +6;
        public static final int TRANSACTION_ADD_INOUT = IBinder.FIRST_CALL_TRANSACTION +7;
        public static final int TRANSACTION_REGISTER_CALLBACK = IBinder.FIRST_CALL_TRANSACTION +8;


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
                case TRANSACTION_ADD_IN:
                    Log.d(TAG, "onTransact: TRANSACTION_ADD_IN");
                    data.enforceInterface(DESCRIPTOR);
                    AudioInfo audioInfoIn = null;
                    if (data.readInt() == 1){
                        audioInfoIn = AudioInfo.CREATOR.createFromParcel(data);
                    }
                    addIn(audioInfoIn);
                    reply.writeNoException();

                    return true;
                case TRANSACTION_ADD_OUT:
                    Log.d(TAG, "onTransact: TRANSACTION_ADD_OUT");
                    data.enforceInterface(DESCRIPTOR);
                    AudioInfo audioInfoOut = new AudioInfo();
                    addOut(audioInfoOut);
                    reply.writeNoException();
                    if(audioInfoOut != null) {
                        reply.writeInt(1);
                        audioInfoOut.writeToParcel(reply, 0);
                    }else {
                        reply.writeInt(0);
                    }
                    return true;
                case TRANSACTION_ADD_INOUT:
                    Log.d(TAG, "onTransact: TRANSACTION_ADD_INOUT");
                    data.enforceInterface(DESCRIPTOR);
                    AudioInfo audioInfoInout = null;
                    if (data.readInt() == 1){
                        audioInfoInout = AudioInfo.CREATOR.createFromParcel(data);
                    }
                    reply.writeNoException();
                    if(audioInfoInout != null) {
                        reply.writeInt(1);
                        audioInfoInout.writeToParcel(reply, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    }else {
                        reply.writeInt(0);
                    }
                    return true;
                case TRANSACTION_REGISTER_CALLBACK:
                    Log.d(TAG, "onTransact: TRANSACTION_REGISTER_CALLBACK");
                    data.enforceInterface(DESCRIPTOR);
                    IAudioInfoCallback callback = null;
                    callback = IAudioInfoCallback.Stub.asInterface(data.readStrongBinder());
                    registerCallback(callback);
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
        public void addIn(AudioInfo info) throws RemoteException {
            Log.d(TAG, "addIn: ");
            Parcel data = Parcel.obtain();
            Parcel replay = Parcel.obtain();
            try{
                data.writeInterfaceToken(Stub.DESCRIPTOR);
                if(info != null) {
                    data.writeInt(1);
                    info.writeToParcel(data, 0);
                }else {
                    data.writeInt(0);
                }
                mRemote.transact(Stub.TRANSACTION_ADD_IN,data,replay,0);
                replay.readException();
            }catch (SecurityException e){
                e.printStackTrace();
            }finally {
                data.recycle();
                replay.recycle();
            }
        }

        @Override
        public void addOut(AudioInfo info) throws RemoteException {
            Log.d(TAG, "addOut: ");
            Parcel data = Parcel.obtain();
            Parcel replay = Parcel.obtain();
            try{
                data.writeInterfaceToken(Stub.DESCRIPTOR);
                mRemote.transact(Stub.TRANSACTION_ADD_OUT,data,replay,0);
                replay.readException();
                if(replay.readInt() != 0 && info != null) {
                    Log.d(TAG, "addOut: ");
                    info.readFormParcel(replay);
                }
            }catch (SecurityException e){
                e.printStackTrace();
            }finally {
                data.recycle();
                replay.recycle();
            }
        }

        @Override
        public void addInout(AudioInfo info) throws RemoteException {
            Log.d(TAG, "addInout: ");
            Parcel data = Parcel.obtain();
            Parcel replay = Parcel.obtain();
            try{
                data.writeInterfaceToken(Stub.DESCRIPTOR);
                if(info != null) {
                    data.writeInt(1);
                    info.writeToParcel(data, 0);
                }else {
                    data.writeInt(0);
                }
                mRemote.transact(Stub.TRANSACTION_ADD_INOUT,data,replay,0);
                replay.readException();
                if(replay.readInt() != 0 && info != null) {
                    info.readFormParcel(replay);
                }
            }catch (SecurityException e){
                e.printStackTrace();
            }finally {
                data.recycle();
                replay.recycle();
            }
        }

        @Override
        public void registerCallback(IAudioInfoCallback callback) throws RemoteException {
            Log.d(TAG, "registerCallback: "+callback);
            Parcel data = Parcel.obtain();
            Parcel replay = Parcel.obtain();
            try{
                data.writeInterfaceToken(Stub.DESCRIPTOR);
                if(callback != null){
                    data.writeStrongBinder(callback.asBinder());
                }else {
                    data.writeStrongBinder(null);
                }
                mRemote.transact(Stub.TRANSACTION_REGISTER_CALLBACK,data,replay,0);
                replay.readException();
            }catch (SecurityException e){
                e.printStackTrace();
            }finally {
                data.recycle();
                replay.recycle();
            }

        }

        @Override
        public void unregisterCallback(IAudioInfoCallback callback) throws RemoteException {

        }

        @Override
        public IBinder asBinder() {
            return mRemote;
        }
    }
}
