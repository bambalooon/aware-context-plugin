package com.aware.cdm;

import android.net.Uri;
import com.aware.cdm.broadcast.factory.*;
import com.aware.cdm.factory.*;
import com.aware.cdm.property.ContextProperty;
import com.aware.cdm.broadcast.property.ContextPropertyParcel;
import com.aware.plugin.google.activity_recognition.Google_AR_Provider;
import com.aware.plugin.openweather.Provider;
import com.aware.providers.Locations_Provider;
import com.aware.providers.WiFi_Provider;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Set;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 * @param <CP> mapped ContextProperty type
 */
public class ContextMapping<CP extends ContextProperty> {
    private static ContextMapping<ContextPropertyParcel> INSTANCE;
    public static ContextMapping<ContextPropertyParcel> getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ContextMapping<>(ImmutableMap.<Uri, ContextPropertyFactory<ContextPropertyParcel>>builder()
                    .put(Locations_Provider.Locations_Data.CONTENT_URI, new LocationPropertyFactory())
                    .put(Google_AR_Provider.Google_Activity_Recognition_Data.CONTENT_URI, new GoogleActivityRecognitionPropertyFactory())
                    .put(Provider.OpenWeather_Data.CONTENT_URI, new OpenWeatherPropertyFactory())
                    .put(WiFi_Provider.WiFi_Sensor.CONTENT_URI, new WifiSensorPropertyFactory())
                    .put(WiFi_Provider.WiFi_Data.CONTENT_URI, new WifiDataPropertyFactory())
                    .build());
        }
        return INSTANCE;
    }

    private final Map<Uri, ContextPropertyFactory<CP>> map;

    public ContextMapping(Map<Uri, ContextPropertyFactory<CP>> map) {
        this.map = map;
    }

    public ContextPropertyFactory<CP> getContextPropertyFactory(Uri uri) {
        return map.get(uri);
    }

    public Set<Uri> getContextUriList() {
        return map.keySet();
    }
}
