package com.aware.context.broadcast.factory;

import android.database.Cursor;
import com.aware.context.factory.ContextPropertyFactory;
import com.aware.context.broadcast.property.ContextPropertyParcel;
import com.aware.context.broadcast.property.WifiDataProperty;
import com.aware.providers.WiFi_Provider;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 */
public class WifiDataPropertyFactory implements ContextPropertyFactory<ContextPropertyParcel> {

    @Override
    public ContextPropertyParcel createInstance(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Data._ID);
        int timestampIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Data.TIMESTAMP);
        int bssidIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Data.BSSID);
        int ssidIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Data.SSID);
        int securityIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Data.SECURITY);
        int frequencyIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Data.FREQUENCY);
        int rssiIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Data.RSSI);
        int labelIndex = cursor.getColumnIndex(WiFi_Provider.WiFi_Data.LABEL);

        int id = cursor.getInt(idIndex);
        long timestamp = cursor.getLong(timestampIndex);
        String bssid = cursor.getString(bssidIndex);
        String ssid = cursor.getString(ssidIndex);
        String security = cursor.getString(securityIndex);
        int frequency = cursor.getInt(frequencyIndex);
        int rssi = cursor.getInt(rssiIndex);
        String label = cursor.getString(labelIndex);

        return new WifiDataProperty(id, timestamp, bssid, ssid, security, frequency, rssi, label);
    }
}
