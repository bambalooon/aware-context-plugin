package com.aware.plugin.contextbroadcaster;

import android.content.*;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.aware.Aware;
import com.aware.cdm.ContextMapping;
import com.aware.cdm.ContextPropertyCreator;
import com.aware.cdm.processor.ContextPropertyProcessor;
import com.aware.cdm.processor.ContextUpdateBroadcaster;
import com.aware.cdm.receiver.ContextUpdateReceiver;
import com.aware.cdm.record.ContextProperty;
import com.aware.plugin.contextbroadcaster.positioner.NewRecordsCursorPositioner;
import com.aware.utils.Aware_Plugin;

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
            ContentObserver contextObserver = new ContextObserver(
                    contextChangeHandler,
                    contentUri,
                    NewRecordsCursorPositioner.createInstancePositionedAtEnd(contentUri, contentResolver),
                    new ContextPropertyCreator(),
                    new ContextUpdateBroadcaster(applicationContext));
            contentResolver.registerContentObserver(contentUri, true, contextObserver);
            contentObservers.add(contextObserver);
        }

        broadcastReceiver = new ContextUpdateReceiver(new ContextPropertyProcessor() {
            @Override
            public void process(ContextProperty contextProperty) {
                Log.d(TAG, contextProperty.toString());
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
