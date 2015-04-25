package com.aware.plugin.poirecommender.service;

import android.app.IntentService;
import android.content.Intent;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.provider.Context;
import com.aware.context.transform.ContextPropertySerialization;
import com.aware.plugin.poirecommender.provider.PoiRecommenderData;

/**
 * Name: PoiRecommenderService
 * Description: PoiRecommenderService
 * Date: 2015-03-21
 * Created by BamBalooon
 */
public class PoiRecommenderService extends IntentService {
    public static final String ACTION_STORE_POI_WITH_CONTEXT = "ACTION_STORE_POI_WITH_CONTEXT";
    private static final String TAG = "PoiRecommenderService";

    public PoiRecommenderService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch (intent.getAction()) {
            case ACTION_STORE_POI_WITH_CONTEXT:
                //FIXME: get sent POI
                Context context = new Context(getContentResolver(), new ContextPropertySerialization<>(GenericContextProperty.class));
                new PoiRecommenderData(getContentResolver()).setContext(context.getContextProperties().values());
                break;
            default:
                throw new IllegalArgumentException(
                        "Service invoked with unknown action: '" + intent.getAction() + "'.");
        }
    }
}
