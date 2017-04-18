package com.example.antoine.newsgateway;

/**
 * Created by antoine on 4/18/17.
 */

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import java.util.ArrayList;

public class NewsGatewayService extends Service {

    public static final String BROADCAST_SOURCES = "BROADCAST_SOURCES";
    public static final String BROADCAST_ARTICLES = "BROADCAST_ARTICLES";
    private static final String TAG = "NewsGatewayService";
    private ArrayList<Source> sourcesList;
    private ArrayList<Article> articlesList;
    private SendSourceReceiver sendSourceReceiver;
    private SendArticleReceiver sendArticleReceiver;
    private boolean runningArticles = true;
    private boolean runningSources = true;
    private NewsGatewayService newsGatewayService;

    public NewsGatewayService() {
        this.sourcesList = new ArrayList<>();
        this.articlesList = new ArrayList<>();
        this.newsGatewayService = this;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.sendSourceReceiver = new SendSourceReceiver(this);
        IntentFilter filter = new IntentFilter(BROADCAST_SOURCES);
        registerReceiver(sendSourceReceiver, filter);
        this.sendArticleReceiver = new SendArticleReceiver(this);
        IntentFilter filter2 = new IntentFilter(BROADCAST_ARTICLES);
        registerReceiver(sendArticleReceiver, filter2);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    public void sendSourceList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                new SourcesDownloadAsyncTask(newsGatewayService, null).execute();
                for (int i = 0; i < 15; i++) {
                    if (!runningSources) {
                        Log.d(TAG, "run: Thread loop stopped early");
                        break;
                    }

                    Log.d(TAG, "run: " + i);

                    try {
                        if (sourcesList.isEmpty()) {
                            Thread.sleep(1000);
                        } else {
                            i = 15;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent();
                intent.setAction(MainActivity.BROADCAST_SOURCES);
                intent.putExtra(MainActivity.DATA_SOURCES, sourcesList);
                sendBroadcast(intent);
                unregisterReceiver(sendSourceReceiver);
                sendSourceReceiver = new SendSourceReceiver(newsGatewayService);
                IntentFilter filter = new IntentFilter(BROADCAST_SOURCES);
                registerReceiver(sendSourceReceiver, filter);
                Log.d(TAG, "run: Ending loop");
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        this.unregisterReceiver(sendSourceReceiver);
        this.unregisterReceiver(sendArticleReceiver);
        super.onDestroy();
    }

    public ArrayList<Source> getSourcesList() {
        return sourcesList;
    }

    public void setSourcesList(ArrayList<Source> sourcesList) {
        this.sourcesList = sourcesList;
    }

    public void sendArticleList(final String source) {
        new ArticlesDownloadAsyncTask(newsGatewayService, source).execute();
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < 15; i++) {
                    if (!runningArticles) {
                        Log.d(TAG, "run: Thread loop stopped early");
                        break;
                    }

                    Log.d(TAG, "run: " + i);

                    try {
                        if (articlesList.isEmpty()) {
                            Thread.sleep(1000);
                        } else {
                            i = 15;
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent();
                intent.setAction(MainActivity.BROADCAST_ARTICLES);
                intent.putExtra(MainActivity.DATA_ARTICLES, articlesList);
                sendBroadcast(intent);
                unregisterReceiver(sendArticleReceiver);
                sendArticleReceiver = new SendArticleReceiver(newsGatewayService);
                IntentFilter filter = new IntentFilter(BROADCAST_ARTICLES);
                registerReceiver(sendArticleReceiver, filter);
                Log.d(TAG, "run: Ending loop");
            }
        }).start();
    }

    public ArrayList<Article> getArticlesList() {
        return articlesList;
    }

    public void setArticlesList(ArrayList<Article> articlesList) {
        this.articlesList = articlesList;
    }
}
