package com.aware.context;

import com.aware.context.property.ContextProperty;
import com.google.common.collect.Maps;

import java.util.Collection;
import java.util.Map;

/**
 * Name: Context
 * Description: Represents user's current context with ContextProperties
 * Date: 2015-03-22
 * Created by BamBalooon
 *
 * @param <CP> stored ContextProperties type
 * @param <ID> stored ContextProperties identifier type
 */
public class Context<CP extends ContextProperty<ID>, ID> {
    private Map<ID, CP> contextProperties = Maps.newHashMap();

    public Collection<CP> getContextProperties() {
        return contextProperties.values();
    }

    public CP getContextProperty(ID contextPropertyId) {
        return contextProperties.get(contextPropertyId);
    }

    public void setContextProperty(CP contextProperty) {
        contextProperties.put(contextProperty.getId(), contextProperty);
    }
}
