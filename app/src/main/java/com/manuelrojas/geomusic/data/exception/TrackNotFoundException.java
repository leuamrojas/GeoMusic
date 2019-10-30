package com.manuelrojas.geomusic.data.exception;

/**
 * Exception throw by the application when a Repo search can't return a valid result.
 */
public class TrackNotFoundException extends Exception {
    public TrackNotFoundException() {
        super();
    }
}