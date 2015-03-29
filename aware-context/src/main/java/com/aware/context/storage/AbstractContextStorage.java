package com.aware.context.storage;

import com.aware.context.Context;
import com.aware.context.property.ContextProperty;

/**
 * Name: AbstractContextStorage
 * Description: AbstractContextStorage defines default implementation of getContext() method
 * Date: 2015-03-29
 * Created by BamBalooon
 *
 * @param <CP> ContextProperty type
 */
public abstract class AbstractContextStorage<CP extends ContextProperty> implements ContextStorage<CP> {

    @Override
    public Context<CP> getContext() {
        return new Context<>(getContextProperties());
    }
}
