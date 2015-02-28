package com.aware.cdm.record;

import android.os.Parcel;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 */
public class WifiSensorRecord implements ContextRecord {
    public static final Creator<WifiSensorRecord> CREATOR = new Creator<WifiSensorRecord>() {
        @Override
        public WifiSensorRecord createFromParcel(Parcel parcel) {
            return new WifiSensorRecord(parcel);
        }

        @Override
        public WifiSensorRecord[] newArray(int size) {
            return new WifiSensorRecord[size];
        }
    };

    private final int id;
    private final long timestamp;
    private final String deviceId;
    private final String macAddress;
    private final String ssid;
    private final String bssid;

    public WifiSensorRecord(int id, long timestamp, String deviceId, String macAddress, String ssid, String bssid) {
        this.id = id;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.macAddress = macAddress;
        this.ssid = ssid;
        this.bssid = bssid;
    }

    public WifiSensorRecord(Parcel in) {
        this.id = in.readInt();
        this.timestamp = in.readLong();
        this.deviceId = in.readString();
        this.macAddress = in.readString();
        this.ssid = in.readString();
        this.bssid = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flag) {
        out.writeInt(id);
        out.writeLong(timestamp);
        out.writeString(deviceId);
        out.writeString(macAddress);
        out.writeString(ssid);
        out.writeString(bssid);
    }

    public int getId() {
        return id;
    }

    public double getTimestamp() {
        return timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getSsid() {
        return ssid;
    }

    public String getBssid() {
        return bssid;
    }

    @Override
    public String toString() {
        return "WifiSensorRecord{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", deviceId='" + deviceId + '\'' +
                ", macAddress='" + macAddress + '\'' +
                ", ssid='" + ssid + '\'' +
                ", bssid='" + bssid + '\'' +
                '}';
    }
}
