package com.aware.poirecommender.openstreetmap.model.response;

import com.google.gson.annotations.SerializedName;

/**
 * Name: Osm3s
 * Description: Osm3s
 * Date: 2014-11-10
 * Created by BamBalooon
 */
public class Osm3s {
    @SerializedName("timestamp_osm_base")
    private final String timestamp;
    private final String copyright;

    public Osm3s(String timestamp, String copyright) {
        this.timestamp = timestamp;
        this.copyright = copyright;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getCopyright() {
        return copyright;
    }
}
