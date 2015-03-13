package com.aware.context.factory;

import android.database.Cursor;

/**
 * Created by BamBalooon on 2015-03-10.
 */
public class CellValueRetriever {
    public Object retrieveValue(Cursor cursor, int cellIndex, Class<?> cellType) {
        switch (cellType.getSimpleName()) {
            case "Integer":
                return cursor.getInt(cellIndex);
            case "Long":
                return cursor.getLong(cellIndex);
            case "Double":
                return cursor.getDouble(cellIndex);
            case "String":
                return cursor.getString(cellIndex);
            default:
                throw new IllegalArgumentException("Could not retrieve data of type: " + cellType.getSimpleName());
        }

    }
}
