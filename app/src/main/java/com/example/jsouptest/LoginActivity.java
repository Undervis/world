package com.example.jsouptest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activity);

        TextView tv_login = findViewById(R.id.tv_login);
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Activity activity = new Activity();
                //VK.login(activity);
                Intent intent = new Intent(LoginActivity.this, LoginWebClient.class);
                startActivityForResult(intent, 1);
            }
        });

        TextView tv_login_without = findViewById(R.id.tv_login_without);
        tv_login_without.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("access_token", data.getStringExtra("access_token"));
        intent.putExtra("user_id", data.getStringExtra("user_id"));
        MainActivity.accessTokenByUser = data.getStringExtra("access_token");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}