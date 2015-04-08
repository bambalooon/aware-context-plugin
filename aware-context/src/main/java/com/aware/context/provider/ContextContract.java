package com.aware.context.provider;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Name: ContextContract
 * Description: ContextContract
 * Date: 2015-03-28
 * Created by BamBalooon
 */
public final class ContextContract {
    public static final String AUTHORITY = "com.aware.context.provider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Context implements BaseColumns {
        /**
         * Content URI for ContextProperties table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(ContextContract.CONTENT_URI, "context");

        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/vnd.com.aware.context.provider.context";

        /**
         * The mime type of a single item.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/vnd.com.aware.context.provider.context.property";

        /**
         * The context property column.
         */
        public static final String _CONTEXT_PROPERTY = "context_property";

        /**
         * A projection of all columns in ContextProperties table.
         */
        public static final String[] PROJECTION_ALL = {
            _ID, _CONTEXT_PROPERTY
        };
    }


}
