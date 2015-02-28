package com.aware.plugin.contextbroadcaster.positioner;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 */
public class NewRecordsCursorPositioner extends AbstractCursorPositioner {
    public static NewRecordsCursorPositioner createInstancePositionedAtStart(Uri contentUri, ContentResolver contentResolver) {
        return new NewRecordsCursorPositioner(contentUri, contentResolver);
    }

    public static NewRecordsCursorPositioner createInstancePositionedAtEnd(Uri contentUri, ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(contentUri, null, null, null, null);
        int lastRecordPosition = cursor.getCount() - 1;
        cursor.close();
        return new NewRecordsCursorPositioner(contentUri, contentResolver, lastRecordPosition);
    }

    private int cursorPosition;

    public NewRecordsCursorPositioner(Uri contentUri, ContentResolver contentResolver, int cursorPosition) {
        super(contentUri, contentResolver);
        this.cursorPosition = cursorPosition;
    }

    public NewRecordsCursorPositioner(Uri contentUri, ContentResolver contentResolver) {
        this(contentUri, contentResolver, CURSOR_START_POSITION);
    }

    @Override
    public void initialize() {
        super.initialize();
        cursor.moveToPosition(cursorPosition);
    }

    @Override
    public Cursor moveToNext() {
        if (cursor.moveToNext()) {
            cursorPosition = cursor.getPosition();
        } else {
            super.terminate();
        }
        return cursor;
    }
}
