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
         * <P>Type: LONG (Long)</P>
         */
        public static final String TIMESTAMP = "timestamp";
        /**
         * The device ID column.
         * <P>Type: STRING (String)</P>
         */
        public static final String DEVICE_ID = "device_id";

        /**
         * Columns with Aware tables IDs.
         */
        public static final String ACCELEROMETER_ID = "accel_id";
        public static final String APPLICATION_FOREGROUND_ID = "app_frgnd_id";
        public static final String APPLICATIONS_HISTORY_ID = "app_hist_id";
        public static final String APPLICATIONS_NOTIFICATION_ID = "app_notif_id";
        public static final String APPLICATION_CRASH_ID = "app_crsh_id";
        public static final String BAROMETER_ID = "bar_id";
        public static final String BATTERY_ID = "bat_id";
        public static final String BATTERY_CHARGE_ID = "bat_chrg_id";
        public static final String BATTERY_DISCHARGE_ID = "bat_dchrg_id";
        public static final String BLUETOOTH_ID = "bltth_id";
        public static final String COMMUNICATION_CALL_ID = "comm_call_id";
        public static final String COMMUNICATION_MESSAGE_ID = "comm_msg_id";
        public static final String ESMS_ID = "esms_id";
        public static final String GRAVITY_ID = "grvt_id";
        public static final String GYROSCOPE_ID = "grscp_id";
        public static final String INSTALLATION_ID = "instll_id";
        public static final String LIGHT_ID = "lght_id";
        public static final String LINEAR_ACCELEROMETER_ID = "lnr_accel_id";
        public static final String LOCATION_ID = "loc_id";
        public static final String MAGNETOMETER_ID = "mgnt_id";
        public static final String MQTT_MESSAGE_ID = "mqtt_msg_id";
        public static final String MQTT_SUBSCRIPTION_ID = "mqtt_subs_id";
        public static final String NETWORK_ID = "ntwrk_id";
        public static final String NETWORK_TRAFFIC_ID = "ntwrk_trffc_id";
        public static final String PLUGIN_GOOGLE_ACTIVITY_RECOGNITION_ID = "ggl_actvt_rcgntn_id";
        public static final String PLUGIN_OPENWEATHER_ID = "openwthr_id";
        public static final String PROCESSOR_ID = "prcssr_id";
        public static final String PROXIMITY_ID = "prxmt_id";
        public static final String ROTATION_ID = "rot_id";
        public static final String SCREEN_ID = "scrn_id";
        public static final String TELEPHONY_ID = "tel_id";
        public static final String TELEPHONY_CDMA_ID = "tel_cdma_id";
        public static final String TELEPHONY_GSM_ID = "tel_gsm_id";
        public static final String TELEPHONY_GSM_NEIGHBOR_ID = "tel_gsm_nghbr_id";
        public static final String TEMPERATURE_ID = "temp_id";
        public static final String TIMEZONE_ID = "tmzn_id";
        public static final String WIFI_ID = "wf_id";

        /**
         * A projection of all columns in ContextProperties table.
         */
        public static final String[] PROJECTION_ALL = {
                _ID, TIMESTAMP, DEVICE_ID,
                ACCELEROMETER_ID,
                APPLICATION_FOREGROUND_ID, APPLICATIONS_HISTORY_ID, APPLICATIONS_NOTIFICATION_ID, APPLICATION_CRASH_ID,
                BAROMETER_ID,
                BATTERY_ID, BATTERY_CHARGE_ID, BATTERY_DISCHARGE_ID,
                BLUETOOTH_ID,
                COMMUNICATION_CALL_ID, COMMUNICATION_MESSAGE_ID,
                ESMS_ID,
                GRAVITY_ID,
                GYROSCOPE_ID,
                INSTALLATION_ID,
                LIGHT_ID,
                LINEAR_ACCELEROMETER_ID,
                LOCATION_ID,
                MAGNETOMETER_ID,
                MQTT_MESSAGE_ID, MQTT_SUBSCRIPTION_ID,
                NETWORK_ID,
                NETWORK_TRAFFIC_ID,
                PLUGIN_GOOGLE_ACTIVITY_RECOGNITION_ID,
                PLUGIN_OPENWEATHER_ID,
                PROCESSOR_ID,
                PROXIMITY_ID,
                ROTATION_ID,
                SCREEN_ID,
                TELEPHONY_ID, TELEPHONY_CDMA_ID, TELEPHONY_GSM_ID, TELEPHONY_GSM_NEIGHBOR_ID,
                TEMPERATURE_ID,
                TIMEZONE_ID,
                WIFI_ID
        };

        /**
         * The default sort order.
         */
        public static final String SORT_ORDER_DEFAULT = _ID + " ASC";
    }
}
