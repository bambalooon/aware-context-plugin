package com.aware.context.storage;

import java.util.Map;

/**
 * Name: ContextStorage
 * Description: ContextStorage defines interface to get and set Context
 * Date: 2015-03-22
 * Created by BamBalooon
 *
 * @param <CP> ContextProperty type (could also be serialized String)
 */
public interface ContextStorage<CP>  {
    CP getContextProperty(String contextPropertyId);
    void setContextProperty(String contextPropertyId, CP contextProperty);
    Map<String, CP> getContextProperties();
}
