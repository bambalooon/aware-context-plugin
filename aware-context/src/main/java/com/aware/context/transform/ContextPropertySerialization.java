package com.aware.context.transform;

import com.aware.context.property.ContextProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

/**
 * Name: ContextPropertySerialization
 * Description: ContextPropertySerialization provides function to serialize and deserialize ContextProperties
 * Date: 2015-03-22
 * Created by BamBalooon
 */
public class ContextPropertySerialization<CP extends ContextProperty> {
    private static final Gson GSON_SERIALIZER = new GsonBuilder()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .create();
    private final Class<CP> contextPropertyClass;

    public ContextPropertySerialization(Class<CP> contextPropertyClass) {
        this.contextPropertyClass = contextPropertyClass;
    }

    public String serialize(CP contextProperty) {
        return GSON_SERIALIZER.toJson(contextProperty);
    }

    public CP deserialize(String contextPropertyJson) {
        return GSON_SERIALIZER.fromJson(contextPropertyJson, contextPropertyClass);
    }
}
