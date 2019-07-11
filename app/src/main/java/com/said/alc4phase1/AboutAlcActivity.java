package com.said.alc4phase1;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.http.SslError;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


/**
 * @author Said Mohamed
 */
public class AboutAlcActivity extends AppCompatActivity {

    private WebView mAboutAlcWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_alc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//Display the Up button to return back to main activity

        ProgressBar progressBar = findViewById(R.id.progressBar);//Loading indicator
        boolean connectionStatus = checkInternetConnection();

        if(connectionStatus){
            //Network is available
            //Display the webview
            mAboutAlcWebView = findViewById(R.id.webview);
            mAboutAlcWebView.getSettings().setJavaScriptEnabled(true);
            mAboutAlcWebView.setWebViewClient(new myWebViewClient(progressBar));
            mAboutAlcWebView.loadUrl("https://andela.com/alc/");
        }
        else {
            //Network unavailable
            //Hide Progressbar
            //Display error message
            progressBar.setVisibility(View.GONE);
            findViewById(R.id.no_internet).setVisibility(View.VISIBLE);
        }

    }

    /**
     * Helper method to check network availability
     * @return true if network available false if no internet connection
     */
    private boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                this.getSystemService(Context.CONNECTIVITY_SERVICE);

        return (connectivityManager.getActiveNetworkInfo() != null
                && connectivityManager.getActiveNetworkInfo().isAvailable()
                && connectivityManager.getActiveNetworkInfo().isConnected());
    }


    /**
     * Defines the behavior when the back key is pressed
     *
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mAboutAlcWebView.canGoBack()) {
            mAboutAlcWebView.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history exit the activity
        return super.onKeyDown(keyCode, event);
    }



    private class myWebViewClient extends WebViewClient {
        private ProgressBar progressBar;

        private myWebViewClient(ProgressBar progressBar) {
            this.progressBar=progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }

        /**
         * Display loading indicator every time a page loads
         */
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        /**
         * When page has finished loading hide the loading indicator and display the page
         */
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.GONE);
            view.setVisibility(View.VISIBLE);
        }

        /**
         * Handle the ssl error received when loading Andela's page
         */
        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
        }
    }
}


