package com.aware.cdm.factory;

import android.database.Cursor;
import com.aware.cdm.record.ContextRecord;
import com.aware.cdm.record.LocationsDataRecord;
import com.aware.providers.Locations_Provider;

/**
 * Created by Krzysztof Balon on 2015-02-23.
 */
public class LocationsDataRecordFactory implements ContextRecordFactory {
    
    @Override
    public ContextRecord createInstance(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(Locations_Provider.Locations_Data._ID);
        int timestampIndex = cursor.getColumnIndex(Locations_Provider.Locations_Data.TIMESTAMP);
        int deviceIdIndex = cursor.getColumnIndex(Locations_Provider.Locations_Data.DEVICE_ID);
        int latitudeIndex = cursor.getColumnIndex(Locations_Provider.Locations_Data.LATITUDE);
        int longitudeIndex = cursor.getColumnIndex(Locations_Provider.Locations_Data.LONGITUDE);
        int bearingIndex = cursor.getColumnIndex(Locations_Provider.Locations_Data.BEARING);
        int speedIndex = cursor.getColumnIndex(Locations_Provider.Locations_Data.SPEED);
        int altitudeIndex = cursor.getColumnIndex(Locations_Provider.Locations_Data.ALTITUDE);
        int providerIndex = cursor.getColumnIndex(Locations_Provider.Locations_Data.PROVIDER);
        int accuracyIndex = cursor.getColumnIndex(Locations_Provider.Locations_Data.ACCURACY);
        int labelIndex = cursor.getColumnIndex(Locations_Provider.Locations_Data.LABEL);

        int id = cursor.getInt(idIndex);
        long timestamp = cursor.getLong(timestampIndex);
        String deviceId = cursor.getString(deviceIdIndex);
        double latitude = cursor.getDouble(latitudeIndex);
        double longitude = cursor.getDouble(longitudeIndex);
        double bearing = cursor.getDouble(bearingIndex);
        double speed = cursor.getDouble(speedIndex);
        double altitude = cursor.getDouble(altitudeIndex);
        String provider = cursor.getString(providerIndex);
        double accuracy = cursor.getDouble(accuracyIndex);
        String label = cursor.getString(labelIndex);

        return new LocationsDataRecord(id, timestamp, deviceId, latitude, longitude, bearing, speed, altitude, provider, accuracy, label);
    }
}
