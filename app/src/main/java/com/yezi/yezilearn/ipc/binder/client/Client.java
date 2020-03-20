package com.yezi.yezilearn.ipc.binder.client;

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
import com.yezi.yezilearn.ipc.binder.server.IAudioService;
import com.yezi.yezilearn.ipc.binder.server.Server;

/**
 * @author : yezi
 * @date : 2020/3/19 10:32
 * desc   :
 * version: 1.0
 */
public class Client extends AppCompatActivity {
    IAudioService mAudioService;
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
        bindService(new Intent(this, Server.class),mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mAudioService = IAudioService.Stub.asInterface(iBinder);
            Toast.makeText(getApplicationContext(),"服务连接成功",Toast.LENGTH_LONG).show();
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Toast.makeText(getApplicationContext(),"服务断开连接",Toast.LENGTH_LONG).show();
        }
    };
}
