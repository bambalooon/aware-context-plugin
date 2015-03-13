package com.aware.context.broadcast.processor;

import android.content.Context;
import android.content.Intent;
import com.aware.context.processor.ContextPropertyProcessor;
import com.aware.context.broadcast.property.ContextPropertyParcel;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 */
public class ContextUpdateBroadcaster implements ContextPropertyProcessor<ContextPropertyParcel> {
    public static final String ACTION_AWARE_CONTEXT_UPDATE = "ACTION_AWARE_CONTEXT_UPDATE";
    public static final String CONTEXT_RECORD_EXTRA = "CONTEXT_RECORD_EXTRA";

    private final Context context;

    public ContextUpdateBroadcaster(Context context) {
        this.context = context;
    }

    @Override
    public void process(ContextPropertyParcel contextPropertyParcel) {
        Intent intent = new Intent(ACTION_AWARE_CONTEXT_UPDATE);
        intent.putExtra(CONTEXT_RECORD_EXTRA, contextPropertyParcel);
        context.sendBroadcast(intent);
    }
}
