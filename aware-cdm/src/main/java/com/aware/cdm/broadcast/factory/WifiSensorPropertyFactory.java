package com.aware.cdm.broadcast.factory;

import android.database.Cursor;
import com.aware.cdm.factory.ContextPropertyFactory;
import com.aware.cdm.broadcast.property.ContextPropertyParcel;
import com.aware.cdm.broadcast.property.WifiSensorProperty;
import com.aware.providers.WiFi_Provider;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 */
public class WifiSensorPropertyFactory implements ContextPropertyFactory<ContextPropertyParcel> {

    @Override
    public ContextPropertyParcel createInstance(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Sensor._ID);
        int timestampIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Sensor.TIMESTAMP);
        int deviceIdIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Sensor.DEVICE_ID);
        int macAddressIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Sensor.MAC_ADDRESS);
        int ssidIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Sensor.SSID);
        int bssidIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Sensor.BSSID);

        int id = cursor.getInt(idIndex);
        long timestamp = cursor.getLong(timestampIndex);
        String deviceId = cursor.getString(deviceIdIndex);
        String macAddress = cursor.getString(macAddressIndex);
        String ssid = cursor.getString(ssidIndex);
        String bssid = cursor.getString(bssidIndex);

        return new WifiSensorProperty(id, timestamp, deviceId, macAddress, ssid, bssid);
    }
}
