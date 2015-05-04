package com.aware.poirecommender.openstreetmap.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Name: Element
 * Description: Element
 * Date: 2014-11-10
 * Created by BamBalooon
 */
public class Element {
    public static final String ELEMENT_NAME_TAG = "name";
    @SerializedName("type")
    private final String type;
    @SerializedName("id")
    private final long id;
    @SerializedName("lat")
    private final double lat;
    @SerializedName("lon")
    private final double lon;
    @SerializedName("tags")
    private final Map<String, String> tags;

    public Element(String type, long id, double lat, double lon, Map<String, String> tags) {
        this.type = type;
        this.id = id;
        this.lat = lat;
        this.lon = lon;
        this.tags = tags;
    }

    public String getType() {
        return type;
    }

    public long getId() {
        return id;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public Map<String, String> getTags() {
        return tags;
    }
}
