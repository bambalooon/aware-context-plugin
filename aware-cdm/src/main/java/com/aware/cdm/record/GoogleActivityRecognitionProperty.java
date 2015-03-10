package com.aware.cdm.record;

import android.os.Parcel;

/**
 * Created by Krzysztof Balon on 2015-02-24.
 */
public class GoogleActivityRecognitionProperty implements ContextProperty {
    public static final Creator<GoogleActivityRecognitionProperty> CREATOR = new Creator<GoogleActivityRecognitionProperty>() {
        @Override
        public GoogleActivityRecognitionProperty createFromParcel(Parcel parcel) {
            return new GoogleActivityRecognitionProperty(parcel);
        }

        @Override
        public GoogleActivityRecognitionProperty[] newArray(int size) {
            return new GoogleActivityRecognitionProperty[size];
        }
    };
    
    private final int id;
    private final long timestamp;
    private final String deviceId;
    private final String activityName;
    private final int activityType;
    private final int confidence;
    private final String activities;

    public GoogleActivityRecognitionProperty(int id, long timestamp, String deviceId, String activityName, int activityType, int confidence, String activities) {
        this.id = id;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.activityName = activityName;
        this.activityType = activityType;
        this.confidence = confidence;
        this.activities = activities;
    }

    public GoogleActivityRecognitionProperty(Parcel in) {
        this.id = in.readInt();
        this.timestamp = in.readLong();
        this.deviceId = in.readString();
        this.activityName = in.readString();
        this.activityType = in.readInt();
        this.confidence = in.readInt();
        this.activities = in.readString();
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
        out.writeString(activityName);
        out.writeInt(activityType);
        out.writeInt(confidence);
        out.writeString(activities);
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

    public String getActivityName() {
        return activityName;
    }

    public int getActivityType() {
        return activityType;
    }

    public int getConfidence() {
        return confidence;
    }

    public String getActivities() {
        return activities;
    }

    @Override
    public String toString() {
        return "GoogleActivityRecognitionDataRecord{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", deviceId='" + deviceId + '\'' +
                ", activityName='" + activityName + '\'' +
                ", activityType=" + activityType +
                ", confidence=" + confidence +
                ", activities='" + activities + '\'' +
                '}';
    }
}
