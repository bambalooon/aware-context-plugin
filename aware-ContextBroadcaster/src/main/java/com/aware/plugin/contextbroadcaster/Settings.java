package com.aware.plugin.contextbroadcaster;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import com.aware.Aware;

/**
 * Created by Krzysztof Balon on 2015-02-23.
 */
public class Settings extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    /**
     * State of Context Broadcaster plugin
     */
    public static final String STATUS_PLUGIN_CONTEXT_BROADCASTER = "status_plugin_context_broadcaster";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        syncSettings();
    }

    private void syncSettings() {
        CheckBoxPreference check = (CheckBoxPreference) findPreference(STATUS_PLUGIN_CONTEXT_BROADCASTER);
        check.setChecked(Aware.getSetting(getApplicationContext(), STATUS_PLUGIN_CONTEXT_BROADCASTER).equals("true"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncSettings();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference.getKey().equals(STATUS_PLUGIN_CONTEXT_BROADCASTER)) {
            boolean isActive = sharedPreferences.getBoolean(key, false);
            Aware.setSetting(getApplicationContext(), key, isActive);

            if (isActive) {
                Aware.startPlugin(getApplicationContext(), getPackageName());
            } else {
                Aware.stopPlugin(getApplicationContext(), getPackageName());
            }
        }
        Intent apply = new Intent(Aware.ACTION_AWARE_REFRESH);
        sendBroadcast(apply);
    }
}
