package com.aware.poirecommender.provider;

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
 * Name: PoiRecommenderProvider
 * Description: PoiRecommenderProvider
 * Date: 2015-04-20
 * Created by BamBalooon
 */
public class PoiRecommenderProvider extends ContentProvider {
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
                PoiRecommenderContract.AUTHORITY,
                PoiRecommenderContract.Contexts.TABLE_NAME,
                CONTEXT_LIST);
        URI_MATCHER.addURI(
                PoiRecommenderContract.AUTHORITY,
                PoiRecommenderContract.Contexts.TABLE_NAME + "/#",
                CONTEXT);
    }

    private static final String DATABASE_NAME = Environment.getExternalStorageDirectory()
            + "/AWARE/plugin_poirecommender.db";

    private static final String[] DATABASE_TABLES = { PoiRecommenderContract.Contexts.TABLE_NAME };

    private static final String[] TABLES_FIELDS = {
            //Contexts
            PoiRecommenderContract.Contexts._ID + " integer primary key autoincrement,"
                    + PoiRecommenderContract.Contexts.TIMESTAMP + " real default 0,"
                    + PoiRecommenderContract.Contexts.DEVICE_ID + " text default '',"
                    + PoiRecommenderContract.Contexts.ACCELEROMETER_ID + " integer,"
                    + PoiRecommenderContract.Contexts.APPLICATION_FOREGROUND_ID + " integer,"
                    + PoiRecommenderContract.Contexts.APPLICATIONS_HISTORY_ID + " integer,"
                    + PoiRecommenderContract.Contexts.APPLICATIONS_NOTIFICATION_ID + " integer,"
                    + PoiRecommenderContract.Contexts.APPLICATION_CRASH_ID + " integer,"
                    + PoiRecommenderContract.Contexts.BAROMETER_ID + " integer,"
                    + PoiRecommenderContract.Contexts.BATTERY_ID + " integer,"
                    + PoiRecommenderContract.Contexts.BATTERY_CHARGE_ID + " integer,"
                    + PoiRecommenderContract.Contexts.BATTERY_DISCHARGE_ID + " integer,"
                    + PoiRecommenderContract.Contexts.BLUETOOTH_ID + " integer,"
                    + PoiRecommenderContract.Contexts.COMMUNICATION_CALL_ID + " integer,"
                    + PoiRecommenderContract.Contexts.COMMUNICATION_MESSAGE_ID + " integer,"
                    + PoiRecommenderContract.Contexts.ESMS_ID + " integer,"
                    + PoiRecommenderContract.Contexts.GRAVITY_ID + " integer,"
                    + PoiRecommenderContract.Contexts.GYROSCOPE_ID + " integer,"
                    + PoiRecommenderContract.Contexts.INSTALLATION_ID + " integer,"
                    + PoiRecommenderContract.Contexts.LIGHT_ID + " integer,"
                    + PoiRecommenderContract.Contexts.LINEAR_ACCELEROMETER_ID + " integer,"
                    + PoiRecommenderContract.Contexts.LOCATION_ID + " integer,"
                    + PoiRecommenderContract.Contexts.MAGNETOMETER_ID + " integer,"
                    + PoiRecommenderContract.Contexts.MQTT_MESSAGE_ID + " integer,"
                    + PoiRecommenderContract.Contexts.MQTT_SUBSCRIPTION_ID + " integer,"
                    + PoiRecommenderContract.Contexts.NETWORK_ID + " integer,"
                    + PoiRecommenderContract.Contexts.NETWORK_TRAFFIC_ID + " integer,"
                    + PoiRecommenderContract.Contexts.PLUGIN_GOOGLE_ACTIVITY_RECOGNITION_ID + " integer,"
                    + PoiRecommenderContract.Contexts.PLUGIN_OPENWEATHER_ID + " integer,"
                    + PoiRecommenderContract.Contexts.PROCESSOR_ID + " integer,"
                    + PoiRecommenderContract.Contexts.PROXIMITY_ID + " integer,"
                    + PoiRecommenderContract.Contexts.ROTATION_ID + " integer,"
                    + PoiRecommenderContract.Contexts.SCREEN_ID + " integer,"
                    + PoiRecommenderContract.Contexts.TELEPHONY_ID + " integer,"
                    + PoiRecommenderContract.Contexts.TELEPHONY_CDMA_ID + " integer,"
                    + PoiRecommenderContract.Contexts.TELEPHONY_GSM_ID + " integer,"
                    + PoiRecommenderContract.Contexts.TELEPHONY_GSM_NEIGHBOR_ID + " integer,"
                    + PoiRecommenderContract.Contexts.TEMPERATURE_ID + " integer,"
                    + PoiRecommenderContract.Contexts.TIMEZONE_ID + " integer,"
                    + PoiRecommenderContract.Contexts.WIFI_ID + " integer," + "UNIQUE ("
                    + PoiRecommenderContract.Contexts.TIMESTAMP + ", "
                    + PoiRecommenderContract.Contexts.DEVICE_ID + ")"
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
                return PoiRecommenderContract.Contexts.CONTENT_TYPE;
            case CONTEXT:
                return PoiRecommenderContract.Contexts.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        switch (URI_MATCHER.match(uri)) {
            case CONTEXT_LIST:
                long id = db.insert(PoiRecommenderContract.Contexts.TABLE_NAME, null, values);
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
                queryBuilder.setTables(PoiRecommenderContract.Contexts.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = PoiRecommenderContract.Contexts.SORT_ORDER_DEFAULT;
                }
                break;
            case CONTEXT:
                queryBuilder.setTables(PoiRecommenderContract.Contexts.TABLE_NAME);
                queryBuilder.appendWhere(PoiRecommenderContract.Contexts._ID + " = " + uri.getLastPathSegment());
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
                updateCount = db.update(PoiRecommenderContract.Contexts.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CONTEXT:
                String where = PoiRecommenderContract.Contexts._ID + " = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                updateCount = db.update(PoiRecommenderContract.Contexts.TABLE_NAME, values, where, selectionArgs);
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
                deleteCount = db.delete(PoiRecommenderContract.Contexts.TABLE_NAME, selection, selectionArgs);
                break;
            case CONTEXT:
                String where = PoiRecommenderContract.Contexts._ID + " = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                deleteCount = db.delete(PoiRecommenderContract.Contexts.TABLE_NAME, where, selectionArgs);
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