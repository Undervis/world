package com.example.jsouptest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.vk.api.sdk.VK;

public class LoginWebClient extends AppCompatActivity {

    String urlString;
    static String dialogMessage;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_web);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        Uri uri = Uri.parse("https://oauth.vk.com/authorize").buildUpon()
                .appendQueryParameter("client_id", "7659889").appendQueryParameter("redirect_uri", "0")
                .appendQueryParameter("display", "mobile").appendQueryParameter("response_type", "token").build();
        urlString = uri.toString();
        WebView webView = findViewById(R.id.web_login);
        webView.loadUrl(uri.toString());
        WebClient webClient = new WebClient();
        webView.setWebViewClient(webClient);
    }

    public static class DialogCreator extends AppCompatDialogFragment{
        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setMessage(dialogMessage)
                    .setNegativeButton("Выход", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            getActivity().finish();
                        }
                    });

            return dialog.create();
        }
    }

    private class WebClient extends WebViewClient{
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progressBar.setVisibility(View.INVISIBLE);
            if(view.getTitle().equals("")){
                dialogMessage = "Отсутсвует подключение к интернету";
                FragmentManager manager = getSupportFragmentManager();
                DialogCreator dialogCreator = new DialogCreator();
                dialogCreator.show(manager, "error");
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            checkURL(url);
            return false;
        }
    }

    private void checkURL(String url){
        Intent intent = new Intent(LoginWebClient.this, MainActivity.class);
        if(url.contains("vk.com/blank.html")){
            String fragmentOfURL = Uri.parse(url).getFragment();
            String accessToken = fragmentOfURL.substring(13, 98);
            String userID = fragmentOfURL.substring(124);
            intent.putExtra("access_token", accessToken);
            intent.putExtra("user_id", userID);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        }
        if(url.contains("user_denied")){
            dialogMessage = "Вы отменили вход в VK";
            FragmentManager manager = getSupportFragmentManager();
            DialogCreator dialogCreator = new DialogCreator();
            dialogCreator.show(manager, "error");
        }

    }

}