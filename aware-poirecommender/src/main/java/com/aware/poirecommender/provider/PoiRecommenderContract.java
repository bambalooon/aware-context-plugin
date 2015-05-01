package com.aware.poirecommender.provider;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * Name: PoiRecommenderContract
 * Description: PoiRecommenderContract
 * Date: 2015-04-20
 * Created by BamBalooon
 */
public class PoiRecommenderContract {
    public static final String AUTHORITY = "com.aware.poirecommender.provider.poirecommender";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    public static final class Contexts {
        /**
         * Contexts table name
         */
        public static final String TABLE_NAME = "contexts";

        /**
         * Content URI for Contexts table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(PoiRecommenderContract.CONTENT_URI, TABLE_NAME);

        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/vnd.com.aware.poirecommender.provider.poirecommender_contexts";

        /**
         * The mime type of a single item.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/vnd.com.aware.poirecommender.provider.poirecommender_contexts";

        /**
         * The unique ID for a row.
         * <P>Type: INTEGER (Integer)</P>
         */
        public static final String _ID = "_id";

        /**
         * The timestamp column.
         * <P>Type: DOUBLE (Double)</P>
         */
        public static final String TIMESTAMP = "timestamp";
        /**
         * The device ID column.
         * <P>Type: STRING (String)</P>
         */
        public static final String DEVICE_ID = "device_id";

        /**
         * Columns with Aware tables Timestamps.
         */
        public static final String ACCELEROMETER_TIMESTAMP = "double_accel_tmstmp";
        public static final String APPLICATION_FOREGROUND_TIMESTAMP = "double_app_frgnd_tmstmp";
        public static final String APPLICATIONS_HISTORY_TIMESTAMP = "double_app_hist_tmstmp";
        public static final String APPLICATIONS_NOTIFICATION_TIMESTAMP = "double_app_notif_tmstmp";
        public static final String APPLICATION_CRASH_TIMESTAMP = "double_app_crsh_tmstmp";
        public static final String BAROMETER_TIMESTAMP = "double_bar_tmstmp";
        public static final String BATTERY_TIMESTAMP = "double_bat_tmstmp";
        public static final String BATTERY_CHARGE_TIMESTAMP = "double_bat_chrg_tmstmp";
        public static final String BATTERY_DISCHARGE_TIMESTAMP = "double_bat_dchrg_tmstmp";
        public static final String BLUETOOTH_TIMESTAMP = "double_bltth_tmstmp";
        public static final String COMMUNICATION_CALL_TIMESTAMP = "double_comm_call_tmstmp";
        public static final String COMMUNICATION_MESSAGE_TIMESTAMP = "double_comm_msg_tmstmp";
        public static final String ESMS_TIMESTAMP = "double_esms_tmstmp";
        public static final String GRAVITY_TIMESTAMP = "double_grvt_tmstmp";
        public static final String GYROSCOPE_TIMESTAMP = "double_grscp_tmstmp";
        public static final String INSTALLATION_TIMESTAMP = "double_instll_tmstmp";
        public static final String LIGHT_TIMESTAMP = "double_lght_tmstmp";
        public static final String LINEAR_ACCELEROMETER_TIMESTAMP = "double_lnr_accel_tmstmp";
        public static final String LOCATION_TIMESTAMP = "double_loc_tmstmp";
        public static final String MAGNETOMETER_TIMESTAMP = "double_mgnt_tmstmp";
        public static final String MQTT_MESSAGE_TIMESTAMP = "double_mqtt_msg_tmstmp";
        public static final String MQTT_SUBSCRIPTION_TIMESTAMP = "double_mqtt_subs_tmstmp";
        public static final String NETWORK_TIMESTAMP = "double_ntwrk_tmstmp";
        public static final String NETWORK_TRAFFIC_TIMESTAMP = "double_ntwrk_trffc_tmstmp";
        public static final String PLUGIN_GOOGLE_ACTIVITY_RECOGNITION_TIMESTAMP = "double_ggl_actvt_rcgntn_tmstmp";
        public static final String PLUGIN_OPENWEATHER_TIMESTAMP = "double_openwthr_tmstmp";
        public static final String PROCESSOR_TIMESTAMP = "double_prcssr_tmstmp";
        public static final String PROXIMITY_TIMESTAMP = "double_prxmt_tmstmp";
        public static final String ROTATION_TIMESTAMP = "double_rot_tmstmp";
        public static final String SCREEN_TIMESTAMP = "double_scrn_tmstmp";
        public static final String TELEPHONY_TIMESTAMP = "double_tel_tmstmp";
        public static final String TELEPHONY_CDMA_TIMESTAMP = "double_tel_cdma_tmstmp";
        public static final String TELEPHONY_GSM_TIMESTAMP = "double_tel_gsm_tmstmp";
        public static final String TELEPHONY_GSM_NEIGHBOR_TIMESTAMP = "double_tel_gsm_nghbr_tmstmp";
        public static final String TEMPERATURE_TIMESTAMP = "double_temp_tmstmp";
        public static final String TIMEZONE_TIMESTAMP = "double_tmzn_tmstmp";
        public static final String WIFI_TIMESTAMP = "double_wf_tmstmp";

        /**
         * A projection of all columns in Contexts table.
         */
        public static final String[] PROJECTION_ALL = {
                _ID, TIMESTAMP, DEVICE_ID,
                ACCELEROMETER_TIMESTAMP,
                APPLICATION_FOREGROUND_TIMESTAMP, APPLICATIONS_HISTORY_TIMESTAMP,
                APPLICATIONS_NOTIFICATION_TIMESTAMP, APPLICATION_CRASH_TIMESTAMP,
                BAROMETER_TIMESTAMP,
                BATTERY_TIMESTAMP, BATTERY_CHARGE_TIMESTAMP, BATTERY_DISCHARGE_TIMESTAMP,
                BLUETOOTH_TIMESTAMP,
                COMMUNICATION_CALL_TIMESTAMP, COMMUNICATION_MESSAGE_TIMESTAMP,
                ESMS_TIMESTAMP,
                GRAVITY_TIMESTAMP,
                GYROSCOPE_TIMESTAMP,
                INSTALLATION_TIMESTAMP,
                LIGHT_TIMESTAMP,
                LINEAR_ACCELEROMETER_TIMESTAMP,
                LOCATION_TIMESTAMP,
                MAGNETOMETER_TIMESTAMP,
                MQTT_MESSAGE_TIMESTAMP, MQTT_SUBSCRIPTION_TIMESTAMP,
                NETWORK_TIMESTAMP,
                NETWORK_TRAFFIC_TIMESTAMP,
                PLUGIN_GOOGLE_ACTIVITY_RECOGNITION_TIMESTAMP,
                PLUGIN_OPENWEATHER_TIMESTAMP,
                PROCESSOR_TIMESTAMP,
                PROXIMITY_TIMESTAMP,
                ROTATION_TIMESTAMP,
                SCREEN_TIMESTAMP,
                TELEPHONY_TIMESTAMP, TELEPHONY_CDMA_TIMESTAMP, TELEPHONY_GSM_TIMESTAMP, TELEPHONY_GSM_NEIGHBOR_TIMESTAMP,
                TEMPERATURE_TIMESTAMP,
                TIMEZONE_TIMESTAMP,
                WIFI_TIMESTAMP
        };

        /**
         * The default sort order.
         */
        public static final String SORT_ORDER_DEFAULT = _ID + " ASC";
    }

    public static final class Pois {
        /**
         * POIs table name
         */
        public static final String TABLE_NAME = "pois";

        /**
         * Content URI for POIs table.
         */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(PoiRecommenderContract.CONTENT_URI, TABLE_NAME);

        /**
         * The mime type of a directory of items.
         */
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE
                + "/vnd.com.aware.poirecommender.provider.poirecommender_pois";

        /**
         * The mime type of a single item.
         */
        public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE
                + "/vnd.com.aware.poirecommender.provider.poirecommender_pois";

        /**
         * The unique ID for a row.
         * <P>Type: LONG (Long)</P>
         */
        public static final String _ID = "_id";

        /**
         * The timestamp column.
         * <P>Type: LONG (Long)</P>
         */
        public static final String TIMESTAMP = "timestamp";
        /**
         * The device ID column.
         * <P>Type: STRING (String)</P>
         */
        public static final String DEVICE_ID = "device_id";

        /**
         * Unique ID of POI
         * <P> Type: LONG (Long)</P>
         */
        public static final String POI_ID = "double_poi_id";

        /**
         * Type of element
         * <P> Type: STRING (String)</P>
         */
        public static final String TYPE = "type";

        /**
         * Latitude o element
         * <P> Type: DOUBLE (Double)</P>
         */
        public static final String LATITUDE = "double_lat";

        /**
         * Longitude o element
         * <P> Type: DOUBLE (Double)</P>
         */
        public static final String LONGITUDE = "double_lon";

        public static final String NAME = "name";

        public static final String LAYER = "layer";
        public static final String OPENING_HOURS = "opn_hrs";
        public static final String PHONE = "phn";
        public static final String WEBSITE = "webst";
        public static final String OPERATOR = "oprtr";

        public static final String AMENITY = "amnt";
        public static final String TOURISM = "trsm";
        public static final String SHOP = "shop";

        public static final String CITY = "city";
        public static final String COUNTRY = "cntr";
        public static final String STREET = "strt";
        public static final String HOUSE_NUMBER = "hs_nmbr";
        public static final String HOUSE_NAME = "hs_nm";
        public static final String POST_CODE = "pst_cd";

        /**
         * A projection of all columns in POIs table.
         */
        public static final String[] PROJECTION_ALL = {
                _ID, TIMESTAMP, DEVICE_ID,
                POI_ID, TYPE, LATITUDE, LONGITUDE,
                NAME,
                LAYER, OPENING_HOURS, PHONE, WEBSITE, OPERATOR,
                AMENITY, TOURISM, SHOP,
                CITY, COUNTRY, STREET, HOUSE_NUMBER, HOUSE_NAME, POST_CODE
        };

        /**
         * The default sort order.
         */
        public static final String SORT_ORDER_DEFAULT = _ID + " ASC";
    }
}
