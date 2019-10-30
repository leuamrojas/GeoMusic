package com.manuelrojas.geomusic.data.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//@Entity(tableName = "track", foreignKeys = @ForeignKey( entity = ArtistEntity.class, parentColumns = "mbid",
//        childColumns = "artist_id", onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE),
//        indices = {@Index("artist_id")})
@Entity(tableName = "track")
public class TrackEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "artist_id")
    private String artistId;
//    @PrimaryKey
//    @NonNull
    private String name;
    private String duration;
    private String listeners;
    private String mbid;
    private String url;
    private int rank;
    @ColumnInfo(name = "image_small")
    private String imageSmall;
    @ColumnInfo(name = "image_medium")
    private String imageMedium;
    @ColumnInfo(name = "image_large")
    private String imageLarge;
    @ColumnInfo(name = "image_extra_large")
    private String imageExtraLarge;
    @ColumnInfo(name = "artist_name")
    private String artistName;
    @ColumnInfo(name = "artist_url")
    private String artistUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArtistId() {
        return artistId;
    }

    public void setArtistId(String artistId) {
        this.artistId = artistId;
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
}
