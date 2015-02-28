package com.aware.cdm.record;

import android.os.Parcel;

/**
 * Created by Krzysztof Balon on 2015-02-23.
 */
public class LocationsDataRecord implements ContextRecord {
    public static final Creator<LocationsDataRecord> CREATOR = new Creator<LocationsDataRecord>() {
        @Override
        public LocationsDataRecord createFromParcel(Parcel parcel) {
            return new LocationsDataRecord(parcel);
        }

        @Override
        public LocationsDataRecord[] newArray(int size) {
            return new LocationsDataRecord[size];
        }
    };

    private final int id;
    private final long timestamp;
    private final String deviceId;
    private final double latitude;
    private final double longitude;
    private final double bearing;
    private final double speed;
    private final double altitude;
    private final String provider;
    private final double accuracy;
    private final String label;

    public LocationsDataRecord(int id, long timestamp, String deviceId, double latitude, double longitude, double bearing, double speed, double altitude, String provider, double accuracy, String label) {
        this.id = id;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.bearing = bearing;
        this.speed = speed;
        this.altitude = altitude;
        this.provider = provider;
        this.accuracy = accuracy;
        this.label = label;
    }

    public LocationsDataRecord(Parcel in) {
        this.id = in.readInt();
        this.timestamp = in.readLong();
        this.deviceId = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.bearing = in.readDouble();
        this.speed = in.readDouble();
        this.altitude = in.readDouble();
        this.provider = in.readString();
        this.accuracy = in.readDouble();
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
        out.writeString(deviceId);
        out.writeDouble(latitude);
        out.writeDouble(longitude);
        out.writeDouble(bearing);
        out.writeDouble(speed);
        out.writeDouble(altitude);
        out.writeString(provider);
        out.writeDouble(accuracy);
        out.writeString(label);
    }

    public int getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getBearing() {
        return bearing;
    }

    public double getSpeed() {
        return speed;
    }

    public double getAltitude() {
        return altitude;
    }

    public String getProvider() {
        return provider;
    }

    public double getAccuracy() {
        return accuracy;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String toString() {
        return "LocationsDataRecord{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", deviceId='" + deviceId + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", bearing=" + bearing +
                ", speed=" + speed +
                ", altitude=" + altitude +
                ", provider='" + provider + '\'' +
                ", accuracy=" + accuracy +
                ", label='" + label + '\'' +
                '}';
    }
}
