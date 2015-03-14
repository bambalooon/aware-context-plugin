package com.aware.context.observer;

import android.database.Cursor;
import android.net.Uri;
import com.aware.context.factory.ContextPropertyFactory;
import com.aware.context.property.ContextProperty;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 * @param <CP> created ContextProperty type
 */
public class ContextPropertyCreator<CP extends ContextProperty> {
    private static ContextPropertyCreator<ContextProperty> DEFAULT_INSTANCE;
    public static ContextPropertyCreator<ContextProperty> getDefaultInstance() {
        if (DEFAULT_INSTANCE == null) {
            DEFAULT_INSTANCE = new ContextPropertyCreator<>(ContextPropertyMapping.getDefaultInstance());
        }
        return DEFAULT_INSTANCE;
    }
    private final ContextPropertyMapping<CP> contextPropertyMapping;

    public ContextPropertyCreator(ContextPropertyMapping<CP> contextPropertyMapping) {
        this.contextPropertyMapping = contextPropertyMapping;
    }

    public CP createContextProperty(Uri uri, Cursor cursor) {
        ContextPropertyFactory<CP> contextPropertyFactory = contextPropertyMapping.getContextPropertyFactory(uri);
        return contextPropertyFactory.createInstance(cursor);
    }
}
