package com.yezi.yezilearn.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.yezi.yezilearn.R;
import com.yezi.yezilearn.databinding.ActivityIocClientBinding;
import com.yezi.yezilearn.ipc.aidl.IMediaService;
import com.yezi.yezilearn.ipc.aidl.MediaInfo;
import com.yezi.yezilearn.ipc.bean.AudioInfo;
import com.yezi.yezilearn.ipc.bean.MusicInfo;
import com.yezi.yezilearn.ipc.binder.IAudioService;

import java.util.ArrayList;


/**
 * @author : yezi
 * @date : 2020/3/19 10:32
 * desc   :
 * version: 1.0
 */
public class Client extends AppCompatActivity {
    final String TAG = "Client";
    IAudioService mAudioService;
    IMediaService mMediaService;

    ActivityIocClientBinding mActivityIocClientBinding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivityIocClientBinding = DataBindingUtil.setContentView(this,R.layout.activity_ioc_client);
        mActivityIocClientBinding.setButtonOnClickListener(mOnClickListener);

        initDates();

        connectServer();


        findViewById(R.id.set_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAudioService!=null){
                    try {
                        mAudioService.setMusic(musicInfo);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        findViewById(R.id.get_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAudioService!=null){
                    MusicInfo result = null;
                    try {
                        result = mAudioService.getMusic();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(getApplicationContext(),"当前 music = "+result,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void connectServer(){
        bindService(new Intent(this, AudioServer.class), mAudioServiceConnection, Context.BIND_AUTO_CREATE);
        bindService(new Intent(this, MediaServer.class), mMediaServiceConnection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection mAudioServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mAudioService = IAudioService.Stub.asInterface(iBinder);
            Toast.makeText(getApplicationContext(),"audio服务连接成功",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(getApplicationContext(),"audio服务断开连接",Toast.LENGTH_LONG).show();
        }
    };

    ServiceConnection mMediaServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mMediaService = IMediaService.Stub.asInterface(iBinder);
            Toast.makeText(getApplicationContext(),"media服务连接成功",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(getApplicationContext(),"media服务断开连接",Toast.LENGTH_LONG).show();
        }
    };
    static Toast mToast;
    void showToast(String msg){
        Log.d(TAG, msg);
        if(mToast != null){
            mToast.cancel();
        }
        mToast = Toast.makeText(this,msg,Toast.LENGTH_LONG);
        mToast.show();
    }

    final int [] volumeIndex = {0};

    AudioInfo primaryAudioInfo = null;
    static MusicInfo musicInfo = null;
    void initDates(){
        primaryAudioInfo = new AudioInfo(1,2,"primary");
        ArrayList<AudioInfo> audioInfoArrayList = new ArrayList<>(3);
        audioInfoArrayList.add(new AudioInfo(1,2,"name 1"));
        audioInfoArrayList.add(new AudioInfo(1,2,"name 2"));
        audioInfoArrayList.add(new AudioInfo(1,2,"name 3"));
        musicInfo = new MusicInfo(primaryAudioInfo,audioInfoArrayList,"music 1");
    }



    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(mAudioService == null || mMediaService == null) {
                return;
            }
            try {
                switch (view.getId()){
                    case R.id.set_volume:
                        mAudioService.setVolume(volumeIndex[0]);
                        volumeIndex[0]++;
                        break;
                    case R.id.get_volume:
                        int volumeIndex = mAudioService.getVolume();
                        showToast("当前 volume index = "+volumeIndex);
                        break;
                    case R.id.set_music:
                        mAudioService.setMusic(musicInfo);
                        break;
                    case R.id.get_music:
                        MusicInfo music = mAudioService.getMusic();
                        showToast("当前 music = "+music);
                        break;
                    case R.id.add_in:
                        MediaInfo mediaInfoIn = new MediaInfo(20,20,"mediaInfoIn");
                        mMediaService.addMediaIn(mediaInfoIn);
                        showToast("当前 media in = "+mediaInfoIn);
                        break;
                    case R.id.add_out:
                        MediaInfo mediaInfoOut = new MediaInfo(20,20,"mediaInfoOut");
                        mMediaService.addMediaOut(mediaInfoOut);
                        showToast("当前 media out = "+mediaInfoOut);
                        break;
                    case R.id.add_inout:
                        MediaInfo mediaInfoInout = new MediaInfo(20,20,"mediaInfoInout");
                        mMediaService.addMediaInout(mediaInfoInout);
                        showToast("当前 media inout = "+mediaInfoInout);
                        break;
                    default:
                        break;
                }
            }catch (RemoteException e){
                e.printStackTrace();
            }

        }
    };
}
