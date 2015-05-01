package com.aware.poirecommender.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.provider.Context;
import com.aware.context.transform.ContextPropertySerialization;
import com.aware.poirecommender.openstreetmap.model.response.Element;
import com.aware.poirecommender.provider.PoiRecommenderData;
import com.aware.poirecommender.transform.Serializer;

/**
 * Name: PoiRecommenderService
 * Description: PoiRecommenderService
 * Date: 2015-03-21
 * Created by BamBalooon
 */
public class PoiRecommenderService extends IntentService {
    public static final String ACTION_STORE_POI_WITH_CONTEXT =
            "com.aware.poirecommender.service.PoiRecommenderService.ACTION_STORE_POI_WITH_CONTEXT";
    public static final String POI_EXTRA = "POI_EXTRA";
    private static final String TAG = "PoiRecommenderService";

    public PoiRecommenderService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch (intent.getAction()) {
            case ACTION_STORE_POI_WITH_CONTEXT:
                Log.d(TAG, "Storing current context in database...");
                String poiJson = intent.getStringExtra(POI_EXTRA);
                PoiRecommenderData poiRecommenderData = new PoiRecommenderData(getApplicationContext());
                Serializer<Element> elementSerializer = new Serializer<>(Element.class);
                if (poiJson != null) {
                    poiRecommenderData.addElement(elementSerializer.deserialize(poiJson));
                    Context context = new Context(getContentResolver(),
                            new ContextPropertySerialization<>(GenericContextProperty.class));
                    poiRecommenderData.addContext(context.getContextProperties().values());
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "Service invoked with unknown action: '" + intent.getAction() + "'.");
        }
    }
}
