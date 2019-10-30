package com.manuelrojas.geomusic.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Streamable implements Parcelable {

    private String text;

    private String fulltrack;

    protected Streamable(Parcel in) {
        text = in.readString();
        fulltrack = in.readString();
    }

    public static final Creator<Streamable> CREATOR = new Creator<Streamable>() {
        @Override
        public Streamable createFromParcel(Parcel in) {
            return new Streamable(in);
        }

        @Override
        public Streamable[] newArray(int size) {
            return new Streamable[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFulltrack() {
        return fulltrack;
    }

    public void setFulltrack(String fulltrack) {
        this.fulltrack = fulltrack;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeString(fulltrack);
    }
}
