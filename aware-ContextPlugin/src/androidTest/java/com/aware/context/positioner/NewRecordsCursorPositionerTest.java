package com.aware.context.positioner;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.test.mock.MockContentResolver;
import junit.framework.TestCase;

public class NewRecordsCursorPositionerTest extends TestCase {
    private MockContentProvider mockContentProvider = new MockContentProvider(new String[]{"col1", "col2"});
    private CursorPositioner cursorPositionerAtStart;
    private CursorPositioner cursorPositionerAtEnd;

    private Object[][] cursorData = {
            new Object[] {"firstRow", 1},
            new Object[] {"secondRow", 2},
            new Object[] {"thirdRow", 3}
    };

    @Override
    public void setUp() {
        mockContentProvider.setCursorData(cursorData);

        MockContentResolver mockContentResolver = new MockContentResolver();
        mockContentResolver.addProvider(MockContentProvider.AUTHORITY, mockContentProvider);

        cursorPositionerAtStart = NewRecordsCursorPositioner.createInstancePositionedAtStart(Uri
                .parse("content://" + MockContentProvider.AUTHORITY), mockContentResolver);
        cursorPositionerAtEnd = NewRecordsCursorPositioner.createInstancePositionedAtEnd(Uri
                .parse("content://" + MockContentProvider.AUTHORITY), mockContentResolver);
    }

    public void testGettingAllNewRecordsFromCursorPositionedAtStart() {
        //when
        cursorPositionerAtStart.initialize();

        //then
        int rowIndex = 0;
        Cursor positionedCursor;
        while ((positionedCursor = cursorPositionerAtStart.moveToNext()) != null) {
            assertFalse(positionedCursor.isClosed());
            assertEquals(positionedCursor.getString(0), cursorData[rowIndex][0]);
            assertEquals(positionedCursor.getInt(1), cursorData[rowIndex][1]);
            rowIndex++;
        }
        assertEquals(rowIndex, cursorData.length);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
    }

    public void testGettingNoNewRecordsFromCursorPositionedAtEnd() {
        //when
        cursorPositionerAtEnd.initialize();
        Cursor positionedCursor = cursorPositionerAtEnd.moveToNext();

        //then
        assertNull(positionedCursor);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
    }

    public void testGettingNewRecordsAfterCursorDataUpdateFromCursorPositionedAtStart() {
        //given
        testGettingAllNewRecordsFromCursorPositionedAtStart();
        int oldRowsCount = cursorData.length;
        Object[][] updatedCursorData = new Object[][] {
                new Object[] {"firstRow", 1},
                new Object[] {"secondRow", 2},
                new Object[] {"thirdRow", 3},
                new Object[] {"fourthRow", 4},
                new Object[] {"fifthRow", 5}
        };
        mockContentProvider.setCursorData(updatedCursorData);

        //when
        cursorPositionerAtStart.initialize();

        //then
        int rowIndex = 0;
        Cursor positionedCursor;
        while ((positionedCursor = cursorPositionerAtStart.moveToNext()) != null) {
            assertFalse(positionedCursor.isClosed());
            assertEquals(positionedCursor.getString(0), updatedCursorData[oldRowsCount + rowIndex][0]);
            assertEquals(positionedCursor.getInt(1), updatedCursorData[oldRowsCount + rowIndex][1]);
            rowIndex++;
        }
        assertEquals(rowIndex, updatedCursorData.length - oldRowsCount);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
    }

    public void testGettingNewRecordsAfterCursorDataUpdateFromCursorPositionedAtEnd() {
        //given
        int oldRowsCount = cursorData.length;
        Object[][] updatedCursorData = new Object[][] {
                new Object[] {"firstRow", 1},
                new Object[] {"secondRow", 2},
                new Object[] {"thirdRow", 3},
                new Object[] {"fourthRow", 4},
                new Object[] {"fifthRow", 5}
        };
        mockContentProvider.setCursorData(updatedCursorData);

        //when
        cursorPositionerAtEnd.initialize();

        //then
        int rowIndex = 0;
        Cursor positionedCursor;
        while ((positionedCursor = cursorPositionerAtEnd.moveToNext()) != null) {
            assertFalse(positionedCursor.isClosed());
            assertEquals(positionedCursor.getString(0), updatedCursorData[oldRowsCount + rowIndex][0]);
            assertEquals(positionedCursor.getInt(1), updatedCursorData[oldRowsCount + rowIndex][1]);
            rowIndex++;
        }
        assertEquals(rowIndex, updatedCursorData.length - oldRowsCount);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
    }

    private class MockContentProvider extends android.test.mock.MockContentProvider {
        private static final String AUTHORITY = "authority";
        private final String[] cursorColumns;
        private Object[][] cursorData;
        private MatrixCursor lastQueriedCursor;

        public MockContentProvider(String[] cursorColumns) {
            this.cursorColumns = cursorColumns;
        }

        public void setCursorData(Object[][] cursorData) {
            this.cursorData = cursorData;
        }

        public Cursor getLastQueriedCursor() {
            return lastQueriedCursor;
        }

        @Override
        public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
            lastQueriedCursor = new MatrixCursor(cursorColumns, cursorData.length);
            for (Object[] cursorRow : cursorData) {
                lastQueriedCursor.addRow(cursorRow);
            }
            return lastQueriedCursor;
        }
    }
}
