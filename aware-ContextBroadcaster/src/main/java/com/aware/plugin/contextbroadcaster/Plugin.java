package com.aware.plugin.contextbroadcaster;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.aware.Aware;
import com.aware.context.ContextMapping;
import com.aware.context.ContextPropertyCreator;
import com.aware.context.observer.ContextObserver;
import com.aware.context.processor.ContextPropertyProcessor;
import com.aware.context.property.ContextProperty;
import com.aware.context.observer.positioner.NewRecordsCursorPositioner;
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
        contentObservers = new ArrayList<>();
        for (Uri contentUri : ContextMapping.getInstance().getContextUriList()) {
            ContentObserver contextObserver = new ContextObserver<>(
                    contextChangeHandler,
                    contentUri,
                    NewRecordsCursorPositioner.createInstancePositionedAtEnd(contentUri, contentResolver),
                    new ContextPropertyCreator<>(ContextMapping.getInstance()),
                    new ContextPropertyProcessor<ContextProperty>() {
                        @Override
                        public void process(ContextProperty contextProperty) {
                            Log.d(TAG, contextProperty.toString());
                        }
                    });
            contentResolver.registerContentObserver(contentUri, true, contextObserver);
            contentObservers.add(contextObserver);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ContentResolver contentResolver = getContentResolver();
        for (ContentObserver contentObserver : contentObservers) {
            contentResolver.unregisterContentObserver(contentObserver);
        }
        contentObservers = null;

        Aware.setSetting(getApplicationContext(), Settings.STATUS_PLUGIN_CONTEXT_BROADCASTER, false);
        Intent refresh = new Intent(Aware.ACTION_AWARE_REFRESH);
        sendBroadcast(refresh);
    }
}
