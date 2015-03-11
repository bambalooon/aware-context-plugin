package com.aware.cdm.factory;

import android.database.Cursor;
import com.aware.cdm.property.ContextProperty;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 * @param <CP> returned ContextProperty type
 */
public interface ContextPropertyFactory<CP extends ContextProperty> {
    CP createInstance(Cursor cursor);
}
