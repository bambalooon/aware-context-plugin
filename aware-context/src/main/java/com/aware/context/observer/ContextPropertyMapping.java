package com.aware.context.observer;

import android.net.Uri;
import com.aware.context.factory.ContextPropertyFactory;
import com.aware.context.factory.GenericContextPropertyFactory;
import com.aware.context.property.ContextProperty;
import com.aware.context.property.GenericContextProperty;
import com.aware.plugin.google.activity_recognition.Google_AR_Provider;
import com.aware.plugin.openweather.Provider;
import com.aware.providers.Locations_Provider;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Set;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 * @param <CP> mapped ContextProperty type
 */
public class ContextPropertyMapping<CP extends ContextProperty> {
    private static ContextPropertyMapping<GenericContextProperty> DEFAULT_INSTANCE;
    public static ContextPropertyMapping<GenericContextProperty> getDefaultInstance() {
        if (DEFAULT_INSTANCE == null) {
            DEFAULT_INSTANCE = new ContextPropertyMapping<>(ImmutableMap.<Uri, ContextPropertyFactory<GenericContextProperty>>builder()
                    .put(
                            Google_AR_Provider.Google_Activity_Recognition_Data.CONTENT_URI,
                            new GenericContextPropertyFactory(
                                    Google_AR_Provider.AUTHORITY,
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
                                    Locations_Provider.AUTHORITY,
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
                                    Provider.AUTHORITY,
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
        }
        return DEFAULT_INSTANCE;
    }

    private final Map<Uri, ContextPropertyFactory<CP>> map;

    public ContextPropertyMapping(Map<Uri, ContextPropertyFactory<CP>> map) {
        this.map = map;
    }

    public ContextPropertyFactory<CP> getContextPropertyFactory(Uri uri) {
        return map.get(uri);
    }

    public Set<Uri> getContextPropertyUriList() {
        return map.keySet();
    }
}
