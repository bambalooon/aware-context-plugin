package com.aware.cdm.factory;

import android.database.Cursor;
import com.aware.cdm.record.ContextRecord;
import com.aware.cdm.record.WifiDataRecord;
import com.aware.providers.WiFi_Provider;

/**
 * Created by Krzysztof Balon on 2015-02-22.
 */
public class WifiDataRecordFactory implements ContextRecordFactory {

    @Override
    public ContextRecord createInstance(Cursor cursor) {
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

        return new WifiDataRecord(id, timestamp, bssid, ssid, security, frequency, rssi, label);
    }
}
