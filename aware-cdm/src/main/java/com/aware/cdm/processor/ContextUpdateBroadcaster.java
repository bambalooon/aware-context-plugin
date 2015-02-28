package com.aware.cdm.processor;

import android.content.Context;
import android.content.Intent;
import com.aware.cdm.record.ContextRecord;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 */
public class ContextUpdateBroadcaster implements ContextRecordProcessor {
    public static final String ACTION_AWARE_CONTEXT_UPDATE = "ACTION_AWARE_CONTEXT_UPDATE";
    public static final String CONTEXT_RECORD_EXTRA = "CONTEXT_RECORD_EXTRA";

    private final Context context;

    public ContextUpdateBroadcaster(Context context) {
        this.context = context;
    }

    @Override
    public void process(ContextRecord contextRecord) {
        Intent intent = new Intent(ACTION_AWARE_CONTEXT_UPDATE);
        intent.putExtra(CONTEXT_RECORD_EXTRA, contextRecord);
        context.sendBroadcast(intent);
    }
}
