package com.aware.context.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.storage.ContextStorage;
import com.aware.context.transform.ContextPropertySerialization;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Name: CurrentContext
 * Description: CurrentContext
 * Date: 2015-04-11
 * Created by BamBalooon
 */
public class Context implements ContextStorage<GenericContextProperty> {
    private final ContentResolver contentResolver;
    private final ContextPropertySerialization<GenericContextProperty> contextPropertySerialization;

    public Context(ContentResolver contentResolver,
                   ContextPropertySerialization<GenericContextProperty> contextPropertySerialization) {
        this.contentResolver = contentResolver;
        this.contextPropertySerialization = contextPropertySerialization;
    }

    @Override
    public GenericContextProperty getContextProperty(String contextPropertyId) {
        Cursor contextPropertyCursor = contentResolver.query(
                Uri.withAppendedPath(ContextContract.Properties.CONTENT_URI, contextPropertyId),
                null, null, null, null);
        if (contextPropertyCursor == null || !contextPropertyCursor.moveToFirst()) {
            return null;
        }
        int contextPropertyColumnIndex = contextPropertyCursor
                .getColumnIndex(ContextContract.Properties._CONTEXT_PROPERTY);
        String contextPropertyJson = contextPropertyCursor.getString(contextPropertyColumnIndex);
        return contextPropertySerialization.deserialize(contextPropertyJson);
    }

    @Override
    public void setContextProperty(String contextPropertyId, GenericContextProperty contextProperty) {
        Preconditions.checkArgument(contextPropertyId.equals(contextProperty.getId()),
                "Given ContextProperty ID must be equal to ContextProperty's ID.");
        String contextPropertyJson = contextPropertySerialization.serialize(contextProperty);
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContextContract.Properties._ID, contextPropertyId);
        contentValues.put(ContextContract.Properties._CONTEXT_PROPERTY, contextPropertyJson);
        contentResolver.insert(ContextContract.Properties.CONTENT_URI, contentValues);
    }

    @Override
    public Map<String, GenericContextProperty> getContextProperties() {
        Cursor contextPropertiesCursor = contentResolver
                .query(ContextContract.Properties.CONTENT_URI, null, null, null, null);
        if (contextPropertiesCursor == null || !contextPropertiesCursor.moveToFirst()) {
            return null;
        }
        int contextPropertyIdColumnIndex = contextPropertiesCursor.getColumnIndex(ContextContract.Properties._ID);
        int contextPropertyJsonColumnIndex = contextPropertiesCursor
                .getColumnIndex(ContextContract.Properties._CONTEXT_PROPERTY);
        Map<String, GenericContextProperty> contextProperties = Maps.newHashMap();
        do {
            String contextPropertyId = contextPropertiesCursor.getString(contextPropertyIdColumnIndex);
            String contextPropertyJson = contextPropertiesCursor.getString(contextPropertyJsonColumnIndex);
            GenericContextProperty contextProperty = contextPropertySerialization.deserialize(contextPropertyJson);
            contextProperties.put(contextPropertyId, contextProperty);
        } while (contextPropertiesCursor.moveToNext());
        return contextProperties;
    }
}
