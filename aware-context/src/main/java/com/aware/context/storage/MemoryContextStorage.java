package com.aware.context.storage;

import com.aware.context.Context;
import com.aware.context.GenericContext;

/**
 * Name: MemoryContextStorage
 * Description: MemoryContextStorage stores given Context in memory
 * Date: 2015-03-22
 * Created by BamBalooon
 */
public class MemoryContextStorage<CTX extends Context> implements ContextStorage<CTX> {
    private static MemoryContextStorage<GenericContext> INSTANCE;
    public static MemoryContextStorage<GenericContext> getDefaultInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MemoryContextStorage<>();
        }
        return INSTANCE;
    }
    private CTX context;

    protected MemoryContextStorage() {
    }

    @Override
    public CTX getContext() {
        return context;
    }

    @Override
    public void setContext(CTX context) {
        this.context = context;
    }
}
