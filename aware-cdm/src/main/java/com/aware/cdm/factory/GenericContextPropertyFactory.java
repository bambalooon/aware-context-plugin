package com.aware.cdm.factory;

import android.database.Cursor;
import com.aware.cdm.property.ContextProperty;
import com.aware.cdm.property.GenericContextProperty;
import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by BamBalooon on 2015-03-10.
 */
public class GenericContextPropertyFactory implements ContextPropertyFactory<ContextProperty> {
    private final CellValueRetriever cellValueRetriever;
    private final String propertyName;
    private final Map<String, Class<?>> propertyColumns;

    public GenericContextPropertyFactory(CellValueRetriever cellValueRetriever, String propertyName, Map<String, Class<?>> propertyColumns) {
        this.cellValueRetriever = cellValueRetriever;
        this.propertyName = propertyName;
        this.propertyColumns = propertyColumns;
    }

    @Override
    public ContextProperty createInstance(Cursor cursor) {
        ImmutableMap.Builder<String, Object> propertyValuesBuilder = ImmutableMap.builder();
        for (Map.Entry<String, Class<?>> propertyColumn : propertyColumns.entrySet()) {
            String columnName = propertyColumn.getKey();
            int columnIndex = cursor.getColumnIndex(columnName);
            Object columnValue = cellValueRetriever.retrieveValue(cursor, columnIndex, propertyColumn.getValue());
            propertyValuesBuilder.put(columnName, columnValue);
        }

        return new GenericContextProperty(propertyName, propertyValuesBuilder.build());
    }
}
