package com.aware.context.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import com.aware.utils.DatabaseHelper;

/**
 * Name: ContextHistoryProvider
 * Description: ContextHistoryProvider
 * Date: 2015-04-20
 * Created by BamBalooon
 */
public class ContextHistoryProvider extends ContentProvider {
    /**
     * Database current version
     */
    public static final int DATABASE_VERSION = 1;

    private static final int CONTEXT_LIST = 1;
    private static final int CONTEXT = 2;
    private static final UriMatcher URI_MATCHER;

    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(
                ContextHistoryContract.AUTHORITY,
                ContextHistoryContract.Contexts.TABLE_NAME,
                CONTEXT_LIST);
        URI_MATCHER.addURI(
                ContextHistoryContract.AUTHORITY,
                ContextHistoryContract.Contexts.TABLE_NAME + "/#",
                CONTEXT);
    }

    private static final String DATABASE_NAME = Environment.getExternalStorageDirectory()
            + "/AWARE/plugin_context_history.db";

    private static final String[] DATABASE_TABLES = { ContextHistoryContract.Contexts.TABLE_NAME };

    private static final String[] TABLES_FIELDS = {
            //Contexts
            ContextHistoryContract.Contexts._ID + " integer primary key autoincrement,"
                    + ContextHistoryContract.Contexts.TIMESTAMP + " real default 0,"
                    + ContextHistoryContract.Contexts.DEVICE_ID + " text default '',"
                    + ContextHistoryContract.Contexts.ACCELEROMETER_ID + " integer,"
                    + ContextHistoryContract.Contexts.APPLICATION_FOREGROUND_ID + " integer,"
                    + ContextHistoryContract.Contexts.APPLICATIONS_HISTORY_ID + " integer,"
                    + ContextHistoryContract.Contexts.APPLICATIONS_NOTIFICATION_ID + " integer,"
                    + ContextHistoryContract.Contexts.APPLICATION_CRASH_ID + " integer,"
                    + ContextHistoryContract.Contexts.BAROMETER_ID + " integer,"
                    + ContextHistoryContract.Contexts.BATTERY_ID + " integer,"
                    + ContextHistoryContract.Contexts.BATTERY_CHARGE_ID + " integer,"
                    + ContextHistoryContract.Contexts.BATTERY_DISCHARGE_ID + " integer,"
                    + ContextHistoryContract.Contexts.BLUETOOTH_ID + " integer,"
                    + ContextHistoryContract.Contexts.COMMUNICATION_CALL_ID + " integer,"
                    + ContextHistoryContract.Contexts.COMMUNICATION_MESSAGE_ID + " integer,"
                    + ContextHistoryContract.Contexts.ESMS_ID + " integer,"
                    + ContextHistoryContract.Contexts.GRAVITY_ID + " integer,"
                    + ContextHistoryContract.Contexts.GYROSCOPE_ID + " integer,"
                    + ContextHistoryContract.Contexts.INSTALLATION_ID + " integer,"
                    + ContextHistoryContract.Contexts.LIGHT_ID + " integer,"
                    + ContextHistoryContract.Contexts.LINEAR_ACCELEROMETER_ID + " integer,"
                    + ContextHistoryContract.Contexts.LOCATION_ID + " integer,"
                    + ContextHistoryContract.Contexts.MAGNETOMETER_ID + " integer,"
                    + ContextHistoryContract.Contexts.MQTT_MESSAGE_ID + " integer,"
                    + ContextHistoryContract.Contexts.MQTT_SUBSCRIPTION_ID + " integer,"
                    + ContextHistoryContract.Contexts.NETWORK_ID + " integer,"
                    + ContextHistoryContract.Contexts.NETWORK_TRAFFIC_ID + " integer,"
                    + ContextHistoryContract.Contexts.PLUGIN_GOOGLE_ACTIVITY_RECOGNITION_ID + " integer,"
                    + ContextHistoryContract.Contexts.PLUGIN_OPENWEATHER_ID + " integer,"
                    + ContextHistoryContract.Contexts.PROCESSOR_ID + " integer,"
                    + ContextHistoryContract.Contexts.PROXIMITY_ID + " integer,"
                    + ContextHistoryContract.Contexts.ROTATION_ID + " integer,"
                    + ContextHistoryContract.Contexts.SCREEN_ID + " integer,"
                    + ContextHistoryContract.Contexts.TELEPHONY_ID + " integer,"
                    + ContextHistoryContract.Contexts.TELEPHONY_CDMA_ID + " integer,"
                    + ContextHistoryContract.Contexts.TELEPHONY_GSM_ID + " integer,"
                    + ContextHistoryContract.Contexts.TELEPHONY_GSM_NEIGHBOR_ID + " integer,"
                    + ContextHistoryContract.Contexts.TEMPERATURE_ID + " integer,"
                    + ContextHistoryContract.Contexts.TIMEZONE_ID + " integer,"
                    + ContextHistoryContract.Contexts.WIFI_ID + " integer," + "UNIQUE ("
                    + ContextHistoryContract.Contexts.TIMESTAMP + ", "
                    + ContextHistoryContract.Contexts.DEVICE_ID + ")"
    };

    private DatabaseHelper databaseHelper;

    @Override
    public boolean onCreate() {
        databaseHelper = new DatabaseHelper(
                getContext(), DATABASE_NAME, null, DATABASE_VERSION, DATABASE_TABLES, TABLES_FIELDS);
        return true;
    }

    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)) {
            case CONTEXT_LIST:
                return ContextHistoryContract.Contexts.CONTENT_TYPE;
            case CONTEXT:
                return ContextHistoryContract.Contexts.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        switch (URI_MATCHER.match(uri)) {
            case CONTEXT_LIST:
                long id = db.insert(ContextHistoryContract.Contexts.TABLE_NAME, null, values);
                return getUriForId(id, uri);
            default:
                throw new IllegalArgumentException("Unsupported uri for insert operation: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        switch (URI_MATCHER.match(uri)) {
            case CONTEXT_LIST:
                queryBuilder.setTables(ContextHistoryContract.Contexts.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ContextHistoryContract.Contexts.SORT_ORDER_DEFAULT;
                }
                break;
            case CONTEXT:
                queryBuilder.setTables(ContextHistoryContract.Contexts.TABLE_NAME);
                queryBuilder.appendWhere(ContextHistoryContract.Contexts._ID + " = " + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for query operation: " + uri);
        }
        Cursor cursor = queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int updateCount;
        switch (URI_MATCHER.match(uri)) {
            case CONTEXT_LIST:
                updateCount = db.update(ContextHistoryContract.Contexts.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CONTEXT:
                String where = ContextHistoryContract.Contexts._ID + " = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                updateCount = db.update(ContextHistoryContract.Contexts.TABLE_NAME, values, where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for update operation: " + uri);
        }
        if (updateCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        int deleteCount;
        switch (URI_MATCHER.match(uri)) {
            case CONTEXT_LIST:
                deleteCount = db.delete(ContextHistoryContract.Contexts.TABLE_NAME, selection, selectionArgs);
                break;
            case CONTEXT:
                String where = ContextHistoryContract.Contexts._ID + " = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                deleteCount = db.delete(ContextHistoryContract.Contexts.TABLE_NAME, where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for delete operation: " + uri);
        }
        if (deleteCount > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleteCount;
    }

    private Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            Uri newItemUri = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(newItemUri, null);
            return newItemUri;
        }
        throw new SQLException("Problem while inserting into uri: " + uri);
    }
}
