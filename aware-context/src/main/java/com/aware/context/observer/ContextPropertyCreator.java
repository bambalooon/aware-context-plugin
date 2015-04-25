package com.aware.context.observer;

import android.database.Cursor;
import android.net.Uri;
import com.aware.context.factory.ContextPropertyFactory;
import com.aware.context.property.ContextProperty;

/**
 * Name: ContextPropertyCreator
 * Description: ContextPropertyCreator
 * Date: 2015-02-21
 * Created by BamBalooon
 * @param <CP> created ContextProperty type
 */
public class ContextPropertyCreator<CP extends ContextProperty> {
    private final ContextPropertyMapping<CP> contextPropertyMapping;

    public ContextPropertyCreator(ContextPropertyMapping<CP> contextPropertyMapping) {
        this.contextPropertyMapping = contextPropertyMapping;
    }

    public CP createContextProperty(Uri uri, Cursor cursor) {
        ContextPropertyFactory<CP> contextPropertyFactory = contextPropertyMapping.getContextPropertyFactory(uri);
        return contextPropertyFactory.createInstance(cursor);
    }
}
