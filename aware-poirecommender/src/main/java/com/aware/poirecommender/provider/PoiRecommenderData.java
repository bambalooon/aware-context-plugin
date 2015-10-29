package com.aware.poirecommender.provider;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.context.property.GenericContextProperty;
import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Collection;

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

    public void ratePoi(String userId, long poiId, double poiRating) throws RemoteException, OperationApplicationException {
        ArrayList<ContentProviderOperation> operations = Lists.newArrayList(
                generatePoiRatingsDeleteOperation(poiId),
                generatePoiRatingInsertOperation(userId, poiId, poiRating)
        );

        contentResolver.applyBatch(PoiRecommenderContract.AUTHORITY, operations);
    }

    public void storeContext(Collection<GenericContextProperty> contextProperties, long poiId) {
        String deviceId = Aware.getSetting(context, Aware_Preferences.DEVICE_ID);

        contentResolver.insert(
                PoiRecommenderContract.Contexts.CONTENT_URI,
                generateContextInsertValues(contextProperties, deviceId, poiId)
        );
    }

    private ContentProviderOperation generatePoiRatingsDeleteOperation(long poiId) {
        return ContentProviderOperation
                .newDelete(PoiRecommenderContract.PoisRating.CONTENT_URI)
                .withSelection(PoiRecommenderContract.PoisRating.POI_ID + "=?", new String[]{Long.toString(poiId)})
                .build();
    }

    private ContentProviderOperation generatePoiRatingInsertOperation(String userId, long poiId, double poiRating) {
        ContentValues values = new ContentValues();
        values.put(PoiRecommenderContract.PoisRating.TIMESTAMP, System.currentTimeMillis());
        values.put(PoiRecommenderContract.PoisRating.USER_ID, userId);
        values.put(PoiRecommenderContract.PoisRating.POI_ID, poiId);
        values.put(PoiRecommenderContract.PoisRating.POI_RATING, poiRating);
        return ContentProviderOperation
                .newInsert(PoiRecommenderContract.PoisRating.CONTENT_URI)
                .withValues(values)
                .build();
    }

    private ContentValues generateContextInsertValues(Collection<GenericContextProperty> contextProperties,
                                                      String deviceId,
                                                      long poiId) {
        ContentValues values = new ContentValues();
        values.put(PoiRecommenderContract.Contexts.TIMESTAMP, System.currentTimeMillis());
        values.put(PoiRecommenderContract.Contexts.DEVICE_ID, deviceId);
        values.put(PoiRecommenderContract.Contexts.POI_ID, poiId);
        for (GenericContextProperty contextProperty : contextProperties) {
            Long contextPropertyIdAttribute = Long
                    .parseLong((String) contextProperty.getAttributes().get(CONTEXT_PROPERTY_TIMESTAMP_ATTRIBUTE));
            values.put(contextProperty.getId(), contextPropertyIdAttribute);
        }
        return values;
    }
}
