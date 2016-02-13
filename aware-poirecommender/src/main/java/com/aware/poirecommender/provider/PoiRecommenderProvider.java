package com.aware.poirecommender.provider;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.aware.utils.DatabaseHelper;

import java.util.ArrayList;

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
    public static final int DATABASE_VERSION = 2;

    private static final int CONTEXT_LIST = 1;
    private static final int CONTEXT = 2;
    private static final int POI_RATING_LIST = 3;
    private static final int POI_RATING = 4;
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
        URI_MATCHER.addURI(
                PoiRecommenderContract.AUTHORITY,
                PoiRecommenderContract.PoisRating.TABLE_NAME,
                POI_RATING_LIST);
        URI_MATCHER.addURI(
                PoiRecommenderContract.AUTHORITY,
                PoiRecommenderContract.PoisRating.TABLE_NAME + "/#",
                POI_RATING);
    }

    private static final String DATABASE_NAME = "plugin_poirecommender.db";

    public static final String[] DATABASE_TABLES = {
            PoiRecommenderContract.Contexts.TABLE_NAME,
            PoiRecommenderContract.PoisRating.TABLE_NAME
    };

    public static final String[] TABLES_FIELDS = {
            //Contexts
            PoiRecommenderContract.Contexts._ID + " integer primary key autoincrement,"
                    + PoiRecommenderContract.Contexts.TIMESTAMP + " real not null,"
                    + PoiRecommenderContract.Contexts.DEVICE_ID + " text not null,"
                    + PoiRecommenderContract.Contexts.POI_ID + " real not null,"
                    + PoiRecommenderContract.Contexts.ACCELEROMETER_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.APPLICATION_FOREGROUND_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.APPLICATIONS_HISTORY_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.APPLICATIONS_NOTIFICATION_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.APPLICATION_CRASH_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.BAROMETER_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.BATTERY_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.BATTERY_CHARGE_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.BATTERY_DISCHARGE_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.BLUETOOTH_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.COMMUNICATION_CALL_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.COMMUNICATION_MESSAGE_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.ESMS_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.GRAVITY_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.GYROSCOPE_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.INSTALLATION_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.LIGHT_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.LINEAR_ACCELEROMETER_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.LOCATION_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.MAGNETOMETER_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.MQTT_MESSAGE_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.MQTT_SUBSCRIPTION_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.NETWORK_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.NETWORK_TRAFFIC_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.PLUGIN_GOOGLE_ACTIVITY_RECOGNITION_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.PLUGIN_OPENWEATHER_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.PROCESSOR_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.PROXIMITY_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.ROTATION_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.SCREEN_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.TELEPHONY_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.TELEPHONY_CDMA_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.TELEPHONY_GSM_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.TELEPHONY_GSM_NEIGHBOR_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.TEMPERATURE_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.TIMEZONE_TIMESTAMP + " real,"
                    + PoiRecommenderContract.Contexts.WIFI_TIMESTAMP + " real," + "UNIQUE ("
                    + PoiRecommenderContract.Contexts.TIMESTAMP + ", "
                    + PoiRecommenderContract.Contexts.DEVICE_ID + ")",
            //POIs rating
            PoiRecommenderContract.PoisRating._ID + " integer primary key autoincrement,"
                    + PoiRecommenderContract.PoisRating.TIMESTAMP + " real not null,"
                    + PoiRecommenderContract.PoisRating.DEVICE_ID + " text not null,"
                    + PoiRecommenderContract.PoisRating.USER_ID + " text not null,"
                    + PoiRecommenderContract.PoisRating.POI_ID + " real not null,"
                    + PoiRecommenderContract.PoisRating.POI_RATING + " real not null," + "UNIQUE ("
                    + PoiRecommenderContract.PoisRating.TIMESTAMP + ", "
                    + PoiRecommenderContract.PoisRating.DEVICE_ID + ")"
    };

    private final ThreadLocal<Boolean> mIsInBatchMode = new ThreadLocal<>();
    private DatabaseHelper mDatabaseHelper;

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(
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
            case POI_RATING_LIST:
                return PoiRecommenderContract.PoisRating.CONTENT_TYPE;
            case POI_RATING:
                return PoiRecommenderContract.PoisRating.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
    }

    @Override
    public ContentProviderResult[] applyBatch(@NonNull ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        mIsInBatchMode.set(true);
        // Works because SQLite Database uses a thread local SQLiteSession object for all manipulations
        db.beginTransaction();
        try {
            final ContentProviderResult[] results = super.applyBatch(operations);
            db.setTransactionSuccessful();
            getContext().getContentResolver().notifyChange(PoiRecommenderContract.CONTENT_URI, null);
            return results;
        } finally {
            mIsInBatchMode.remove();
            db.endTransaction();
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        long id;
        switch (URI_MATCHER.match(uri)) {
            case CONTEXT_LIST:
                id = db.insert(PoiRecommenderContract.Contexts.TABLE_NAME, null, values);
                break;
            case POI_RATING_LIST:
                id = db.insert(PoiRecommenderContract.PoisRating.TABLE_NAME, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unsupported uri for insert operation: " + uri);
        }
        return getUriForId(id, uri);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
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
            case POI_RATING_LIST:
                queryBuilder.setTables(PoiRecommenderContract.PoisRating.TABLE_NAME);
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = PoiRecommenderContract.PoisRating.SORT_ORDER_DEFAULT;
                }
                break;
            case POI_RATING:
                queryBuilder.setTables(PoiRecommenderContract.PoisRating.TABLE_NAME);
                queryBuilder.appendWhere(PoiRecommenderContract.PoisRating._ID + " = " + uri.getLastPathSegment());
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
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int updateCount;
        String where;
        switch (URI_MATCHER.match(uri)) {
            case CONTEXT_LIST:
                updateCount = db.update(PoiRecommenderContract.Contexts.TABLE_NAME, values, selection, selectionArgs);
                break;
            case CONTEXT:
                where = PoiRecommenderContract.Contexts._ID + " = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                updateCount = db.update(PoiRecommenderContract.Contexts.TABLE_NAME, values, where, selectionArgs);
                break;
            case POI_RATING_LIST:
                updateCount = db.update(PoiRecommenderContract.PoisRating.TABLE_NAME, values, selection, selectionArgs);
                break;
            case POI_RATING:
                where = PoiRecommenderContract.PoisRating._ID + " = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                updateCount = db.update(PoiRecommenderContract.PoisRating.TABLE_NAME, values, where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for update operation: " + uri);
        }
        if (updateCount > 0 && !isInBatchMode()) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updateCount;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int deleteCount;
        String where;
        switch (URI_MATCHER.match(uri)) {
            case CONTEXT_LIST:
                deleteCount = db.delete(PoiRecommenderContract.Contexts.TABLE_NAME, selection, selectionArgs);
                break;
            case CONTEXT:
                where = PoiRecommenderContract.Contexts._ID + " = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                deleteCount = db.delete(PoiRecommenderContract.Contexts.TABLE_NAME, where, selectionArgs);
                break;
            case POI_RATING_LIST:
                deleteCount = db.delete(PoiRecommenderContract.PoisRating.TABLE_NAME, selection, selectionArgs);
                break;
            case POI_RATING:
                where = PoiRecommenderContract.PoisRating._ID + " = " + uri.getLastPathSegment();
                if (!TextUtils.isEmpty(selection)) {
                    where += " AND " + selection;
                }
                deleteCount = db.delete(PoiRecommenderContract.PoisRating.TABLE_NAME, where, selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI for delete operation: " + uri);
        }
        if (deleteCount > 0 && !isInBatchMode()) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return deleteCount;
    }

    private Uri getUriForId(long id, Uri uri) {
        if (id > 0) {
            Uri newItemUri = ContentUris.withAppendedId(uri, id);
            if (!isInBatchMode()) {
                getContext().getContentResolver().notifyChange(newItemUri, null);
            }
            return newItemUri;
        }
        throw new SQLException("Problem while inserting into uri: " + uri);
    }

    private boolean isInBatchMode() {
        return mIsInBatchMode.get() != null && mIsInBatchMode.get();
    }
}
