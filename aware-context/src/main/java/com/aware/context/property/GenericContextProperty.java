package com.aware.context.property;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;

import java.util.Map;

/**
 * Created by BamBalooon on 2015-03-10.
 */
public class GenericContextProperty implements ContextProperty {
    private final String id;
    private final Map<String, Object> attributes;

    public GenericContextProperty(String id, Map<String, Object> attributes) {
        Preconditions.checkNotNull(id, "GenericContextProperty ID must be specified.");
        Preconditions.checkNotNull(attributes, "GenericContextProperty attributes map must be specified.");
        this.id = id;
        this.attributes = attributes;
    }

    @Override
    public String getId() {
        return id;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return id + '{' +
                FluentIterable.from(attributes.entrySet())
                        .transform(new Function<Map.Entry<String, Object>, String>() {
                            @Override
                            public String apply(Map.Entry<String, Object> input) {
                                return input.getKey() + ": '" + input.getValue() + '\'';
                            }
                        }).join(Joiner.on(", "))
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericContextProperty)) return false;

        GenericContextProperty that = (GenericContextProperty) o;

        if (!attributes.equals(that.attributes)) return false;
        if (!id.equals(that.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + attributes.hashCode();
        return result;
    }
}
