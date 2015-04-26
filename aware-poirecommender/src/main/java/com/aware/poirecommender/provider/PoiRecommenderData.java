package com.aware.poirecommender.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.context.property.GenericContextProperty;

import java.util.Collection;

/**
 * Name: PoiRecommenderData
 * Description: PoiRecommenderData
 * Date: 2015-04-25
 * Created by BamBalooon
 */
public class PoiRecommenderData {
    public static final String CONTEXT_PROPERTY_ID_ATTRIBUTE = "_id";
    private final Context context;
    private final ContentResolver contentResolver;

    public PoiRecommenderData(Context context) {
        this.context = context;
        this.contentResolver = context.getContentResolver();
    }

    public void setContext(Collection<GenericContextProperty> contextProperties) {
        ContentValues values = new ContentValues();
        values.put(PoiRecommenderContract.Contexts.TIMESTAMP, System.currentTimeMillis());
        values.put(PoiRecommenderContract.Contexts.DEVICE_ID, Aware.getSetting(context, Aware_Preferences.DEVICE_ID));
        for (GenericContextProperty contextProperty : contextProperties) {
            Double contextPropertyIdAttribute = (Double) contextProperty.getAttributes()
                    .get(CONTEXT_PROPERTY_ID_ATTRIBUTE);
            values.put(contextProperty.getId(), contextPropertyIdAttribute.intValue());
        }
        contentResolver.insert(PoiRecommenderContract.Contexts.CONTENT_URI, values);
    }
}
