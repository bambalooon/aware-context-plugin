package com.aware.plugin.context;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.aware.context.management.ContextManagement;
import com.aware.context.wrapper.GenericContextWrapper;

/**
 * Name: ContextService
 * Description: ContextService
 * Date: 2015-03-21
 * Created by BamBalooon
 */
public class ContextService extends IntentService {
    private static final String TAG = "ContextService";
    public static final String ACTION_CONTEXT_RETRIEVE = "ACTION_CONTEXT_RETRIEVE";
    public static final String CONTEXT_RECEIVER = "CONTEXT_RECEIVER";

    public ContextService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch (intent.getAction()) {
            case ACTION_CONTEXT_RETRIEVE:
                PendingIntent contextReceiver = intent.getParcelableExtra(CONTEXT_RECEIVER);

                Context applicationContext = getApplicationContext();
                ContextManagement contextManagement = new ContextManagement(applicationContext);

                Intent contextIntent = new GenericContextWrapper().wrapContext(contextManagement.getContext());
                try {
                    contextReceiver.send(applicationContext, Activity.RESULT_OK, contextIntent);
                } catch (PendingIntent.CanceledException e) {
                    Log.d(TAG, e.getMessage(), e);
                }
                break;
            default:
                throw new IllegalArgumentException(
                        "Service invoked with unknown action: '" + intent.getAction() + "'.");
        }
    }
}
