package com.aware.context.positioner;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.test.mock.MockContentResolver;
import android.test.ExceptionCatcher;
import android.test.MockContentProvider;
import junit.framework.TestCase;

public class LastRecordCursorPositionerTest extends TestCase {
    private MockContentProvider mockContentProvider = new MockContentProvider(new String[]{"col1", "col2"});
    private MockContentResolver mockContentResolver = new MockContentResolver();
    private CursorPositioner cursorPositioner;

    private Object[][] cursorData = {
            new Object[] {"firstRow", 1},
            new Object[] {"secondRow", 2},
            new Object[] {"thirdRow", 3}
    };

    @Override
    public void setUp() {
        mockContentProvider.setCursorData(cursorData);
        mockContentResolver.addProvider(MockContentProvider.AUTHORITY, mockContentProvider);

        cursorPositioner = new LastRecordCursorPositioner(Uri
                .parse("content://" + MockContentProvider.AUTHORITY), mockContentResolver);
    }

    public void testGettingLastRecordFromCursor() {
        //when
        cursorPositioner.initialize();
        Cursor positionedCursor = cursorPositioner.moveToNext();

        //then
        assertFalse(mockContentProvider.getLastQueriedCursor().isClosed());
        assertFalse(positionedCursor.isClosed());
        assertEquals(positionedCursor.getString(0), "thirdRow");
        assertEquals(positionedCursor.getInt(1), 3);

        //when trying to move cursor second time (without cursor change)
        Cursor cursorMovedSecondTime = cursorPositioner.moveToNext();

        //then
        assertNull(cursorMovedSecondTime);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
        assertTrue(positionedCursor.isClosed());
    }

    public void testGettingLastRecordFromCursorWithoutInitialization() {
        //when
        Exception caughtException = new ExceptionCatcher() {
            @Override
            protected void invoke() throws Exception {
                cursorPositioner.moveToNext();
            }
        }.catchException();

        //then
        assertTrue(caughtException instanceof IllegalStateException);
        assertEquals(caughtException.getMessage(),
                "CursorPositioner's method moveToNext invoked without initialization.");
    }

    public void testGettingLastRecordFromEmptyCursor() {
        //given
        Object[][] noCursorData = new Object[][] {};
        mockContentProvider.setCursorData(noCursorData);

        //when
        cursorPositioner.initialize();
        Cursor positionedCursor = cursorPositioner.moveToNext();

        //then
        assertNull(positionedCursor);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
    }

    public void testGettingLastRecordAgainWithoutCursorUpdate() {
        //when moved to last record and closed cursor
        cursorPositioner.initialize();
        cursorPositioner.moveToNext();
        cursorPositioner.moveToNext();

        //when data in db didn't change - same row count
        cursorPositioner.initialize();
        Cursor positionedCursor = cursorPositioner.moveToNext();

        //then
        assertNull(positionedCursor);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
    }

    public void testGettingLastRecordAgainAfterCursorUpdate() {
        //when firstCursor is moved to last position and closed
        cursorPositioner.initialize();
        cursorPositioner.moveToNext();
        cursorPositioner.moveToNext();
        Cursor firstCursor = mockContentProvider.getLastQueriedCursor();

        //given 2 additional rows where added to db
        Object[][] newCursorData = new Object[][] {
                {"firstRow", 1},
                {"secondRow", 2},
                {"thirdRow", 3},
                {"newRow", 4},
                {"lastNewRow", 5}
        };
        mockContentProvider.setCursorData(newCursorData);

        //when cursor is initialized and traversed for 2nd time
        cursorPositioner.initialize();
        Cursor positionedCursor = cursorPositioner.moveToNext();

        //then
        assertTrue(firstCursor.isClosed());
        assertFalse(mockContentProvider.getLastQueriedCursor().isClosed());
        assertFalse(positionedCursor.isClosed());
        assertEquals(positionedCursor.getString(0), "lastNewRow");
        assertEquals(positionedCursor.getInt(1), 5);

        //when trying to move cursor 2nd time (without cursor change)
        Cursor cursorMovedSecondTime = cursorPositioner.moveToNext();

        //then
        assertNull(cursorMovedSecondTime);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
        assertTrue(positionedCursor.isClosed());
    }

    public void testGettingLastRecordAgainWhenDataUpdatedBetweenMoveToNextCalls() {
        //when firstCursor is moved to last position
        cursorPositioner.initialize();
        cursorPositioner.moveToNext();
        MatrixCursor firstCursor = mockContentProvider.getLastQueriedCursor();

        //given 2 additional rows where added to cursor
        firstCursor.addRow(new Object[]{"newRow", 4});
        firstCursor.addRow(new Object[]{"lastNewRow", 5});

        //when cursor is traversed for 2nd time
        Cursor positionedCursor = cursorPositioner.moveToNext();

        //then
        assertSame(firstCursor, mockContentProvider.getLastQueriedCursor());
        assertFalse(firstCursor.isClosed());
        assertFalse(positionedCursor.isClosed());
        assertEquals(positionedCursor.getString(0), "lastNewRow");
        assertEquals(positionedCursor.getInt(1), 5);

        //when trying to move cursor 2nd time (without cursor change)
        Cursor cursorMovedSecondTime = cursorPositioner.moveToNext();

        //then
        assertNull(cursorMovedSecondTime);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
        assertTrue(positionedCursor.isClosed());
    }

    //FIXME: should it behave like that?
    public void testGettingLastRecordFromClosedCursor() {
        //given
        cursorPositioner.initialize();
        mockContentProvider.getLastQueriedCursor().close();

        //when
        Cursor positionedCursor = cursorPositioner.moveToNext();

        //then
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
        assertTrue(positionedCursor.isClosed());
        assertEquals(positionedCursor.getString(0), "thirdRow");
        assertEquals(positionedCursor.getInt(1), 3);

        //when trying to move cursor second time (without cursor change)
        Cursor cursorMovedSecondTime = cursorPositioner.moveToNext();

        //then
        assertNull(cursorMovedSecondTime);
        assertTrue(mockContentProvider.getLastQueriedCursor().isClosed());
        assertTrue(positionedCursor.isClosed());
    }
}
