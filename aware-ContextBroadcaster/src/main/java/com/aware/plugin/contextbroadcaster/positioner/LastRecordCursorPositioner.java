package com.aware.plugin.contextbroadcaster.positioner;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 */
public class LastRecordCursorPositioner extends AbstractCursorPositioner {
    private int cursorPosition = CURSOR_START_POSITION;

    public LastRecordCursorPositioner(Uri contentUri, ContentResolver contentResolver) {
        super(contentUri, contentResolver);
    }

    @Override
    public Cursor moveToNext() {
        if (cursor.moveToLast() && cursor.getPosition() > cursorPosition) {
            cursorPosition = cursor.getPosition();
        } else {
            super.terminate();
        }
        return cursor;
    }
}
