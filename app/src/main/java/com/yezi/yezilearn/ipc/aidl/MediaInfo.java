package com.yezi.yezilearn.ipc.aidl;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/**
 * @author : yezi
 * @date : 2020/3/21 15:22
 * desc   :
 * version: 1.0
 */
public class MediaInfo implements Parcelable {
    private final String TAG = "MediaInfo";

    private int volumeIndex;
    private int stream;
    private String name;

    public MediaInfo(){

    }

    public MediaInfo(int volumeIndex ,int stream, String name){
        this.volumeIndex = volumeIndex;
        this.stream = stream;
        this.name = name;
    }

    public MediaInfo(Parcel in) {
        Log.d(TAG, "MediaInfo: parcel in");
        volumeIndex = in.readInt();
        stream = in.readInt();
        name = in.readString();
    }

    public void readFromParcel(Parcel in){
        volumeIndex = in.readInt();
        stream = in.readInt();
        name = in.readString();
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
        return "MediaInfo{" +
                ", volumeIndex=" + volumeIndex +
                ", stream=" + stream +
                ", name='" + name + '\'' +
                '}';
    }

    public static final Creator<MediaInfo> CREATOR = new Creator<MediaInfo>() {
        private final String TAG = "MediaInfo.CREATOR";
        @Override
        public MediaInfo createFromParcel(Parcel in) {
            Log.d(TAG, "createFromParcel: ");
            return new MediaInfo(in);
        }

        @Override
        public MediaInfo[] newArray(int size) {
            Log.d(TAG, "newArray: "+size);
            return new MediaInfo[size];
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
        parcel.writeInt(volumeIndex);
        parcel.writeInt(stream);
        parcel.writeString(name);
    }
}
