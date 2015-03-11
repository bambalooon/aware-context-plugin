package com.aware.plugin.contextbroadcaster;

import android.content.*;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.aware.Aware;
import com.aware.cdm.ContextMapping;
import com.aware.cdm.ContextPropertyCreator;
import com.aware.cdm.factory.CellValueRetriever;
import com.aware.cdm.factory.ContextPropertyFactory;
import com.aware.cdm.factory.GenericContextPropertyFactory;
import com.aware.cdm.processor.ContextPropertyProcessor;
import com.aware.cdm.broadcast.processor.ContextUpdateBroadcaster;
import com.aware.cdm.property.ContextProperty;
import com.aware.cdm.broadcast.property.ContextPropertyParcel;
import com.aware.cdm.broadcast.receiver.ContextUpdateReceiver;
import com.aware.plugin.contextbroadcaster.positioner.NewRecordsCursorPositioner;
import com.aware.plugin.google.activity_recognition.Google_AR_Provider;
import com.aware.providers.Locations_Provider;
import com.aware.utils.Aware_Plugin;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Balon on 2015-02-20.
 */
public class Plugin extends Aware_Plugin {
    private static final String TAG = "AWARE::ContextBroadcaster";
    private final Handler contextChangeHandler = new Handler();
    private List<ContentObserver> contentObservers;
    private BroadcastReceiver broadcastReceiver;

    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT_PRODUCER = new ContextProducer() {
            @Override
            public void onContext() {
                //do nothing
            }
        };

        Aware.setSetting(getApplicationContext(), Settings.STATUS_PLUGIN_CONTEXT_BROADCASTER, true);
        Intent refresh = new Intent(Aware.ACTION_AWARE_REFRESH);
        sendBroadcast(refresh);

        ContentResolver contentResolver = getContentResolver();
        Context applicationContext = getApplicationContext();

        contentObservers = new ArrayList<>();
        for (Uri contentUri : ContextMapping.getInstance().getContextUriList()) {
            ContentObserver contextObserver = new ContextObserver<>(
                    contextChangeHandler,
                    contentUri,
                    NewRecordsCursorPositioner.createInstancePositionedAtEnd(contentUri, contentResolver),
                    new ContextPropertyCreator<>(ContextMapping.getInstance()),
                    new ContextUpdateBroadcaster(applicationContext));
            contentResolver.registerContentObserver(contentUri, true, contextObserver);
            contentObservers.add(contextObserver);
        }

        ContextMapping<ContextProperty> contextMapping = new ContextMapping<>(ImmutableMap.<Uri, ContextPropertyFactory<ContextProperty>>builder()
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
                                ImmutableMap.<String,Class<?>>builder()
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
        for (Uri contentUri : contextMapping.getContextUriList()) {
            ContentObserver contextObserver = new ContextObserver<>(
                    contextChangeHandler,
                    contentUri,
                    NewRecordsCursorPositioner.createInstancePositionedAtEnd(contentUri, contentResolver),
                    new ContextPropertyCreator<>(contextMapping),
                    new ContextPropertyProcessor<ContextProperty>() {
                        @Override
                        public void process(ContextProperty contextProperty) {
                            Log.d(TAG, contextProperty.toString());
                        }
                    });
            contentResolver.registerContentObserver(contentUri, true, contextObserver);
            contentObservers.add(contextObserver);
        }

        broadcastReceiver = new ContextUpdateReceiver(new ContextPropertyProcessor<ContextPropertyParcel>() {
            @Override
            public void process(ContextPropertyParcel contextPropertyParcel) {
                Log.d(TAG, contextPropertyParcel.toString());
            }
        });
        registerReceiver(broadcastReceiver, new IntentFilter(ContextUpdateBroadcaster.ACTION_AWARE_CONTEXT_UPDATE));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        for (ContentObserver contentObserver : contentObservers) {
            getContentResolver().unregisterContentObserver(contentObserver);
        }
        contentObservers = null;
        unregisterReceiver(broadcastReceiver);

        Aware.setSetting(getApplicationContext(), Settings.STATUS_PLUGIN_CONTEXT_BROADCASTER, false);
        Intent refresh = new Intent(Aware.ACTION_AWARE_REFRESH);
        sendBroadcast(refresh);
    }
}
