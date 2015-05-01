package com.aware.plugin.poirecommender.application;

import android.app.Application;
import android.net.Uri;
import com.aware.context.factory.ContextPropertyFactory;
import com.aware.context.factory.GenericContextPropertyFactory;
import com.aware.context.observer.ContextPropertyCreator;
import com.aware.context.observer.ContextPropertyMapping;
import com.aware.context.property.GenericContextProperty;
import com.aware.plugin.google.activity_recognition.Google_AR_Provider;
import com.aware.plugin.openweather.Provider;
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
                        Google_AR_Provider.Google_Activity_Recognition_Data.CONTENT_URI,
                        new GenericContextPropertyFactory(
                                PoiRecommenderContract.Contexts.PLUGIN_GOOGLE_ACTIVITY_RECOGNITION_TIMESTAMP,
                                ImmutableMap.<String, Class<?>>builder()
                                        .put(Google_AR_Provider.Google_Activity_Recognition_Data._ID, Integer.class)
                                        .put(Google_AR_Provider.Google_Activity_Recognition_Data.TIMESTAMP, Long.class)
                                        .put(Google_AR_Provider.Google_Activity_Recognition_Data.DEVICE_ID, String.class)
                                        .put(Google_AR_Provider.Google_Activity_Recognition_Data.ACTIVITY_NAME, String.class)
                                        .put(Google_AR_Provider.Google_Activity_Recognition_Data.ACTIVITY_TYPE, Integer.class)
                                        .put(Google_AR_Provider.Google_Activity_Recognition_Data.CONFIDENCE, Integer.class)
                                        .put(Google_AR_Provider.Google_Activity_Recognition_Data.ACTIVITIES, String.class)
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
                        Provider.OpenWeather_Data.CONTENT_URI,
                        new GenericContextPropertyFactory(
                                PoiRecommenderContract.Contexts.PLUGIN_OPENWEATHER_TIMESTAMP,
                                ImmutableMap.<String, Class<?>>builder()
                                        .put(Provider.OpenWeather_Data._ID, Integer.class)
                                        .put(Provider.OpenWeather_Data.TIMESTAMP, Long.class)
                                        .put(Provider.OpenWeather_Data.DEVICE_ID, String.class)
                                        .put(Provider.OpenWeather_Data.CITY, String.class)
                                        .put(Provider.OpenWeather_Data.TEMPERATURE, Double.class)
                                        .put(Provider.OpenWeather_Data.TEMPERATURE_MAX, Double.class)
                                        .put(Provider.OpenWeather_Data.TEMPERATURE_MIN, Double.class)
                                        .put(Provider.OpenWeather_Data.UNITS, String.class)
                                        .put(Provider.OpenWeather_Data.HUMIDITY, Double.class)
                                        .put(Provider.OpenWeather_Data.PRESSURE, Double.class)
                                        .put(Provider.OpenWeather_Data.WIND_SPEED, Double.class)
                                        .put(Provider.OpenWeather_Data.WIND_DEGREES, Double.class)
                                        .put(Provider.OpenWeather_Data.CLOUDINESS, Double.class)
                                        .put(Provider.OpenWeather_Data.RAIN, Double.class)
                                        .put(Provider.OpenWeather_Data.SNOW, Double.class)
                                        .put(Provider.OpenWeather_Data.SUNRISE, Long.class)
                                        .put(Provider.OpenWeather_Data.SUNSET, Long.class)
                                        .put(Provider.OpenWeather_Data.WEATHER_ICON_ID, Integer.class)
                                        .put(Provider.OpenWeather_Data.WEATHER_DESCRIPTION, String.class)
                                        .build()))
                .build());

        contextPropertyCreator = new ContextPropertyCreator<>(contextPropertyMapping);
    }
}