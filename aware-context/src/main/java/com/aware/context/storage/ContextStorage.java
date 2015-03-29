package com.aware.context.storage;

import com.aware.context.Context;
import com.aware.context.property.ContextProperty;

import java.util.Collection;

/**
 * Name: ContextStorage
 * Description: ContextStorage defines interface to get and set Context
 * Date: 2015-03-22
 * Created by BamBalooon
 *
 * @param <CP> ContextProperty type
 */
public interface ContextStorage<CP extends ContextProperty>  {
    CP getContextProperty(String contextPropertyId);
    void setContextProperty(CP contextProperty);
    Collection<CP> getContextProperties();
    Context<CP> getContext();
}
