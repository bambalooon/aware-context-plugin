package com.aware.context.management;

import com.aware.context.Context;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.storage.ContextStorage;

/**
 * Name: ContextManagement
 * Description: ContextManagement stores GenericContext in memory and SharedPreferences,
 *              it allows to retrieve Context and modify ContextProperties
 * Date: 2015-03-22
 * Created by BamBalooon
 */
public final class ContextManagement {
    private final ContextStorage<GenericContextProperty> contextStorage;

    public ContextManagement(ContextStorage<GenericContextProperty> contextStorage) {
        this.contextStorage = contextStorage;
    }

    public synchronized Context<GenericContextProperty> getContext() {
        return contextStorage.getContext();
    }

    //TODO: should it be block write of 2 different context properties?
    public synchronized void setContextProperty(GenericContextProperty contextProperty) {
        contextStorage.setContextProperty(contextProperty);
    }
}
