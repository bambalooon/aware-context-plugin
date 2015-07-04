package com.aware.poirecommender.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.os.RemoteException;
import android.util.Log;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.provider.Context;
import com.aware.context.transform.ContextPropertySerialization;
import com.aware.poirecommender.provider.PoiRecommenderData;

/**
 * Name: PoiRecommenderService
 * Description: PoiRecommenderService
 * Date: 2015-03-21
 * Created by BamBalooon
 */
public class PoiRecommenderService extends IntentService {
    private static final String TAG = PoiRecommenderService.class.getSimpleName();
    public static final String ACTION_STORE_AND_RATE_POI_WITH_CONTEXT =
            "com.aware.poirecommender.service.PoiRecommenderService.ACTION_STORE_AND_RATE_POI_WITH_CONTEXT";
    public static final String POI_ID_EXTRA = "POI_ID_EXTRA";
    public static final String RATING_EXTRA = "RATING_EXTRA";

    public PoiRecommenderService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch (intent.getAction()) {
            case ACTION_STORE_AND_RATE_POI_WITH_CONTEXT:
                long poiId = intent.getLongExtra(POI_ID_EXTRA, -1);
                double poiRating = intent.getDoubleExtra(RATING_EXTRA, -1);
                if (poiId != -1 && poiRating != -1) {
                    Log.d(TAG, "Storing current context in database...");
                    Context context = new Context(getContentResolver(),
                            new ContextPropertySerialization<>(GenericContextProperty.class));
                    try {
                        new PoiRecommenderData(getApplicationContext())
                                .storeAndRate(context.getContextProperties().values(), poiId, poiRating);
                    } catch (RemoteException | OperationApplicationException e) {
                        Log.e(TAG, "Exception thrown while inserting context and element to database.", e);
                    }
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "Service invoked with unknown action: '" + intent.getAction() + "'.");
        }
    }
}
