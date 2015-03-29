package com.aware.plugin.context;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.context.application.ContextApplication;
import com.aware.context.management.ContextManagement;
import com.aware.context.observer.ContextPropertyCreator;
import com.aware.context.observer.ContextPropertyMapping;
import com.aware.context.observer.ContextPropertyObserver;
import com.aware.context.positioner.NewRecordsCursorPositioner;
import com.aware.context.processor.ContextPropertyProcessor;
import com.aware.context.property.GenericContextProperty;
import com.aware.utils.Aware_Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Krzysztof Balon on 2015-02-20.
 */
public class Plugin extends Aware_Plugin {
    public static final String ACTION_AWARE_PLUGIN_CONTEXT = "ACTION_AWARE_PLUGIN_CONTEXT";
    private HandlerThread handlerThread;
    private List<ContentObserver> contentObservers;
    private ContextManagement contextManagement;

    @Override
    public void onCreate() {
        super.onCreate();
        Context applicationContext = getApplicationContext();

        //Initialize plugin settings
        if( DEBUG ) Log.d(TAG, "Context plugin running");
        if( Aware.getSetting(applicationContext, Settings.STATUS_PLUGIN_CONTEXT).length() == 0 ) {
            Aware.setSetting(applicationContext, Settings.STATUS_PLUGIN_CONTEXT, true);
        }

        CONTEXT_PRODUCER = new ContextProducer() {
            @Override
            public void onContext() {
                Intent broadcast = new Intent(ACTION_AWARE_PLUGIN_CONTEXT);
                sendBroadcast(broadcast);
            }
        };

        handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        Handler contextPropertyChangeHandler = new Handler(handlerThread.getLooper());

        contextManagement = ContextApplication.getInstance().getContextManagement();
        ContentResolver contentResolver = getContentResolver();
        contentObservers = new ArrayList<>();
        for (Uri contextPropertyUri : ContextPropertyMapping.getDefaultInstance().getContextPropertyUriList()) {
            ContentObserver contextPropertyObserver = new ContextPropertyObserver<>(
                    contextPropertyChangeHandler,
                    contextPropertyUri,
                    NewRecordsCursorPositioner.createInstancePositionedAtEnd(contextPropertyUri, contentResolver),
                    ContextPropertyCreator.getDefaultInstance(),
                    new ContextPropertyProcessor<GenericContextProperty>() {
                        @Override
                        public void process(GenericContextProperty contextProperty) {
                            Log.d(TAG, contextProperty.toString());
                            contextManagement.setContextProperty(contextProperty);
                            CONTEXT_PRODUCER.onContext();
                        }
                    });
            contentResolver.registerContentObserver(contextPropertyUri, true, contextPropertyObserver);
            contentObservers.add(contextPropertyObserver);
        }
        //Apply AWARE settings
        Intent refresh = new Intent(Aware.ACTION_AWARE_REFRESH);
        sendBroadcast(refresh);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TAG = "AWARE::ContextPlugin";
        DEBUG = Aware.getSetting(this, Aware_Preferences.DEBUG_FLAG).equals("true");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        ContentResolver contentResolver = getContentResolver();
        for (ContentObserver contentObserver : contentObservers) {
            contentResolver.unregisterContentObserver(contentObserver);
        }
        contentObservers = null;

        if( DEBUG ) Log.d(TAG, "Context plugin terminated");
        Aware.setSetting(getApplicationContext(), Settings.STATUS_PLUGIN_CONTEXT, false);

        //Apply AWARE settings
        Intent refresh = new Intent(Aware.ACTION_AWARE_REFRESH);
        sendBroadcast(refresh);

        super.onDestroy();
    }
}
