package com.aware.cdm.broadcast.property;

import android.os.Parcel;

/**
 * Created by Krzysztof Balon on 2015-02-26.
 */
public class OpenWeatherProperty implements ContextPropertyParcel {
    public static final Creator<OpenWeatherProperty> CREATOR = new Creator<OpenWeatherProperty>() {
        @Override
        public OpenWeatherProperty createFromParcel(Parcel parcel) {
            return new OpenWeatherProperty(parcel);
        }

        @Override
        public OpenWeatherProperty[] newArray(int size) {
            return new OpenWeatherProperty[size];
        }
    };
    
    private final int id;
    private final long timestamp;
    private final String deviceId;
    private final String city;
    private final double temperature;
    private final double temperatureMax;
    private final double temperatureMin;
    private final String units;
    private final double humidity;
    private final double pressure;
    private final double windSpeed;
    private final double windDegrees;
    private final double cloudiness;
    private final int weatherIconId;
    private final String weatherDescription;

    public OpenWeatherProperty(int id, long timestamp, String deviceId, String city, double temperature, double temperatureMax, double temperatureMin, String units, double humidity, double pressure, double windSpeed, double windDegrees, double cloudiness, int weatherIconId, String weatherDescription) {
        this.id = id;
        this.timestamp = timestamp;
        this.deviceId = deviceId;
        this.city = city;
        this.temperature = temperature;
        this.temperatureMax = temperatureMax;
        this.temperatureMin = temperatureMin;
        this.units = units;
        this.humidity = humidity;
        this.pressure = pressure;
        this.windSpeed = windSpeed;
        this.windDegrees = windDegrees;
        this.cloudiness = cloudiness;
        this.weatherIconId = weatherIconId;
        this.weatherDescription = weatherDescription;
    }

    public OpenWeatherProperty(Parcel in) {
        this.id = in.readInt();
        this.timestamp = in.readLong();
        this.deviceId = in.readString();
        this.city = in.readString();
        this.temperature = in.readDouble();
        this.temperatureMax = in.readDouble();
        this.temperatureMin = in.readDouble();
        this.units = in.readString();
        this.humidity = in.readDouble();
        this.pressure = in.readDouble();
        this.windSpeed = in.readDouble();
        this.windDegrees = in.readDouble();
        this.cloudiness = in.readDouble();
        this.weatherIconId = in.readInt();
        this.weatherDescription = in.readString();
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
        out.writeString(city);
        out.writeDouble(temperature);
        out.writeDouble(temperatureMax);
        out.writeDouble(temperatureMin);
        out.writeString(units);
        out.writeDouble(humidity);
        out.writeDouble(pressure);
        out.writeDouble(windSpeed);
        out.writeDouble(windDegrees);
        out.writeDouble(cloudiness);
        out.writeInt(weatherIconId);
        out.writeString(weatherDescription);
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

    public String getCity() {
        return city;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTemperatureMax() {
        return temperatureMax;
    }

    public double getTemperatureMin() {
        return temperatureMin;
    }

    public String getUnits() {
        return units;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public double getWindDegrees() {
        return windDegrees;
    }

    public double getCloudiness() {
        return cloudiness;
    }

    public int getWeatherIconId() {
        return weatherIconId;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    @Override
    public String toString() {
        return "OpenWeatherDataRecord{" +
                "id=" + id +
                ", timestamp=" + timestamp +
                ", deviceId='" + deviceId + '\'' +
                ", city='" + city + '\'' +
                ", temperature=" + temperature +
                ", temperatureMax=" + temperatureMax +
                ", temperatureMin=" + temperatureMin +
                ", units='" + units + '\'' +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", windSpeed=" + windSpeed +
                ", windDegrees=" + windDegrees +
                ", cloudiness=" + cloudiness +
                ", weatherIconId=" + weatherIconId +
                ", weatherDescription='" + weatherDescription + '\'' +
                '}';
    }
}
