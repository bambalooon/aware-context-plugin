package com.aware.plugin.poirecommender.application;

import android.app.Application;
import android.net.Uri;
import com.aware.context.factory.ContextPropertyFactory;
import com.aware.context.factory.GenericContextPropertyFactory;
import com.aware.context.observer.ContextPropertyCreator;
import com.aware.context.observer.ContextPropertyMapping;
import com.aware.context.property.GenericContextProperty;
import com.aware.poirecommender.provider.PoiRecommenderContract;
import com.aware.providers.Locations_Provider;
import com.google.common.collect.ImmutableMap;

/**
 * Name: PoiRecommenderApplication
 * Description: PoiRecommenderApplication
 * Date: 2015-04-25
 * Created by BamBalooon
 */
public class PoiRecommenderApplication extends Application {
    private static PoiRecommenderApplication sInstance;
    public static PoiRecommenderApplication getInstance() {
        if (sInstance == null) {
            throw new ExceptionInInitializerError("PoiRecommenderApplication wasn't initialized. "
                    + "PoiRecommenderApplication has to be specified as application name in manifest.");
        }
        return sInstance;
    }
    private ContextPropertyMapping<GenericContextProperty> contextPropertyMapping;
    private ContextPropertyCreator<GenericContextProperty> contextPropertyCreator;

    public ContextPropertyMapping<GenericContextProperty> getContextPropertyMapping() {
        return contextPropertyMapping;
    }

    public ContextPropertyCreator<GenericContextProperty> getContextPropertyCreator() {
        return contextPropertyCreator;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initializeApplication();
    }

    private void initializeApplication() {
        contextPropertyMapping = new ContextPropertyMapping<>(ImmutableMap.<Uri, ContextPropertyFactory<GenericContextProperty>>builder()
                .put(
                        Uri.parse("content://com.aware.plugin.google.activity_recognition.provider/plugin_google_activity_recognition"),
                        new GenericContextPropertyFactory(
                                PoiRecommenderContract.Contexts.PLUGIN_GOOGLE_ACTIVITY_RECOGNITION_TIMESTAMP,
                                ImmutableMap.<String, Class<?>>builder()
                                        .put("_id", Integer.class)
                                        .put("timestamp", Long.class)
                                        .put("device_id", String.class)
                                        .put("activity_name", String.class)
                                        .put("activity_type", Integer.class)
                                        .put("confidence", Integer.class)
                                        .put("activities", String.class)
                                        .build()))
                .put(
                        Locations_Provider.Locations_Data.CONTENT_URI,
                        new GenericContextPropertyFactory(
                                PoiRecommenderContract.Contexts.LOCATION_TIMESTAMP,
                                ImmutableMap.<String, Class<?>>builder()
                                        .put(Locations_Provider.Locations_Data._ID, Integer.class)
                                        .put(Locations_Provider.Locations_Data.TIMESTAMP, Long.class)
                                        .put(Locations_Provider.Locations_Data.DEVICE_ID, String.class)
                                        .put(Locations_Provider.Locations_Data.LATITUDE, Double.class)
                                        .put(Locations_Provider.Locations_Data.LONGITUDE, Double.class)
                                        .put(Locations_Provider.Locations_Data.BEARING, Double.class)
                                        .put(Locations_Provider.Locations_Data.SPEED, Double.class)
                                        .put(Locations_Provider.Locations_Data.ALTITUDE, Double.class)
                                        .put(Locations_Provider.Locations_Data.PROVIDER, String.class)
                                        .put(Locations_Provider.Locations_Data.ACCURACY, Double.class)
                                        .put(Locations_Provider.Locations_Data.LABEL, String.class)
                                        .build()))
                .put(
                        Uri.parse("content://com.aware.plugin.openweather.provider.openweather/plugin_openweather"),
                        new GenericContextPropertyFactory(
                                PoiRecommenderContract.Contexts.PLUGIN_OPENWEATHER_TIMESTAMP,
                                ImmutableMap.<String, Class<?>>builder()
                                        .put("_id", Integer.class)
                                        .put("timestamp", Long.class)
                                        .put("device_id", String.class)
                                        .put("city", String.class)
                                        .put("temperature", Double.class)
                                        .put("temperature_max", Double.class)
                                        .put("temperature_min", Double.class)
                                        .put("unit", String.class)
                                        .put("humidity", Double.class)
                                        .put("pressure", Double.class)
                                        .put("wind_speed", Double.class)
                                        .put("wind_degrees", Double.class)
                                        .put("cloudiness", Double.class)
                                        .put("rain", Double.class)
                                        .put("snow", Double.class)
                                        .put("sunrise", Long.class)
                                        .put("sunset", Long.class)
                                        .put("weather_icon_id", Integer.class)
                                        .put("weather_description", String.class)
                                        .build()))
                .build());

        contextPropertyCreator = new ContextPropertyCreator<>(contextPropertyMapping);
    }
}