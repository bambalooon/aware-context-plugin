package com.aware.context.wrapper;

import android.content.Intent;
import com.aware.context.GenericContext;
import com.aware.context.transform.ContextSerialization;

/**
 * Name: GenericContextWrapper
 * Description: GenericContextWrapper
 * Date: 2015-03-23
 * Created by BamBalooon
 */
public class GenericContextWrapper implements ContextIntentWrapper<GenericContext> {
    private final ContextSerialization<GenericContext> contextSerialization = ContextSerialization
            .getDefaultInstance();

    @Override
    public Intent wrapContext(GenericContext context) {
        Intent contextIntent = new Intent();
        contextIntent.putExtra(CONTEXT_EXTRA, contextSerialization.CONTEXT_SERIALIZER.apply(context));
        return contextIntent;
    }

    @Override
    public GenericContext unwrapContext(Intent contextIntent) {
        String serializedContext = contextIntent.getStringExtra(CONTEXT_EXTRA);
        return contextSerialization.CONTEXT_DESERIALIZER.apply(serializedContext);
    }
}
