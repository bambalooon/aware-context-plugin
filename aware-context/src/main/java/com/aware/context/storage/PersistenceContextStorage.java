package com.aware.context.storage;

import android.content.SharedPreferences;
import com.aware.context.property.ContextProperty;
import com.aware.context.transform.ContextPropertySerialization;
import com.google.common.collect.Lists;

import java.util.Collection;

/**
 * Name: PersistenceContextStorage
 * Description: PersistenceContextStorage stores Context in SharedPreferences
 * Date: 2015-03-22
 * Created by BamBalooon
 */
public class PersistenceContextStorage<CP extends ContextProperty>
        extends AbstractContextStorage<CP> implements ContextStorage<CP> {

    public static final String CONTEXT_PREFERENCES = "CONTEXT_PREFERENCES";

    private final SharedPreferences sharedPreferences;
    private final ContextPropertySerialization<CP> contextPropertySerialization;

    public PersistenceContextStorage(SharedPreferences sharedPreferences,
                                     ContextPropertySerialization<CP> contextPropertySerialization) {
        this.sharedPreferences = sharedPreferences;
        this.contextPropertySerialization = contextPropertySerialization;
    }

    @Override
    public CP getContextProperty(String contextPropertyId) {
        String contextJson = sharedPreferences.getString(contextPropertyId, null);
        return contextPropertySerialization.CONTEXT_DESERIALIZER.apply(contextJson);
    }

    @Override
    public void setContextProperty(CP contextProperty) {
        sharedPreferences.edit()
                .putString(
                        contextProperty.getId(),
                        contextPropertySerialization.CONTEXT_SERIALIZER.apply(contextProperty))
                .apply();
    }

    @Override
    public Collection<CP> getContextProperties() {
        Collection<CP> contextProperties = Lists.newLinkedList();
        for (Object contextPropertyJson : sharedPreferences.getAll().values()) {
            contextProperties.add(
                    contextPropertySerialization.CONTEXT_DESERIALIZER.apply((String) contextPropertyJson)
            );
        }
        return contextProperties;
    }
}
