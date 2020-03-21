package com.yezi.yezilearn.ipc.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * @author : yezi
 * @date : 2020/3/21 13:46
 * desc   :
 * version: 1.0
 */
public class MusicInfo implements Parcelable {
    private AudioInfo primaryAudio;
    private ArrayList<AudioInfo> audioInfos = new ArrayList<>();
    private String name;

    public MusicInfo(AudioInfo primaryAudio, ArrayList<AudioInfo> audioInfos, String name) {
        this.primaryAudio = primaryAudio;
        this.audioInfos = audioInfos;
        this.name = name;
    }

    @Override
    public String toString() {
        return "MusicInfo{" +
                "primaryAudio=" + primaryAudio +
                ", audioInfos=" + audioInfos +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        primaryAudio.writeToParcel(parcel,i);
        //parcel.writeParcelable(primaryAudio,i);
       // parcel.writeList(audioInfos);
        parcel.writeTypedList(audioInfos);
        parcel.writeString(name);
    }

    public static final Creator<MusicInfo> CREATOR = new Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel parcel) {
            return new MusicInfo(AudioInfo.CREATOR.createFromParcel(parcel),
                    //parcel.readArrayList(AudioInfo.class.getClassLoader()),
                    parcel.createTypedArrayList(AudioInfo.CREATOR),
                    parcel.readString());
        }

        @Override
        public MusicInfo[] newArray(int i) {
            return new MusicInfo[i];
        }
    };
}
