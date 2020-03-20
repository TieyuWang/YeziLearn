package com.yezi.yezilearn.ipc;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yezi.yezilearn.R;
import com.yezi.yezilearn.ipc.binder.IAudioService;


/**
 * @author : yezi
 * @date : 2020/3/19 10:32
 * desc   :
 * version: 1.0
 */
public class Client extends AppCompatActivity {
    IAudioService mAudioService;
    IMediaService mMediaService;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        connectServer();
        final int[] finalIndex = {0};
        findViewById(R.id.set_volume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAudioService!=null){
                    mAudioService.setVolume(finalIndex[0]);
                    finalIndex[0]++;
                }
            }
        });
        findViewById(R.id.get_volume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mAudioService!=null){
                    int index = mAudioService.getVolume();
                    Toast.makeText(getApplicationContext(),"当前index = "+index,Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void connectServer(){
        bindService(new Intent(this, Server.class), mAudioServiceConnection, Context.BIND_AUTO_CREATE);
        bindService(new Intent(this, Server.class), mAudioServiceConnection, Context.BIND_AUTO_CREATE);
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
            mAudioService = IAudioService.Stub.asInterface(iBinder);
            Toast.makeText(getApplicationContext(),"media服务连接成功",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(getApplicationContext(),"media服务断开连接",Toast.LENGTH_LONG).show();
        }
    };
}
