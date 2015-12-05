package com.app.jobblechallenge.utils;

import android.app.Activity;

/**
 * The base interface that an operations ("Ops") class must implement
 * so that it can be notified automatically by the GenericActivity
 * framework when runtime configuration changes occur.
 */
public interface ConfigurableOps {
     
    void onConfiguration(Activity activity);
}
