package com.aware.context.broadcast.factory;

import android.database.Cursor;
import com.aware.context.factory.ContextPropertyFactory;
import com.aware.context.broadcast.property.ContextPropertyParcel;
import com.aware.context.broadcast.property.GoogleActivityRecognitionProperty;
import com.aware.plugin.google.activity_recognition.Google_AR_Provider;

/**
 * Created by Krzysztof Balon on 2015-02-24.
 */
public class GoogleActivityRecognitionPropertyFactory implements ContextPropertyFactory<ContextPropertyParcel> {

    @Override
    public ContextPropertyParcel createInstance(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(Google_AR_Provider.Google_Activity_Recognition_Data._ID);
        int timestampIndex = cursor.getColumnIndex(Google_AR_Provider.Google_Activity_Recognition_Data.TIMESTAMP);
        int deviceIdIndex = cursor.getColumnIndex(Google_AR_Provider.Google_Activity_Recognition_Data.DEVICE_ID);
        int activityNameIndex = cursor.getColumnIndex(Google_AR_Provider.Google_Activity_Recognition_Data.ACTIVITY_NAME);
        int activityTypeIndex = cursor.getColumnIndex(Google_AR_Provider.Google_Activity_Recognition_Data.ACTIVITY_TYPE);
        int confidenceIndex = cursor.getColumnIndex(Google_AR_Provider.Google_Activity_Recognition_Data.CONFIDENCE);
        int activitiesIndex = cursor.getColumnIndex(Google_AR_Provider.Google_Activity_Recognition_Data.ACTIVITIES);

        int id = cursor.getInt(idIndex);
        long timestamp = cursor.getLong(timestampIndex);
        String deviceId = cursor.getString(deviceIdIndex);
        String activityName = cursor.getString(activityNameIndex);
        int activityType = cursor.getInt(activityTypeIndex);
        int confidence = cursor.getInt(confidenceIndex);
        String activities = cursor.getString(activitiesIndex);

        return new GoogleActivityRecognitionProperty(id, timestamp, deviceId, activityName, activityType, confidence, activities);
    }
}
