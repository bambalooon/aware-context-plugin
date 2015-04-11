package com.aware.context.storage;

import android.content.SharedPreferences;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Name: PersistenceContextStorage
 * Description: PersistenceContextStorage stores ContextProperties in SharedPreferences
 * Date: 2015-03-22
 * Created by BamBalooon
 */
public class PersistenceContextStorage implements ContextStorage<String> {
    public static final String CONTEXT_PREFERENCES = "CONTEXT_PREFERENCES";

    private final SharedPreferences sharedPreferences;

    public PersistenceContextStorage(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    //TODO: handle multithreaded read and write
    @Override
    public String getContextProperty(String contextPropertyId) {
        return sharedPreferences.getString(contextPropertyId, null);
    }

    @Override
    public void setContextProperty(String contextPropertyId, String contextPropertyJson) {
        sharedPreferences.edit()
                .putString(contextPropertyId, contextPropertyJson)
                .apply();
    }

    @Override
    public Map<String, String> getContextProperties() {
        Map<String, String> contextProperties = Maps.newHashMap();
        for (Map.Entry<String, ?> contextPropertyEntry : sharedPreferences.getAll().entrySet()) {
            contextProperties.put(contextPropertyEntry.getKey(), (String) contextPropertyEntry.getValue());
        }
        return contextProperties;
    }
}
