package com.aware.plugin.poirecommender.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import com.aware.context.property.GenericContextProperty;

import java.util.Collection;
import java.util.Date;

/**
 * Name: PoiRecommenderData
 * Description: PoiRecommenderData
 * Date: 2015-04-25
 * Created by BamBalooon
 */
public class PoiRecommenderData {
    public static final String CONTEXT_PROPERTY_ID_ATTRIBUTE = "_id";
    private final ContentResolver contentResolver;

    public PoiRecommenderData(ContentResolver contentResolver) {
        this.contentResolver = contentResolver;
    }

    public void setContext(Collection<GenericContextProperty> contextProperties) {
        ContentValues values = new ContentValues();
        values.put(PoiRecommenderContract.Contexts.TIMESTAMP, new Date().getTime());
        for (GenericContextProperty contextProperty : contextProperties) {
            values.put(
                    contextProperty.getId(),
                    (int) contextProperty.getAttributes().get(CONTEXT_PROPERTY_ID_ATTRIBUTE));
        }
        contentResolver.insert(PoiRecommenderContract.Contexts.CONTENT_URI, values);
    }
}
