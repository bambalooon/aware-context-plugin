package com.aware.context.wrapper;

import android.content.Intent;
import com.aware.context.Context;

/**
 * Name: ContextIntentWrapper
 * Description: ContextIntentWrapper
 * Date: 2015-03-23
 * Created by BamBalooon
 */
public interface ContextIntentWrapper<CTX extends Context> {
    String CONTEXT_EXTRA = "CONTEXT_EXTRA";
    Intent wrapContext(CTX context);
    CTX unwrapContext(Intent contextIntent);
}
