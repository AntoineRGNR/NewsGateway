package com.example.antoine.newsgateway;

/**
 * Created by antoine on 4/18/17.
 */

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class SendSourceReceiver extends BroadcastReceiver {

    public static final String BROADCAST_SOURCES = "BROADCAST_SOURCES";
    private NewsGatewayService newsGatewayService;

    public SendSourceReceiver(NewsGatewayService newsGatewayService) {
        this.newsGatewayService = newsGatewayService;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case BROADCAST_SOURCES:
                newsGatewayService.sendSourceList();
                break;
            default:
                break;
        }
    }
}
