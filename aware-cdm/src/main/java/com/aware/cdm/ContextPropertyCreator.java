package com.aware.cdm;

import android.database.Cursor;
import android.net.Uri;
import com.aware.cdm.factory.ContextPropertyFactory;
import com.aware.cdm.property.ContextProperty;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 * @param <CP> created ContextProperty type
 */
public class ContextPropertyCreator<CP extends ContextProperty> {
    private final ContextMapping<CP> contextMapping;

    public ContextPropertyCreator(ContextMapping<CP> contextMapping) {
        this.contextMapping = contextMapping;
    }

    public CP createContextProperty(Uri uri, Cursor cursor) {
        ContextPropertyFactory<CP> contextPropertyFactory = contextMapping.getContextPropertyFactory(uri);
        return contextPropertyFactory.createInstance(cursor);
    }
}
