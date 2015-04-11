package com.aware.context.provider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.storage.ContextStorage;
import com.aware.context.transform.ContextPropertySerialization;

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
        return contextPropertySerialization.CONTEXT_DESERIALIZER.apply(contextPropertyJson);
    }

    @Override
    public void setContextProperty(String contextPropertyId, GenericContextProperty contextProperty) {

    }

    @Override
    public Map<String, GenericContextProperty> getContextProperties() {
        return null;
    }
}
