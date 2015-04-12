package com.aware.context.positioner;

import android.content.ContentResolver;
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
    protected boolean moveCursorToNextItem() {
        if (cursor.moveToLast() && cursor.getPosition() > cursorPosition) {
            cursorPosition = cursor.getPosition();
            return true;
        }
        return false;
    }
}
