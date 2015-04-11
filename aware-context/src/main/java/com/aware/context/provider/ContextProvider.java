package com.aware.context.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.storage.ContextStorage;
import com.aware.context.storage.PersistenceContextStorage;
import com.aware.context.transform.ContextPropertySerialization;

import java.util.Collection;

/**
 * Name: CurrentContextProvider
 * Description: CurrentContextProvider
 * Date: 2015-03-28
 * Created by BamBalooon
 */
public class ContextProvider extends ContentProvider {
    private static final int CONTEXT = 1;
    private static final int CONTEXT_PROPERTY = 2;
    private static final UriMatcher URI_MATCHER;

    private ContextStorage<GenericContextProperty> contextStorage;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(ContextContract.AUTHORITY, "properties", CONTEXT);
        URI_MATCHER.addURI(ContextContract.AUTHORITY, "properties/*", CONTEXT_PROPERTY);
    }

    @Override
    public boolean onCreate() {
        this.contextStorage = new PersistenceContextStorage<>(
                getContext().getSharedPreferences(PersistenceContextStorage.CONTEXT_PREFERENCES, Context.MODE_PRIVATE),
                new ContextPropertySerialization<>(GenericContextProperty.class)
        );
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case CONTEXT:
                return ContextContract.Properties.CONTENT_TYPE;
            case CONTEXT_PROPERTY:
                return ContextContract.Properties.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown or Invalid URI " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if (projection == null) {
            projection = ContextContract.Properties.PROJECTION_ALL;
        }

        final MatrixCursor cursor;
        switch (URI_MATCHER.match(uri)) {
            case CONTEXT:
                Collection<GenericContextProperty> contextProperties = contextStorage.getContextProperties();
                cursor = new MatrixCursor(projection, contextProperties.size());
                for (GenericContextProperty contextProperty : contextProperties) {
                    Object values[] = new Object[projection.length];
                    int i = 0;
                    for (String column : projection) {
                        if (ContextContract.Properties._ID.equals(column)) {
                            values[i++] = contextProperty.getId();
                        } else if (ContextContract.Properties._CONTEXT_PROPERTY.equals(column)) {
                            values[i++] = contextProperty;
                        }
                    }
                    cursor.addRow(values);
                }
                break;
            case CONTEXT_PROPERTY:
                String contextPropertyIdFromUri = uri.getLastPathSegment();
                GenericContextProperty contextProperty = contextStorage.getContextProperty(contextPropertyIdFromUri);
                if (contextProperty == null) {
                    cursor = null;
                    break;
                }
                cursor = new MatrixCursor(projection, 1);
                Object values[] = new Object[projection.length];
                int i = 0;
                for (String column : projection) {
                    if (ContextContract.Properties._ID.equals(column)) {
                        values[i++] = contextPropertyIdFromUri;
                    } else if (ContextContract.Properties._CONTEXT_PROPERTY.equals(column)) {
                        values[i++] = contextProperty;
                    }
                }
                cursor.addRow(values);
                break;
            default:
                throw new IllegalArgumentException("Querying not possible with uri: " + uri);
        }
        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
