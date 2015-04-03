package com.desidime.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

/**
 * Created by Akshay M on 3/30/2015.
 */
public class SplashScreen extends Activity {
    private static final int SPLASH_SHOW_TIME = 1000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        /**
         * Wait in background for splash screen
         */
        new BackgroundSplashTask().execute();
    }

    private class BackgroundSplashTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.sleep(SPLASH_SHOW_TIME);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            finish();
            Intent i = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(i);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
}
