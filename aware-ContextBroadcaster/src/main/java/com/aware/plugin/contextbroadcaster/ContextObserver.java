package com.aware.plugin.contextbroadcaster;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.aware.cdm.ContextPropertyCreator;
import com.aware.cdm.processor.ContextPropertyProcessor;
import com.aware.cdm.processor.ContextUpdateBroadcaster;
import com.aware.cdm.record.ContextProperty;
import com.aware.plugin.contextbroadcaster.positioner.CursorPositioner;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 */
public class ContextObserver extends ContentObserver {
    private static final String TAG = ContextObserver.class.getSimpleName();

    private final Uri contentUri;
    private final CursorPositioner cursorPositioner;
    private final ContextPropertyCreator contextPropertyCreator;
    private final ContextPropertyProcessor contextPropertyProcessor;

    public ContextObserver(Handler handler, Uri contentUri, CursorPositioner cursorPositioner, ContextPropertyCreator contextPropertyCreator, ContextUpdateBroadcaster contextPropertyProcessor) {
        super(handler);
        this.contentUri = contentUri;
        this.cursorPositioner = cursorPositioner;
        this.contextPropertyCreator = contextPropertyCreator;
        this.contextPropertyProcessor = contextPropertyProcessor;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);
        cursorPositioner.initialize();

        Cursor cursor;
        while ((cursor = cursorPositioner.moveToNext()) != null) {
            ContextProperty contextProperty = contextPropertyCreator.createContextProperty(contentUri, cursor);
            contextPropertyProcessor.process(contextProperty);
            Log.d(TAG, contextProperty.toString());
        }
    }
}
