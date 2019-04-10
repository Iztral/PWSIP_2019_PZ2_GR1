package com.grupa1.teleman.files;

import android.app.Application;
import android.content.Context;
import android.system.Os;

import com.grupa1.teleman.MainActivity;

public class FILES {
    public enum FILE_TYPE {
        CONFIG, LASTLOGIN
    }

    public static final String configPath = MainActivity.startContext.getApplicationInfo().dataDir + "/config.txt";
    public static final String lastloginPath = MainActivity.startContext.getApplicationInfo().dataDir + "/lastlogin.txt";
}
