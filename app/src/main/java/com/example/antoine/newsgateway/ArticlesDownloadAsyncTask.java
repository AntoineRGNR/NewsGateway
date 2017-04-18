package com.example.antoine.newsgateway;

/**
 * Created by antoine on 4/18/17.
 */

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class ArticlesDownloadAsyncTask extends AsyncTask<String, Integer, String> {

    //Var Static Final String Declaration & Initialization
    private static final String ARTICLES_SEARCH_HTTP = "https://newsapi.org/v1/articles?source=";
    private static final String ARTICLES_SEARCH_API = "&apiKey=fc71d06a7b2a4737be892816b7281f3c";
    private static final String TAG = "AsyncArticlesLoader";

    //Var String Declaration
    private String source;

    //Var NewsGatewayService Declaration
    private NewsGatewayService newsGatewayService;

    /**
     * Context and Source Initialization
     */
    public ArticlesDownloadAsyncTask(NewsGatewayService newsGatewayService, String source) {
        Log.d(TAG, "ArticlesDownloadAsyncTask: Start");
        this.newsGatewayService = newsGatewayService;
        this.source = source;
    }

    @Override
    protected void onPreExecute() {
        //Nothing to do here
    }


    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: Start");
        //After Receiving Articles, parsing it and send it to Articles list
        newsGatewayService.setArticlesList(parseJSON(s));
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "doInBackground: Start");
        String urlString;
        StringBuilder sb = new StringBuilder();
        if (source != null) {
            urlString = ARTICLES_SEARCH_HTTP + this.source + ARTICLES_SEARCH_API;
            Uri dataUri = Uri.parse(urlString);
            String urlToUse = dataUri.toString().replaceAll(" ", "%20").toLowerCase();
            Log.d(TAG, "doInBackground: " + urlToUse);
            try {
                URL url = new URL(urlToUse);
                URLConnection conn = url.openConnection();
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader((new InputStreamReader(is)));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line).append('\n');
                }
            } catch (Exception e) {
                Log.d(TAG, "doInBackground: ", e);
                return null;
            }
        }
        Log.d(TAG, "doInBackground: End: " + sb.toString());
        return sb.toString();
    }

    /**
     * Parsing JSON Array Method
     */
    private ArrayList<Article> parseJSON(String s) {
        Log.d(TAG, "parseJSON: Start");
        ArrayList<Article> articlesList = new ArrayList<>();
        try {
            JSONObject jObjMain = new JSONObject(s);
            //JSON parser set to look at node articles
            JSONArray jArrayArticles = jObjMain.getJSONArray("articles");
            for (int i = 0; i < jArrayArticles.length(); i++) {
                JSONObject jsonObject = (JSONObject) jArrayArticles.get(i);
                //Adding to Articles List a article made of an Author, a Title, a Description, an Url, an UrlToImage and a PublishedAt
                articlesList.add(new Article(jsonObject.getString("author"), jsonObject.getString("title"), jsonObject.getString("description"), jsonObject.getString("url"), jsonObject.getString("urlToImage"), jsonObject.getString("publishedAt")));
            }
        } catch (Exception e) {
            Log.d(TAG, "parseJSON: " + e.getMessage());
        }
        Log.d(TAG, "parseJSON: End: " + articlesList);
        return articlesList;
    }
}

