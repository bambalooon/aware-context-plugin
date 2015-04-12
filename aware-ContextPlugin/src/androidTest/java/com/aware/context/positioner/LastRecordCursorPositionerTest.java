package com.aware.context.positioner;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.test.mock.MockContentResolver;
import junit.framework.TestCase;

public class LastRecordCursorPositionerTest extends TestCase {
    private MockContentProvider mockContentProvider = new MockContentProvider();
    private CursorPositioner cursorPositioner;
    private MatrixCursor providedCursor;

    @Override
    public void setUp() {
        MockContentResolver mockContentResolver = new MockContentResolver();
        mockContentResolver.addProvider(MockContentProvider.AUTHORITY, mockContentProvider);
        cursorPositioner = new LastRecordCursorPositioner(Uri
                .parse("content://" + MockContentProvider.AUTHORITY), mockContentResolver);

        //setup initial providedCursor returned by provider
        providedCursor = new MatrixCursor(new String[]{"col1", "col2"}, 3);
        providedCursor.addRow(new Object[]{"firstRow", 1});
        providedCursor.addRow(new Object[]{"secondRow", 2});
        providedCursor.addRow(new Object[]{"thirdRow", 3});
        mockContentProvider.willReturn(providedCursor);
    }

    public void testGettingLastRecordFromCursor() {
        //when
        cursorPositioner.initialize();
        Cursor positionedCursor = cursorPositioner.moveToNext();

        //then
        assertFalse(providedCursor.isClosed());
        assertFalse(positionedCursor.isClosed());
        assertEquals(positionedCursor.getString(0), "thirdRow");
        assertEquals(positionedCursor.getInt(1), 3);

        //when trying to move cursor second time (without cursor change)
        Cursor cursorMovedSecondTime = cursorPositioner.moveToNext();

        //then
        assertNull(cursorMovedSecondTime);
        assertTrue(providedCursor.isClosed());
        assertTrue(positionedCursor.isClosed());
    }

    public void testGettingLastRecordFromCursorWithoutInitialization() {
        //when
        Cursor positionedCursor = cursorPositioner.moveToNext();

        //then
        assertNull(positionedCursor);
        assertFalse(providedCursor.isClosed());
    }

    public void testGettingLastRecordFromEmptyCursor() {
        //given
        MatrixCursor cursor = new MatrixCursor(new String[]{"col1", "col2"}, 0);
        mockContentProvider.willReturn(cursor);

        //when
        cursorPositioner.initialize();
        Cursor positionedCursor = cursorPositioner.moveToNext();

        //then
        assertNull(positionedCursor);
        assertTrue(cursor.isClosed());
    }

    public void testGettingLastRecordAgainWithoutCursorUpdate() {
        //when moved to last record and closed cursor
        cursorPositioner.initialize();
        cursorPositioner.moveToNext();
        cursorPositioner.moveToNext();

        //given created equal cursor with same row count
        providedCursor = new MatrixCursor(new String[]{"col1", "col2"}, 3);
        providedCursor.addRow(new Object[]{"firstRow", 1});
        providedCursor.addRow(new Object[]{"secondRow", 2});
        providedCursor.addRow(new Object[]{"thirdRow", 3});
        mockContentProvider.willReturn(providedCursor);

        //when
        cursorPositioner.initialize();
        Cursor positionedCursor = cursorPositioner.moveToNext();

        //then
        assertNull(positionedCursor);
        assertTrue(providedCursor.isClosed());
    }

    public void testGettingLastRecordAgainAfterCursorUpdate() {
        //when providedCursor is moved to last position and closed
        cursorPositioner.initialize();
        cursorPositioner.moveToNext();
        cursorPositioner.moveToNext();

        //given provided cursor was updated to newCursor with additional row
        MatrixCursor newCursor = new MatrixCursor(new String[]{"col1", "col2"}, 4);
        newCursor.addRow(new Object[]{"firstRow", 1});
        newCursor.addRow(new Object[]{"secondRow", 2});
        newCursor.addRow(new Object[]{"thirdRow", 3});
        newCursor.addRow(new Object[]{"newRow", 4});
        mockContentProvider.willReturn(newCursor);

        //when cursor is initialized and traversed for 2nd time
        cursorPositioner.initialize();
        Cursor positionedCursor = cursorPositioner.moveToNext();

        //then
        assertTrue(providedCursor.isClosed());
        assertFalse(newCursor.isClosed());
        assertFalse(positionedCursor.isClosed());
        assertEquals(positionedCursor.getString(0), "newRow");
        assertEquals(positionedCursor.getInt(1), 4);

        //when trying to move cursor 2nd time (without cursor change)
        Cursor cursorMovedSecondTime = cursorPositioner.moveToNext();

        //then
        assertNull(cursorMovedSecondTime);
        assertTrue(newCursor.isClosed());
        assertTrue(positionedCursor.isClosed());
    }

    //FIXME: should it behave like that?
    public void testGettingLastRecordFromClosedCursor() {
        //given
        providedCursor.close();

        //when
        cursorPositioner.initialize();
        Cursor positionedCursor = cursorPositioner.moveToNext();

        //then
        assertTrue(providedCursor.isClosed());
        assertTrue(positionedCursor.isClosed());
        assertEquals(positionedCursor.getString(0), "thirdRow");
        assertEquals(positionedCursor.getInt(1), 3);

        //when trying to move cursor second time (without cursor change)
        Cursor cursorMovedSecondTime = cursorPositioner.moveToNext();

        //then
        assertNull(cursorMovedSecondTime);
        assertTrue(providedCursor.isClosed());
        assertTrue(positionedCursor.isClosed());
    }

    private class MockContentProvider extends android.test.mock.MockContentProvider {
        private static final String AUTHORITY = "authority";
        private Cursor returnedCursor;

        public void willReturn(Cursor cursor) {
            this.returnedCursor = cursor;
        }

        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            return returnedCursor;
        }
    }
}
