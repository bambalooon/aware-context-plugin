package com.aware.cdm.factory;

import android.database.Cursor;
import com.aware.cdm.record.ContextRecord;
import com.aware.cdm.record.OpenWeatherDataRecord;
import com.aware.plugin.openweather.Provider;

/**
 * Created by Krzysztof Balon on 2015-02-26.
 */
public class OpenWeatherDataRecordFactory implements ContextRecordFactory {

    @Override
    public ContextRecord createInstance(Cursor cursor) {
        int idIndex = cursor.getColumnIndex(Provider.OpenWeather_Data._ID);
        int timestampIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.TIMESTAMP);
        int deviceIdIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.DEVICE_ID);
        int cityIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.CITY);
        int temperatureIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.TEMPERATURE);
        int temperatureMaxIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.TEMPERATURE_MAX);
        int temperatureMinIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.TEMPERATURE_MIN);
        int unitsIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.UNITS);
        int humidityIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.HUMIDITY);
        int pressureIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.PRESSURE);
        int windSpeedIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.WIND_SPEED);
        int windDegreesIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.WIND_DEGREES);
        int cloudinessIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.CLOUDINESS);
        int weatherIconIdIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.WEATHER_ICON_ID);
        int weatherDescriptionIndex = cursor.getColumnIndex(Provider.OpenWeather_Data.WEATHER_DESCRIPTION);

        int id = cursor.getInt(idIndex);
        long timestamp = cursor.getLong(timestampIndex);
        String deviceId = cursor.getString(deviceIdIndex);
        String city = cursor.getString(cityIndex);
        double temperature = cursor.getDouble(temperatureIndex);
        double temperatureMax = cursor.getDouble(temperatureMaxIndex);
        double temperatureMin = cursor.getDouble(temperatureMinIndex);
        String units = cursor.getString(unitsIndex);
        double humidity = cursor.getDouble(humidityIndex);
        double pressure = cursor.getDouble(pressureIndex);
        double windSpeed = cursor.getDouble(windSpeedIndex);
        double windDegrees = cursor.getDouble(windDegreesIndex);
        double cloudiness = cursor.getDouble(cloudinessIndex);
        int weatherIconId = cursor.getInt(weatherIconIdIndex);
        String weatherDescription = cursor.getString(weatherDescriptionIndex);

        return new OpenWeatherDataRecord(id, timestamp, deviceId, city, temperature, temperatureMax, temperatureMin, units, humidity,
                pressure, windSpeed, windDegrees, cloudiness, weatherIconId, weatherDescription);
    }
}
