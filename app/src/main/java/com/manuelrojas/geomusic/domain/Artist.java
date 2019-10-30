package com.manuelrojas.geomusic.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Artist implements Parcelable {

    private String mbid;
    private String name;
    private String url;
    private String listeners;
    private String imageSmall;
    private String imageMedium;
    private String imageLarge;
    private String imageExtraLarge;

    public Artist(){}

    protected Artist(Parcel in) {
        mbid = in.readString();
        name = in.readString();
        url = in.readString();
        listeners = in.readString();
        imageSmall = in.readString();
        imageMedium = in.readString();
        imageLarge = in.readString();
        imageExtraLarge = in.readString();
    }

    public static final Creator<Artist> CREATOR = new Creator<Artist>() {
        @Override
        public Artist createFromParcel(Parcel in) {
            return new Artist(in);
        }

        @Override
        public Artist[] newArray(int size) {
            return new Artist[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMbid() {
        return mbid;
    }

    public void setMbid(String mbid) {
        this.mbid = mbid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
    }

    public String getImageSmall() {
        return imageSmall;
    }

    public void setImageSmall(String imageSmall) {
        this.imageSmall = imageSmall;
    }

    public String getImageMedium() {
        return imageMedium;
    }

    public void setImageMedium(String imageMedium) {
        this.imageMedium = imageMedium;
    }

    public String getImageLarge() {
        return imageLarge;
    }

    public void setImageLarge(String imageLarge) {
        this.imageLarge = imageLarge;
    }

    public String getImageExtraLarge() {
        return imageExtraLarge;
    }

    public void setImageExtraLarge(String imageExtraLarge) {
        this.imageExtraLarge = imageExtraLarge;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mbid);
        parcel.writeString(name);
        parcel.writeString(url);
        parcel.writeString(listeners);
        parcel.writeString(imageSmall);
        parcel.writeString(imageMedium);
        parcel.writeString(imageLarge);
        parcel.writeString(imageExtraLarge);
    }
}
