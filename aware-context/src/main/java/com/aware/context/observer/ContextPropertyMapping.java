package com.aware.context.observer;

import android.net.Uri;
import com.aware.context.factory.ContextPropertyFactory;
import com.aware.context.property.ContextProperty;

import java.util.Map;
import java.util.Set;

/**
 * Name: ContextPropertyMapping
 * Description: ${NAME}ContextPropertyMapping
 * Date: 2015-02-21
 * Created by BamBalooon
 * @param <CP> mapped ContextProperty type
 */
public class ContextPropertyMapping<CP extends ContextProperty> {
    private final Map<Uri, ContextPropertyFactory<CP>> map;

    public ContextPropertyMapping(Map<Uri, ContextPropertyFactory<CP>> map) {
        this.map = map;
    }

    public ContextPropertyFactory<CP> getContextPropertyFactory(Uri uri) {
        return map.get(uri);
    }

    public Set<Uri> getContextPropertyUriList() {
        return map.keySet();
    }
}
