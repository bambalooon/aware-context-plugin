package com.aware.context.management;

import com.aware.context.GenericContext;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.storage.MemoryContextStorage;
import com.aware.context.storage.PersistenceContextStorage;

/**
 * Name: ContextManagement
 * Description: ContextManagement stores GenericContext in memory and SharedPreferences,
 *              it allows to retrieve Context and modify ContextProperties
 * Date: 2015-03-22
 * Created by BamBalooon
 */
public class ContextManagement {
    private MemoryContextStorage<GenericContext> memoryContextStorage;
    private PersistenceContextStorage<GenericContext> persistenceContextStorage;

    public ContextManagement(android.content.Context applicationContext) {
        this.memoryContextStorage = MemoryContextStorage.getDefaultInstance();
        this.persistenceContextStorage = PersistenceContextStorage.getDefaultInstance(applicationContext);
    }

    public GenericContext getContext() {
        GenericContext memoryContext = memoryContextStorage.getContext();
        if (memoryContext == null) {
            return persistenceContextStorage.getContext();
        }
        return memoryContext;
    }

    public void setContextProperty(GenericContextProperty contextProperty) {
        GenericContext context = getContext();
        if (context == null) {
            context = new GenericContext();
        }
        context.setContextProperty(contextProperty);
        memoryContextStorage.setContext(context);
        persistenceContextStorage.setContext(context);
    }
}
