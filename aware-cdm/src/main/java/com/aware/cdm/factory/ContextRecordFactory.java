package com.aware.cdm.factory;

import android.database.Cursor;
import com.aware.cdm.record.ContextRecord;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 */
public interface ContextRecordFactory {
    ContextRecord createInstance(Cursor cursor);
}
