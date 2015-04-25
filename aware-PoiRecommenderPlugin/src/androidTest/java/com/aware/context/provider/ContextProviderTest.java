package com.aware.context.provider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.Suppress;

//TODO: change to work on mocks and with ProviderTestCase2
@Suppress
public class ContextProviderTest extends AndroidTestCase {
    private ContentResolver contentResolver;

    @Override
    public void setUp() {
        contentResolver = getContext().getContentResolver();
    }

    public void testQueryContextProperty() {
        //when
        Cursor cursor = contentResolver.query(
                Uri.withAppendedPath(
                        ContextContract.Properties.CONTENT_URI,
                        "com.aware.plugin.google.activity_recognition.provider"
                ), null, null, null, null);

        //then
        assertNotNull(cursor);
        assertEquals(cursor.getColumnNames(), ContextContract.Properties.PROJECTION_ALL);
    }

    public void testNotExistingContextProperty() throws Exception {
        //when
        Cursor cursor = contentResolver.query(
                Uri.withAppendedPath(
                        ContextContract.Properties.CONTENT_URI,
                        "not_existing"
                ), null, null, null, null);

        //then
        assertNull(cursor);
    }

    public void testQueryContext() {
        //when
        Cursor cursor = contentResolver.query(ContextContract.Properties.CONTENT_URI, null, null, null, null);

        //then
        assertNotNull(cursor);
        assertEquals(cursor.getCount(), 3);
    }
}