package com.aware.context.positioner;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 */
public class NewRecordsCursorPositioner extends AbstractCursorPositioner {
    //FIXME: after restart of positioner it will be positioned at start, it might be bad idea to offer this method
    public static NewRecordsCursorPositioner createInstancePositionedAtStart(Uri contentUri, ContentResolver contentResolver) {
        return new NewRecordsCursorPositioner(contentUri, contentResolver);
    }

    public static NewRecordsCursorPositioner createInstancePositionedAtEnd(Uri contentUri, ContentResolver contentResolver) {
        Cursor cursor = contentResolver.query(contentUri, null, null, null, null);
        if (cursor == null) {
            return createInstancePositionedAtStart(contentUri, contentResolver);
        }
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
        if (cursor != null) {
            cursor.moveToPosition(cursorPosition);
        }
    }

    @Override
    protected boolean moveCursorToNextItem() {
        if (cursor.moveToNext()) {
            cursorPosition = cursor.getPosition();
            return true;
        }
        return false;
    }
}
