package com.aware.poirecommender.transform;

import com.google.gson.Gson;

/**
 * Name: Serializer
 * Description: Serializer
 * Date: 2015-04-27
 * Created by BamBalooon
 */
public class Serializer<T> {
    private final Gson gsonSerializer;
    private final Class<T> inputClass;

    public Serializer(Class<T> inputClass) {
        this(new Gson(), inputClass);
    }

    public Serializer(Gson gsonSerializer, Class<T> inputClass) {
        this.gsonSerializer = gsonSerializer;
        this.inputClass = inputClass;
    }

    public String serialize(T input) {
        return gsonSerializer.toJson(input);
    }

    public T deserialize(String json) {
        return gsonSerializer.fromJson(json, inputClass);
    }
}
