package com.aware.context.observer.positioner;

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

    /**
     * Closes cursor and assigns null to it
     */
    protected void terminate() {
        cursor.close();
        cursor = null;
    }
}
