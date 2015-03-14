package com.aware.context.factory;

import android.database.Cursor;
import com.aware.context.property.GenericContextProperty;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by BamBalooon on 2015-03-10.
 */
public class GenericContextPropertyFactory implements ContextPropertyFactory<GenericContextProperty> {
    private final CellValueRetriever cellValueRetriever;
    private final String propertyName;
    private final Map<String, Class<?>> propertyColumns;

    public GenericContextPropertyFactory(String propertyName, Map<String, Class<?>> propertyColumns) {
        this.cellValueRetriever = new CellValueRetriever();
        this.propertyName = propertyName;
        this.propertyColumns = propertyColumns;
    }

    @Override
    public GenericContextProperty createInstance(Cursor cursor) {
        ImmutableMap.Builder<String, Object> propertyValuesBuilder = ImmutableMap.builder();
        for (Map.Entry<String, Class<?>> propertyColumn : propertyColumns.entrySet()) {
            String columnName = propertyColumn.getKey();
            int columnIndex = cursor.getColumnIndex(columnName);
            Object columnValue = cellValueRetriever.retrieveValue(cursor, columnIndex, propertyColumn.getValue());
            propertyValuesBuilder.put(columnName, columnValue);
        }

        return new GenericContextProperty(propertyName, propertyValuesBuilder.build());
    }

    private class CellValueRetriever {
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
}
