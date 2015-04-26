package com.aware.plugin.poirecommender;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.context.observer.ContextPropertyCreator;
import com.aware.context.observer.ContextPropertyObserver;
import com.aware.context.positioner.NewRecordsCursorPositioner;
import com.aware.context.processor.ContextPropertyProcessor;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.provider.Context;
import com.aware.context.storage.ContextStorage;
import com.aware.context.transform.ContextPropertySerialization;
import com.aware.plugin.poirecommender.application.PoiRecommenderApplication;
import com.aware.poirecommender.provider.PoiRecommenderContract;
import com.aware.poirecommender.provider.PoiRecommenderProvider;
import com.aware.utils.Aware_Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Name: Plugin
 * Description: Plugin
 * Date: 2015-02-20
 * Created by BamBalooon
 */
public class Plugin extends Aware_Plugin {
    public static final String ACTION_AWARE_PLUGIN_POIRECOMMENDER = "ACTION_AWARE_PLUGIN_POIRECOMMENDER";
    private HandlerThread handlerThread;
    private List<ContentObserver> contentObservers;
    private ContextStorage<GenericContextProperty> contextStorage;

    @Override
    public void onCreate() {
        super.onCreate();
        android.content.Context applicationContext = getApplicationContext();
        PoiRecommenderApplication application = PoiRecommenderApplication.getInstance();

        //Initialize plugin settings
        if( DEBUG ) Log.d(TAG, "PoiRecommender plugin running");
        if( Aware.getSetting(applicationContext, Settings.STATUS_PLUGIN_POIRECOMMENDER).length() == 0 ) {
            Aware.setSetting(applicationContext, Settings.STATUS_PLUGIN_POIRECOMMENDER, true);
        }

        CONTEXT_PRODUCER = new ContextProducer() {
            @Override
            public void onContext() {
                Intent broadcast = new Intent(ACTION_AWARE_PLUGIN_POIRECOMMENDER);
                sendBroadcast(broadcast);
            }
        };

        DATABASE_TABLES = PoiRecommenderProvider.DATABASE_TABLES;
        TABLES_FIELDS = PoiRecommenderProvider.TABLES_FIELDS;
        CONTEXT_URIS = new Uri[] {PoiRecommenderContract.Contexts.CONTENT_URI};

        handlerThread = new HandlerThread(TAG);
        handlerThread.start();
        Handler contextPropertyChangeHandler = new Handler(handlerThread.getLooper());

        ContentResolver contentResolver = getContentResolver();
        contextStorage = new Context(contentResolver, new ContextPropertySerialization<>(GenericContextProperty.class));
        contentObservers = new ArrayList<>();
        ContextPropertyCreator<GenericContextProperty> contextPropertyCreator = application.getContextPropertyCreator();
        for (Uri contextPropertyUri : application.getContextPropertyMapping().getContextPropertyUriList()) {
            ContentObserver contextPropertyObserver = new ContextPropertyObserver<>(
                    contextPropertyChangeHandler,
                    contextPropertyUri,
                    NewRecordsCursorPositioner.createInstancePositionedAtEnd(contextPropertyUri, contentResolver),
                    contextPropertyCreator,
                    new ContextPropertyProcessor<GenericContextProperty>() {
                        @Override
                        public void process(GenericContextProperty contextProperty) {
                            Log.d(TAG, contextProperty.toString());
                            contextStorage.setContextProperty(contextProperty.getId(), contextProperty);
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

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            handlerThread.quitSafely();
        } else {
            handlerThread.quit();
        }

        if( DEBUG ) Log.d(TAG, "PoiRecommender plugin terminated");
        Aware.setSetting(getApplicationContext(), Settings.STATUS_PLUGIN_POIRECOMMENDER, false);

        //Apply AWARE settings
        Intent refresh = new Intent(Aware.ACTION_AWARE_REFRESH);
        sendBroadcast(refresh);

        super.onDestroy();
    }
}
