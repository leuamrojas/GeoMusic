package com.manuelrojas.geomusic.data.utils;

public class ConvertUtil {

    public static String convertSecondsToMin(String seconds) {
        int min = Integer.parseInt(seconds)/60;
        int sec = Integer.parseInt(seconds)%60;
        String pad = sec<10 ? "0" : "";
        return min + ":" + pad + sec;
    }
}
