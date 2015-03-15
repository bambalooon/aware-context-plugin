package com.aware.context.property;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by BamBalooon on 2015-03-10.
 */
public class GenericContextProperty implements ContextProperty {
    private final String name;
    private final Map<String, Object> attributes;

    public GenericContextProperty(String name, Map<String, Object> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public GenericContextProperty(String name) {
        this(name, Maps.<String, Object>newHashMap());
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public String toString() {
        return "GenericContextProperty{" +
                "name='" + name + "', " +
                FluentIterable.from(attributes.entrySet())
                        .transform(new Function<Map.Entry<String, Object>, String>() {
                            @Override
                            public String apply(Map.Entry<String, Object> input) {
                                return input.getKey() + ": '" + input.getValue() + '\'';
                            }
                        }).join(Joiner.on(", "))
                + '}';
    }
}
