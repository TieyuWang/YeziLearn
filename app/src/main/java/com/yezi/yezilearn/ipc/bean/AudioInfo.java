package com.yezi.yezilearn.ipc.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * @author : yezi
 * @date : 2020/3/21 10:29
 * desc   :
 * version: 1.0
 */
public class AudioInfo implements Parcelable {
    private final String TAG = "AudioInfo";

    private int volumeIndex;
    private int stream;
    private String name;

    public AudioInfo(int volumeIndex ,int stream, String name){
        this.volumeIndex = volumeIndex;
        this.stream = stream;
        this.name = name;
    }

    protected AudioInfo(Parcel in) {
        Log.d(TAG, "AudioInfo: parcel in");
        volumeIndex = in.readInt();
        stream = in.readInt();
        name = in.readString();
    }

    public AudioInfo() {

    }

    public int getVolumeIndex() {
        return volumeIndex;
    }

    public void setVolumeIndex(int volumeIndex) {
        this.volumeIndex = volumeIndex;
    }

    public int getStream() {
        return stream;
    }

    public void setStream(int stream) {
        this.stream = stream;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AudioInfo{" +
                ", volumeIndex=" + volumeIndex +
                ", stream=" + stream +
                ", name='" + name + '\'' +
                '}';
    }

    public static final Creator<AudioInfo> CREATOR = new Creator<AudioInfo>() {
        private final String TAG = "AudioInfo.CREATOR";
        @Override
        public AudioInfo createFromParcel(Parcel in) {
            Log.d(TAG, "createFromParcel: ");
            return new AudioInfo(in);
        }

        @Override
        public AudioInfo[] newArray(int size) {
            Log.d(TAG, "newArray: "+size);
            return new AudioInfo[size];
        }
    };

    @Override
    public int describeContents() {
        Log.d(TAG, "describeContents: ");
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        Log.d(TAG, "writeToParcel: flag = "+i);
        Log.d(TAG, "writeToParcel: "+volumeIndex+stream+name);
        parcel.writeInt(volumeIndex);
        parcel.writeInt(stream);
        parcel.writeString(name);
    }

    public void readFormParcel(Parcel in) {
        Log.d(TAG, "readFormParcel: ");
        volumeIndex = in.readInt();
        stream = in.readInt();
        name = in.readString();
    }
}
