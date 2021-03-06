package com.aware.context.positioner;

import android.database.Cursor;
import android.net.Uri;
import android.test.mock.MockContentResolver;
import android.test.ExceptionCatcher;
import android.test.MockContentProvider;
import junit.framework.TestCase;

public class NewRecordsCursorPositionerTest extends TestCase {
    private MockContentProvider mockContentProvider = new MockContentProvider(new String[]{"col1", "col2"});
    private MockContentResolver mockContentResolver = new MockContentResolver();
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

    public void testGettingNoNewRecordsWhenEmptyCursorWithCursorPositionerAtStart() {
        //given
        Object[][] noCursorData = new Object[][] {};
        mockContentProvider.setCursorData(noCursorData);

        //when
        cursorPositionerAtStart.initialize();
        Cursor positionedCursor = cursorPositionerAtStart.moveToNext();

        //then
        assertNull(positionedCursor);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
    }

    public void testGettingNoNewRecordsWhenEmptyCursorWithCursorPositionerAtEnd() {
        //given
        Object[][] noCursorData = new Object[][] {};
        mockContentProvider.setCursorData(noCursorData);

        //when
        cursorPositionerAtEnd.initialize();
        Cursor positionedCursor = cursorPositionerAtEnd.moveToNext();

        //then
        assertNull(positionedCursor);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
    }

    public void testGettingNoNewRecordsWhenNullCursorWithCursorPositionerAtStart() {
        //given
        mockContentProvider.enableReturnNullCursor();

        //when
        cursorPositionerAtStart.initialize();
        Cursor positionedCursor = cursorPositionerAtStart.moveToNext();

        //then
        assertNull(positionedCursor);
        assertNull(mockContentProvider.getLastQueriedCursor());
    }

    public void testGettingNoNewRecordsWhenNullCursorWithCursorPositionerAtEnd() {
        //given
        mockContentProvider.enableReturnNullCursor();

        //when
        cursorPositionerAtEnd.initialize();
        Cursor positionedCursor = cursorPositionerAtEnd.moveToNext();

        //then
        assertNull(positionedCursor);
        assertNull(mockContentProvider.getLastQueriedCursor());
    }

    public void testGettingNewRecordsWhenCursorPositionerAtStartWasNotInitialized() {
        //when
        Exception caughtException = new ExceptionCatcher() {
            @Override
            protected void invoke() throws Exception {
                cursorPositionerAtStart.moveToNext();
            }
        }.catchException();

        //then
        assertTrue(caughtException instanceof IllegalStateException);
        assertEquals(caughtException.getMessage(),
                "CursorPositioner's method moveToNext invoked without initialization.");
    }

    public void testGettingNewRecordsWhenCursorPositionerAtEndWasNotInitialized() {
        //when
        Exception caughtException = new ExceptionCatcher() {
            @Override
            protected void invoke() throws Exception {
                cursorPositionerAtEnd.moveToNext();
            }
        }.catchException();

        //then
        assertTrue(caughtException instanceof IllegalStateException);
        assertEquals(caughtException.getMessage(),
                "CursorPositioner's method moveToNext invoked without initialization.");
    }

    public void testGettingNewRecordsWhenCursorPositionerAtEndCreatedFromNullCursor() {
        //given
        mockContentProvider.enableReturnNullCursor();
        CursorPositioner cursorPositionerAtEndFromNullCursor = NewRecordsCursorPositioner
                .createInstancePositionedAtEnd(
                        Uri.parse("content://" + MockContentProvider.AUTHORITY),
                        mockContentResolver);
        mockContentProvider.disableReturnNullCursor();

        //when
        cursorPositionerAtEndFromNullCursor.initialize();

        //then cursorPositionerAtEnd acts as cursorPositionerAtStart
        int rowIndex = 0;
        Cursor positionedCursor;
        while ((positionedCursor = cursorPositionerAtEndFromNullCursor.moveToNext()) != null) {
            assertFalse(positionedCursor.isClosed());
            assertEquals(positionedCursor.getString(0), cursorData[rowIndex][0]);
            assertEquals(positionedCursor.getInt(1), cursorData[rowIndex][1]);
            rowIndex++;
        }
        assertEquals(rowIndex, cursorData.length);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
    }

    public void testGettingNewRecordsWhenCursorPositionerAtEndCreatedFromEmptyCursor() {
        //given
        Object[][] noCursorData = new Object[][] {};
        mockContentProvider.setCursorData(noCursorData);
        CursorPositioner cursorPositionerAtEndFromNullCursor = NewRecordsCursorPositioner
                .createInstancePositionedAtEnd(
                        Uri.parse("content://" + MockContentProvider.AUTHORITY),
                        mockContentResolver);
        mockContentProvider.setCursorData(cursorData);

        //when
        cursorPositionerAtEndFromNullCursor.initialize();

        //then cursorPositionerAtEnd acts as cursorPositionerAtStart
        int rowIndex = 0;
        Cursor positionedCursor;
        while ((positionedCursor = cursorPositionerAtEndFromNullCursor.moveToNext()) != null) {
            assertFalse(positionedCursor.isClosed());
            assertEquals(positionedCursor.getString(0), cursorData[rowIndex][0]);
            assertEquals(positionedCursor.getInt(1), cursorData[rowIndex][1]);
            rowIndex++;
        }
        assertEquals(rowIndex, cursorData.length);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
    }

    //FIXME: should it behave like that?
    public void testGettingNewRecordsWhenCursorIsClosedWithCursorPositionerAtStart() {
        //when
        cursorPositionerAtStart.initialize();
        mockContentProvider.getLastQueriedCursor().close();

        //then
        int rowIndex = 0;
        Cursor positionedCursor;
        while ((positionedCursor = cursorPositionerAtStart.moveToNext()) != null) {
            assertTrue(positionedCursor.isClosed());
            assertEquals(positionedCursor.getString(0), cursorData[rowIndex][0]);
            assertEquals(positionedCursor.getInt(1), cursorData[rowIndex][1]);
            rowIndex++;
        }
        assertEquals(rowIndex, cursorData.length);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
    }

    //FIXME: should it behave like that?
    public void testGettingNewRecordsAfterCursorDataUpdateWhenCursorIsClosedWithCursorPositionerAtEnd() {
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
        mockContentProvider.getLastQueriedCursor().close();

        //then
        int rowIndex = 0;
        Cursor positionedCursor;
        while ((positionedCursor = cursorPositionerAtEnd.moveToNext()) != null) {
            assertTrue(positionedCursor.isClosed());
            assertEquals(positionedCursor.getString(0), updatedCursorData[oldRowsCount + rowIndex][0]);
            assertEquals(positionedCursor.getInt(1), updatedCursorData[oldRowsCount + rowIndex][1]);
            rowIndex++;
        }
        assertEquals(rowIndex, updatedCursorData.length - oldRowsCount);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
    }
}
