package com.aware.context.property;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;

import java.util.Map;

/**
 * Created by BamBalooon on 2015-03-10.
 */
public class GenericContextProperty implements ContextProperty<String> {
    private final String id;
    private final Map<String, Object> attributes;

    public GenericContextProperty(String id, Map<String, Object> attributes) {
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
        return "GenericContextProperty{" +
                "id='" + id + "', " +
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
