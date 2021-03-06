package com.example.antoine.newsgateway;

/**
 * Created by antoine on 4/18/17.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

public class RequestSourceReceiver extends BroadcastReceiver {

    public static final String BROADCAST_SOURCES = "BROADCAST_SOURCES";
    public static final String DATA_SOURCES = "DATA_SOURCES";
    private MainActivity ma;

    public RequestSourceReceiver(MainActivity ma) {
        this.ma = ma;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case BROADCAST_SOURCES:
                ArrayList<Source> value;
                if (intent.hasExtra(DATA_SOURCES)) {
                    value = (ArrayList<Source>) intent.getSerializableExtra(DATA_SOURCES);
                    ma.setSourcesList(value);
                }
                break;
            default:
                break;
        }
    }
}

