package com.aware.cdm;

import android.database.Cursor;
import android.net.Uri;
import com.aware.cdm.factory.ContextPropertyFactory;
import com.aware.cdm.record.ContextProperty;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 */
public class ContextPropertyCreator {
    private final ContextMapping contextMapping = ContextMapping.getInstance();

    public ContextProperty createContextProperty(Uri uri, Cursor cursor) {
        ContextPropertyFactory contextPropertyFactory = contextMapping.getContextPropertyFactory(uri);
        return contextPropertyFactory.createInstance(cursor);
    }
}
