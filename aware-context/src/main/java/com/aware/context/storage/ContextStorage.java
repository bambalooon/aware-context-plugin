package com.aware.context.storage;

import com.aware.context.Context;

/**
 * Name: ContextStorage
 * Description: ContextStorage defines interface to get and set Context
 * Date: 2015-03-22
 * Created by BamBalooon
 *
 * @param <CTX> Context type
 */
public interface ContextStorage<CTX extends Context>  {
    CTX getContext();
    void setContext(CTX context);
}
