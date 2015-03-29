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
 */
public class Context<CP extends ContextProperty> {
    private Map<String, CP> contextProperties = Maps.newHashMap();

    public Context() {}

    public Context(Collection<CP> contextProperties) {
        for (CP contextProperty : contextProperties) {
            this.contextProperties.put(contextProperty.getId(), contextProperty);
        }
    }

    public Collection<CP> getContextProperties() {
        return contextProperties.values();
    }

    public CP getContextProperty(String contextPropertyId) {
        return contextProperties.get(contextPropertyId);
    }

    public void addContextProperty(CP contextProperty) {
        contextProperties.put(contextProperty.getId(), contextProperty);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Context)) return false;

        Context context = (Context) o;

        if (!contextProperties.equals(context.contextProperties)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return contextProperties.hashCode();
    }
}
