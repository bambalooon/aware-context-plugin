package com.aware.context.property;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.FluentIterable;

import java.util.Map;

/**
 * Created by BamBalooon on 2015-03-10.
 */
public class GenericContextProperty implements ContextProperty {
    private final String name;
    private final Map<String, Object> values;

    public GenericContextProperty(String name, Map<String, Object> values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public Map<String, Object> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return "GenericContextProperty{" +
                "name='" + name + "', " +
                FluentIterable.from(values.entrySet())
                        .transform(new Function<Map.Entry<String, Object>, String>() {
                            @Override
                            public String apply(Map.Entry<String, Object> input) {
                                return input.getKey() + ": '" + input.getValue() + '\'';
                            }
                        }).join(Joiner.on(", "))
                + '}';
    }
}
