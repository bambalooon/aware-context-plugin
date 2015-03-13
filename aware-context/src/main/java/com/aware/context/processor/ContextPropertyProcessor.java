package com.aware.context.processor;

import com.aware.context.property.ContextProperty;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 * @param <CP> processed ContextProperty type
 */
public interface ContextPropertyProcessor<CP extends ContextProperty> {
    void process(CP contextProperty);
}
