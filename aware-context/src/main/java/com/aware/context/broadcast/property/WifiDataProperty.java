package com.aware.context.broadcast.property;

import android.os.Parcel;

/**
 * Created by Krzysztof Balon on 2015-02-21.
 */
public class WifiDataProperty implements ContextPropertyParcel {
    public static final Creator<WifiDataProperty> CREATOR = new Creator<WifiDataProperty>() {
        @Override
        public WifiDataProperty createFromParcel(Parcel parcel) {
            return new WifiDataProperty(parcel);
        }

        @Override
        public WifiDataProperty[] newArray(int size) {
            return new WifiDataProperty[size];
        }
    };

    private final int id;
    private final long timestamp;
    private final String bssid;
    private final String ssid;
    private final String security;
    private final int frequency;
    private final int rssi;
    private final String label;

    public WifiDataProperty(int id, long timestamp, String bssid, String ssid, String security, int frequency, int rssi, String label) {
        this.id = id;
        this.timestamp = timestamp;
        this.bssid = bssid;
        this.ssid = ssid;
        this.security = security;
        this.frequency = frequency;
        this.rssi = rssi;
        this.label = label;
    }

    private WifiDataProperty(Parcel in) {
        this.id = in.readInt();
        this.timestamp = in.readLong();
        this.bssid = in.readString();
        this.ssid = in.readString();
        this.security = in.readString();
        this.frequency = in.readInt();
        this.rssi = in.readInt();
        this.label = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(id);
        out.writeLong(timestamp);
        out.writeString(bssid);
        out.writeString(ssid);
        out.writeString(security);
        out.writeInt(frequency);
        out.writeInt(rssi);
        out.writeString(label);
    }

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getBssid() {
        return bssid;
    }

    public String getSsid() {
        return ssid;
    }

    public String getSecurity() {
        return security;
    }

    public int getFrequency() {
        return frequency;
    }

    public int getRssi() {
        return rssi;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "WifiDataRecord{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", bssid='" + bssid + '\'' +
                ", ssid='" + ssid + '\'' +
                ", security='" + security + '\'' +
                ", frequency=" + frequency +
                ", rssi=" + rssi +
                ", label='" + label + '\'' +
                '}';
    }
}
