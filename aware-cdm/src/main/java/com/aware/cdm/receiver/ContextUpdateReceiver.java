package com.aware.cdm.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.aware.cdm.processor.ContextRecordProcessor;
import com.aware.cdm.processor.ContextUpdateBroadcaster;
import com.aware.cdm.record.ContextRecord;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 */
public class ContextUpdateReceiver extends BroadcastReceiver {
    private final ContextRecordProcessor contextRecordProcessor;

    public ContextUpdateReceiver(ContextRecordProcessor contextRecordProcessor) {
        this.contextRecordProcessor = contextRecordProcessor;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        ContextRecord contextRecord = intent.getParcelableExtra(ContextUpdateBroadcaster.CONTEXT_RECORD_EXTRA);
        contextRecordProcessor.process(contextRecord);
    }
}
