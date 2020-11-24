package com.example.jsouptest;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.api.sdk.VK;

public class LoginWebClient extends AppCompatActivity {

    String urlString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_web);

        Uri uri = Uri.parse("https://oauth.vk.com/authorize").buildUpon()
                .appendQueryParameter("client_id", "7659889").appendQueryParameter("redirect_uri", "0")
                .appendQueryParameter("display", "mobile").appendQueryParameter("response_type", "token").build();
        urlString = uri.toString();
        WebView webView = findViewById(R.id.web_login);
        webView.loadUrl(uri.toString());
        WebClient webClient = new WebClient();
        webView.setWebViewClient(webClient);
    }

    private class WebClient extends WebViewClient{
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            checkURL(url);
            return false;
        }
    }

    private void checkURL(String url){
        Intent intent = new Intent();
        if(url.contains("vk.com/blank.html")){
            String fragmentOfURL = Uri.parse(url).getFragment();
            String accessToken = fragmentOfURL.substring(13, 98);
            String userID = fragmentOfURL.substring(124);
            intent.putExtra("access_token", accessToken);
            intent.putExtra("user_id", userID);
            setResult(RESULT_OK, intent);
            finish();
        }
        if(url.contains("user_denied")){
            finishActivity(RESULT_CANCELED);
        }
    }

}