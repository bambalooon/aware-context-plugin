package com.aware.plugin.contextbroadcaster.positioner;

import android.database.Cursor;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 */
public interface CursorPositioner {
    int CURSOR_START_POSITION = -1;
    /**
     * Method initializes positioner with new records
     */
    void initialize();

    /**
     * @return Cursor located at one of records or null if there is none anymore (cursor closed when null returned)
     */
    Cursor moveToNext();
}
