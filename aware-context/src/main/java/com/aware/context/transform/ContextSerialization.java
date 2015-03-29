package com.aware.context.transform;

import com.aware.context.Context;
import com.google.common.base.Function;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

/**
 * Name: ContextSerialization
 * Description: ContextSerialization provides function to serialize and deserialize Context
 * Date: 2015-03-22
 * Created by BamBalooon
 */
public class ContextSerialization<CTX extends Context> {
    private static final Gson GSON_SERIALIZER = new GsonBuilder()
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .create();
    private final Class<CTX> contextClass;

    public ContextSerialization(Class<CTX> contextClass) {
        this.contextClass = contextClass;
    }

    public final Function<CTX, String> CONTEXT_SERIALIZER = new Function<CTX, String>() {
        @Override
        public String apply(CTX context) {
            return GSON_SERIALIZER.toJson(context);
        }
    };

    public final Function<String, CTX> CONTEXT_DESERIALIZER = new Function<String, CTX>() {
        @Override
        public CTX apply(String contextJson) {
            return GSON_SERIALIZER.fromJson(contextJson, contextClass);
        }
    };
}
