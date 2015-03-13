package com.aware.context.observer;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.aware.context.ContextPropertyCreator;
import com.aware.context.observer.positioner.CursorPositioner;
import com.aware.context.processor.ContextPropertyProcessor;
import com.aware.context.property.ContextProperty;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 * @param <CP> processed ContextProperty type
 */
public class ContextObserver<CP extends ContextProperty> extends ContentObserver {
    private static final String TAG = ContextObserver.class.getSimpleName();

    private final Uri contentUri;
    private final CursorPositioner cursorPositioner;
    private final ContextPropertyCreator<CP> contextPropertyCreator;
    private final ContextPropertyProcessor<CP> contextPropertyProcessor;

    public ContextObserver(Handler handler, Uri contentUri, CursorPositioner cursorPositioner, ContextPropertyCreator<CP> contextPropertyCreator, ContextPropertyProcessor<CP> contextPropertyProcessor) {
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
            CP contextProperty = contextPropertyCreator.createContextProperty(contentUri, cursor);
            contextPropertyProcessor.process(contextProperty);
        }
    }
}
