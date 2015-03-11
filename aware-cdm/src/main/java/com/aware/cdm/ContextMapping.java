package com.aware.cdm;

import android.net.Uri;
import com.aware.cdm.factory.CellValueRetriever;
import com.aware.cdm.factory.ContextPropertyFactory;
import com.aware.cdm.factory.GenericContextPropertyFactory;
import com.aware.cdm.property.ContextProperty;
import com.aware.plugin.google.activity_recognition.Google_AR_Provider;
import com.aware.providers.Locations_Provider;
import com.google.common.collect.ImmutableMap;

import java.util.Map;
import java.util.Set;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 * @param <CP> mapped ContextProperty type
 */
public class ContextMapping<CP extends ContextProperty> {
    private static ContextMapping<ContextProperty> INSTANCE;
    public static ContextMapping<ContextProperty> getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ContextMapping<>(ImmutableMap.<Uri, ContextPropertyFactory<ContextProperty>>builder()
                    .put(
                            Google_AR_Provider.Google_Activity_Recognition_Data.CONTENT_URI,
                            new GenericContextPropertyFactory(
                                    new CellValueRetriever(),
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
                                    new CellValueRetriever(),
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
                                            .build())
                    )
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
