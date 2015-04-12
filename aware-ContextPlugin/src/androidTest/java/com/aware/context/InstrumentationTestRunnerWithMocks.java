package com.aware.context;

import android.os.Bundle;
import android.test.InstrumentationTestRunner;

/**
 * Name: InstrumentationTestRunnerWithMocks
 * Description: InstrumentationTestRunnerWithMocks allows working with mocks
 * Date: 2015-04-12
 * Created by BamBalooon
 */
public class InstrumentationTestRunnerWithMocks extends InstrumentationTestRunner {
    @Override
    public void onCreate(Bundle arguments) {
        super.onCreate(arguments);
        // temporary workaround for an incompatibility in current dexmaker (1.1) implementation and Android >= 4.3
        // cf. https://code.google.com/p/dexmaker/issues/detail?id=2 for details
        System.setProperty("dexmaker.dexcache", getTargetContext().getCacheDir().toString());
    }
}
