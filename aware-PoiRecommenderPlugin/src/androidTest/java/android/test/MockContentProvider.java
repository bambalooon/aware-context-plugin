package android.test;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

/**
 * Name: MockContentProvider
 * Description: MockContentProvider
 * Date: 2015-04-14
 * Created by BamBalooon
 */
public class MockContentProvider extends android.test.mock.MockContentProvider {
    public static final String AUTHORITY = "authority";
    private final String[] cursorColumns;
    private Object[][] cursorData;
    private MatrixCursor lastQueriedCursor;
    private boolean returnNullCursor = false;

    public MockContentProvider(String[] cursorColumns) {
        this.cursorColumns = cursorColumns;
    }

    public void setCursorData(Object[][] cursorData) {
        this.cursorData = cursorData;
    }

    public MatrixCursor getLastQueriedCursor() {
        return lastQueriedCursor;
    }

    public void enableReturnNullCursor() {
        this.returnNullCursor = true;
    }

    public void disableReturnNullCursor() {
        this.returnNullCursor = false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (returnNullCursor) {
            lastQueriedCursor = null;
            return null;
        }
        lastQueriedCursor = new MatrixCursor(cursorColumns, cursorData.length);
        for (Object[] cursorRow : cursorData) {
            lastQueriedCursor.addRow(cursorRow);
        }
        return lastQueriedCursor;
    }
}
