package com.aware.cdm.processor;

import com.aware.cdm.record.ContextRecord;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 */
public interface ContextRecordProcessor {
    void process(ContextRecord contextRecord);
}
