package com.aware.poirecommender.provider;

import android.content.*;
import android.database.Cursor;
import android.os.RemoteException;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.context.property.GenericContextProperty;
import com.aware.poirecommender.openstreetmap.model.response.Element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * Name: PoiRecommenderData
 * Description: PoiRecommenderData
 * Date: 2015-04-25
 * Created by BamBalooon
 */
public class PoiRecommenderData {
    public static final String CONTEXT_PROPERTY_TIMESTAMP_ATTRIBUTE = "timestamp";
    private final Context context;
    private final ContentResolver contentResolver;

    public PoiRecommenderData(Context context) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
    }

    public void addContextAndElement(Collection<GenericContextProperty> contextProperties, Element element)
            throws RemoteException, OperationApplicationException {

        String deviceId = Aware.getSetting(context, Aware_Preferences.DEVICE_ID);
        long poiId = element.getId();

        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        if (!poiExists(poiId)) {
            operations.add(generatePoiInsertOperation(element, deviceId));
            operations.addAll(generatePoiTagsInsertOperations(element.getTags(), deviceId, poiId));
        }

        operations.add(generateContextInsertOperation(contextProperties, deviceId, poiId));
        contentResolver.applyBatch(PoiRecommenderContract.AUTHORITY, operations);
    }

    private ContentProviderOperation generateContextInsertOperation(
            Collection<GenericContextProperty> contextProperties, String deviceId, long poiId) {
        ContentValues values = new ContentValues();
        values.put(PoiRecommenderContract.Contexts.TIMESTAMP, System.currentTimeMillis());
        values.put(PoiRecommenderContract.Contexts.DEVICE_ID, deviceId);
        values.put(PoiRecommenderContract.Contexts.POI_ID, poiId);
        for (GenericContextProperty contextProperty : contextProperties) {
            Long contextPropertyIdAttribute = Long
                    .parseLong((String) contextProperty.getAttributes().get(CONTEXT_PROPERTY_TIMESTAMP_ATTRIBUTE));
            values.put(contextProperty.getId(), contextPropertyIdAttribute);
        }
        return ContentProviderOperation
                .newInsert(PoiRecommenderContract.Contexts.CONTENT_URI)
                .withValues(values)
                .build();
    }

    private ContentProviderOperation generatePoiInsertOperation(Element element, String deviceId) {
        ContentValues values = new ContentValues();
        values.put(PoiRecommenderContract.Pois.TIMESTAMP, System.currentTimeMillis());
        values.put(PoiRecommenderContract.Pois.DEVICE_ID, deviceId);
        values.put(PoiRecommenderContract.Pois.POI_ID, element.getId());
        values.put(PoiRecommenderContract.Pois.TYPE, element.getType());
        values.put(PoiRecommenderContract.Pois.LATITUDE, element.getLat());
        values.put(PoiRecommenderContract.Pois.LONGITUDE, element.getLon());
        return ContentProviderOperation
                .newInsert(PoiRecommenderContract.Pois.CONTENT_URI)
                .withValues(values)
                .build();
    }

    private Collection<ContentProviderOperation> generatePoiTagsInsertOperations(
            Map<String, String> tags, String deviceId, long poiId) {
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        if (tags != null) {
            ContentValues baseValues = new ContentValues();
            baseValues.put(PoiRecommenderContract.PoiTags.DEVICE_ID, deviceId);
            baseValues.put(PoiRecommenderContract.PoiTags.POI_ID, poiId);
            long timestamp = System.currentTimeMillis();
            for (Map.Entry<String, String> tag : tags.entrySet()) {
                ContentValues values = new ContentValues(baseValues);
                values.put(PoiRecommenderContract.PoiTags.TIMESTAMP, timestamp++);
                values.put(PoiRecommenderContract.PoiTags.KEY, tag.getKey());
                values.put(PoiRecommenderContract.PoiTags.VALUE, tag.getValue());
                operations.add(ContentProviderOperation
                        .newInsert(PoiRecommenderContract.PoiTags.CONTENT_URI)
                        .withValues(values)
                        .build());
            }
        }
        return operations;
    }

    private boolean poiExists(long poiId) {
        Cursor cursor = contentResolver.query(
                PoiRecommenderContract.Pois.CONTENT_URI,
                new String[]{PoiRecommenderContract.Pois._ID},
                PoiRecommenderContract.Pois.POI_ID + "=?",
                new String[]{Long.toString(poiId)},
                null);
        try {
            return cursor.moveToFirst();
        } finally {
            cursor.close();
        }
    }
}
