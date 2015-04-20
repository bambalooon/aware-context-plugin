package com.aware.context.provider;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Name: ContextContract
 * Description: ContextContract
 * Date: 2015-03-28
 * Created by BamBalooon
 */
public final class ContextContract {
    public static final String AUTHORITY = "com.aware.context.provider.context";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Properties {
        /**
         * Content URI for Properties table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContextContract.CONTENT_URI, "properties");

        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/vnd.com.aware.context.provider.context_properties";

        /**
         * The mime type of a single item.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/vnd.com.aware.context.provider.context_properties";

        /**
         * The unique ID for a row.
         * <P>Type: STRING (String)</P>
         */
        public static final String _ID = "_id";

        /**
         * The context property column.
         * <P>Type: STRING (String)</P>
         */
        public static final String CONTEXT_PROPERTY = "context_property";

        /**
         * A projection of all columns in ContextProperties table.
         */
        public static final String[] PROJECTION_ALL = {
            _ID, CONTEXT_PROPERTY
        };
    }


}
