package com.aware.cdm.processor;

import com.aware.cdm.property.ContextProperty;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 * @param <CP> processed ContextProperty type
 */
public interface ContextPropertyProcessor<CP extends ContextProperty> {
    void process(CP contextProperty);
}
