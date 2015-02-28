package com.aware.cdm;

import android.database.Cursor;
import android.net.Uri;
import com.aware.cdm.factory.ContextRecordFactory;
import com.aware.cdm.factory.WifiSensorRecordFactory;
import com.aware.cdm.record.ContextRecord;
import com.aware.providers.WiFi_Provider;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 */
public class ContextRecordCreator {
    private final ContextMapping contextMapping = ContextMapping.getInstance();

    public ContextRecord createContextRecord(Uri uri, Cursor cursor) {
        ContextRecordFactory contextRecordFactory = contextMapping.getContextRecordFactory(uri);
        return contextRecordFactory.createInstance(cursor);
    }
}
