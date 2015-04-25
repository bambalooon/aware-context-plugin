package com.aware.plugin.poirecommender.service;

import android.app.IntentService;
import android.content.Intent;

/**
 * Name: ContextService
 * Description: ContextService
 * Date: 2015-03-21
 * Created by BamBalooon
 */
public class PoiRecommenderService extends IntentService {
    private static final String TAG = "PoiRecommenderService";

    public PoiRecommenderService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch (intent.getAction()) {
            default:
                throw new IllegalArgumentException(
                        "Service invoked with unknown action: '" + intent.getAction() + "'.");
        }
    }
}
