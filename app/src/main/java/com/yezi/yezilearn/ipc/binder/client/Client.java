package com.yezi.yezilearn.ipc.binder.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

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
        findViewById(R.id.set_volume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        findViewById(R.id.get_volume).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
}
