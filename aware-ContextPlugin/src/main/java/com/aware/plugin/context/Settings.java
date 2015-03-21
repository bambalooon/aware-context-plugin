package com.aware.plugin.context;

import android.content.Context;
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
     * State of Context plugin
     */
    public static final String STATUS_PLUGIN_CONTEXT = "status_plugin_context";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);
        syncSettings();
    }

    private void syncSettings() {
        CheckBoxPreference check = (CheckBoxPreference) findPreference(STATUS_PLUGIN_CONTEXT);
        check.setChecked(Aware.getSetting(getApplicationContext(), STATUS_PLUGIN_CONTEXT).equals("true"));
    }

    @Override
    protected void onResume() {
        super.onResume();
        syncSettings();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference.getKey().equals(STATUS_PLUGIN_CONTEXT)) {
            boolean isActive = sharedPreferences.getBoolean(key, false);
            Context applicationContext = getApplicationContext();
            Aware.setSetting(applicationContext, key, isActive);

            if (isActive) {
                Aware.startPlugin(applicationContext, getPackageName());
            } else {
                Aware.stopPlugin(applicationContext, getPackageName());
            }
        }
    }
}
