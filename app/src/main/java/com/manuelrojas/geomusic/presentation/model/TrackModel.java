package com.manuelrojas.geomusic.presentation.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.manuelrojas.geomusic.domain.Artist;
import com.manuelrojas.geomusic.domain.Streamable;

public class TrackModel implements Parcelable {

    private int id;
    private String name;
    private String duration;
    private String listeners;
    private String mbid;
    private String url;
    private Streamable streamable;
    private Artist artist;
    private int rank;
    private String imageSmall;
    private String imageMedium;
    private String imageLarge;
    private String imageExtraLarge;
    private String artistName;
    private String artistUrl;

    public TrackModel() {}

    protected TrackModel(Parcel in) {
        id = in.readInt();
        name = in.readString();
        duration = in.readString();
        listeners = in.readString();
        mbid = in.readString();
        url = in.readString();
        rank = in.readInt();
        imageSmall = in.readString();
        imageMedium = in.readString();
        imageLarge = in.readString();
        imageExtraLarge = in.readString();
        artistName = in.readString();
        artistUrl = in.readString();
    }

    public static final Creator<TrackModel> CREATOR = new Creator<TrackModel>() {
        @Override
        public TrackModel createFromParcel(Parcel in) {
            return new TrackModel(in);
        }

        @Override
        public TrackModel[] newArray(int size) {
            return new TrackModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getListeners() {
        return listeners;
    }

    public void setListeners(String listeners) {
        this.listeners = listeners;
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

    public Streamable getStreamable() {
        return streamable;
    }

    public void setStreamable(Streamable streamable) {
        this.streamable = streamable;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
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

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getArtistUrl() {
        return artistUrl;
    }

    public void setArtistUrl(String artistUrl) {
        this.artistUrl = artistUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(name);
        parcel.writeString(duration);
        parcel.writeString(listeners);
        parcel.writeString(mbid);
        parcel.writeString(url);
        parcel.writeInt(rank);
        parcel.writeString(imageSmall);
        parcel.writeString(imageMedium);
        parcel.writeString(imageLarge);
        parcel.writeString(imageExtraLarge);
        parcel.writeString(artistName);
        parcel.writeString(artistUrl);
    }
}
