package com.aware.cdm.factory;

import android.database.Cursor;
import com.aware.cdm.record.ContextProperty;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 */
public interface ContextPropertyFactory {
    ContextProperty createInstance(Cursor cursor);
}
