package com.aware.poirecommender.provider;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import com.aware.Aware;
import com.aware.Aware_Preferences;
import com.aware.context.property.GenericContextProperty;
import com.aware.poirecommender.openstreetmap.model.response.Element;
import com.aware.poirecommender.openstreetmap.model.response.Tags;

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

    public void addContext(Collection<GenericContextProperty> contextProperties) {
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

    public void addElement(Element element) {
        ContentValues values = new ContentValues();
        values.put(PoiRecommenderContract.Contexts.TIMESTAMP, System.currentTimeMillis());
        values.put(PoiRecommenderContract.Contexts.DEVICE_ID, Aware.getSetting(context, Aware_Preferences.DEVICE_ID));
        values.put(PoiRecommenderContract.Pois.POI_ID, element.getId());
        values.put(PoiRecommenderContract.Pois.TYPE, element.getType());
        values.put(PoiRecommenderContract.Pois.LATITUDE, element.getLat());
        values.put(PoiRecommenderContract.Pois.LONGITUDE, element.getLon());
        Tags tags = element.getTags();
        if (tags != null) {
            values.put(PoiRecommenderContract.Pois.NAME, tags.getName());
            values.put(PoiRecommenderContract.Pois.LAYER, tags.getLayer());
            values.put(PoiRecommenderContract.Pois.OPENING_HOURS, tags.getOpeningHours());
            values.put(PoiRecommenderContract.Pois.PHONE, tags.getPhone());
            values.put(PoiRecommenderContract.Pois.WEBSITE, tags.getWebsite());
            values.put(PoiRecommenderContract.Pois.OPERATOR, tags.getOperator());
            values.put(PoiRecommenderContract.Pois.AMENITY, tags.getAmenity());
            values.put(PoiRecommenderContract.Pois.TOURISM, tags.getTourism());
            values.put(PoiRecommenderContract.Pois.SHOP, tags.getShop());
            values.put(PoiRecommenderContract.Pois.CITY, tags.getCity());
            values.put(PoiRecommenderContract.Pois.COUNTRY, tags.getCountry());
            values.put(PoiRecommenderContract.Pois.STREET, tags.getStreet());
            values.put(PoiRecommenderContract.Pois.HOUSE_NUMBER, tags.getHouseNumber());
            values.put(PoiRecommenderContract.Pois.HOUSE_NAME, tags.getHouseName());
            values.put(PoiRecommenderContract.Pois.POST_CODE, tags.getPostCode());
        }
        contentResolver.insert(PoiRecommenderContract.Pois.CONTENT_URI, values);
    }
}
