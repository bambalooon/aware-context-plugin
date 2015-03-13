package com.aware.context.broadcast.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.aware.context.processor.ContextPropertyProcessor;
import com.aware.context.broadcast.processor.ContextUpdateBroadcaster;
import com.aware.context.broadcast.property.ContextPropertyParcel;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 */
public class ContextUpdateReceiver extends BroadcastReceiver {
    private final ContextPropertyProcessor<ContextPropertyParcel> contextPropertyProcessor;

    public ContextUpdateReceiver(ContextPropertyProcessor<ContextPropertyParcel> contextPropertyProcessor) {
        this.contextPropertyProcessor = contextPropertyProcessor;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ContextPropertyParcel contextPropertyParcel = intent.getParcelableExtra(ContextUpdateBroadcaster.CONTEXT_RECORD_EXTRA);
        contextPropertyProcessor.process(contextPropertyParcel);
    }
}
