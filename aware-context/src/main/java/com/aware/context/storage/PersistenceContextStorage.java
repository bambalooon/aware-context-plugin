package com.aware.context.storage;

import android.content.SharedPreferences;
import com.aware.context.Context;
import com.aware.context.GenericContext;
import com.aware.context.transform.ContextSerialization;

/**
 * Name: PersistenceContextStorage
 * Description: PersistenceContextStorage stores Context in SharedPreferences
 * Date: 2015-03-22
 * Created by BamBalooon
 */
public class PersistenceContextStorage<CTX extends Context> implements ContextStorage<CTX> {
    public static final String CONTEXT_PREFERENCES = "CONTEXT_PREFERENCES";
    private static final String CONTEXT_PREFERENCE = "CONTEXT_PREFERENCE";

    public static PersistenceContextStorage<GenericContext> getDefaultInstance(android.content.Context applicationContext) {
        return new PersistenceContextStorage<>(
                applicationContext.getSharedPreferences(CONTEXT_PREFERENCES, android.content.Context.MODE_PRIVATE),
                ContextSerialization.getDefaultInstance());
    }

    private final SharedPreferences sharedPreferences;
    private final ContextSerialization<CTX> contextSerialization;

    protected PersistenceContextStorage(SharedPreferences sharedPreferences,
                                        ContextSerialization<CTX> contextSerialization) {
        this.sharedPreferences = sharedPreferences;
        this.contextSerialization = contextSerialization;
    }

    @Override
    public CTX getContext() {
        String contextJson = sharedPreferences.getString(CONTEXT_PREFERENCE, null);
        return contextSerialization.CONTEXT_DESERIALIZER.apply(contextJson);
    }

    @Override
    public void setContext(CTX context) {
        sharedPreferences.edit()
                .putString(CONTEXT_PREFERENCE, contextSerialization.CONTEXT_SERIALIZER.apply(context))
                .apply();
    }
}
