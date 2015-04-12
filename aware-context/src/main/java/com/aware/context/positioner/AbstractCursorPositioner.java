package com.aware.context.positioner;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 */
public abstract class AbstractCursorPositioner implements CursorPositioner {
    private final Uri contentUri;
    private final ContentResolver contentResolver;
    protected Cursor cursor;

    protected AbstractCursorPositioner(Uri contentUri, ContentResolver contentResolver) {
        this.contentUri = contentUri;
        this.contentResolver = contentResolver;
    }

    /**
     * Method initializes positioner with new records
     */
    public void initialize() {
        cursor = contentResolver.query(contentUri, null, null, null, null);
    }

    //FIXME: add checking if CursorPositioner was initialized before usage
    @Override
    public final Cursor moveToNext() {
        if (cursor == null || moveCursorToNextItem()) {
            return cursor;
        }
        cursor.close();
        return null;
    }

    /**
     * Moves cursor to next item
     * @return boolean value representing if cursor was moved or not
     */
    protected abstract boolean moveCursorToNextItem();
}
