package com.aware.context.observer;

import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import com.aware.context.positioner.CursorPositioner;
import com.aware.context.processor.ContextPropertyProcessor;
import com.aware.context.property.ContextProperty;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 * @param <CP> processed ContextProperty type
 */
public class ContextPropertyObserver<CP extends ContextProperty> extends ContentObserver {
    private final Uri contextPropertyUri;
    private final CursorPositioner cursorPositioner;
    private final ContextPropertyCreator<CP> contextPropertyCreator;
    private final ContextPropertyProcessor<CP> contextPropertyProcessor;

    public ContextPropertyObserver(Handler handler,
                                   Uri contextPropertyUri,
                                   CursorPositioner cursorPositioner,
                                   ContextPropertyCreator<CP> contextPropertyCreator,
                                   ContextPropertyProcessor<CP> contextPropertyProcessor) {
        super(handler);
        this.contextPropertyUri = contextPropertyUri;
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
            CP contextProperty = contextPropertyCreator.createContextProperty(contextPropertyUri, cursor);
            contextPropertyProcessor.process(contextProperty);
        }
    }
}
