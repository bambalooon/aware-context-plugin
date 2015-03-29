package com.aware.context.application;

import android.app.Application;
import com.aware.context.management.ContextManagement;
import com.aware.context.property.GenericContextProperty;
import com.aware.context.storage.PersistenceContextStorage;
import com.aware.context.transform.ContextPropertySerialization;

/**
 * Name: ContextApplication
 * Description: ContextApplication
 * Date: 2015-03-29
 * Created by BamBalooon
 */
public class ContextApplication extends Application {
    private static ContextApplication sInstance;

    public static ContextApplication getInstance() {
        if (sInstance == null) {
            throw new ExceptionInInitializerError("ContextApplication wasn't initialized. "
                    + "ContextApplication has to be specified as application name in manifest.");
        }
        return sInstance;
    }

    private ContextManagement contextManagement;

    public ContextManagement getContextManagement() {
        return contextManagement;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        initializeInstance();
    }

    protected void initializeInstance() {
        contextManagement = new ContextManagement(
                new PersistenceContextStorage<>(
                        getSharedPreferences(PersistenceContextStorage.CONTEXT_PREFERENCES, MODE_PRIVATE),
                        new ContextPropertySerialization<>(GenericContextProperty.class)
                )
        );
    }
}
