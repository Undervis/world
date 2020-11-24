package com.example.jsouptest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    private List<Friend> friends = new ArrayList<>();
    RecyclerView recyclerView;
    private String lastName;
    private String firstName;
    private String cityTitle;
    private String bdate;
    private String photo;
    private String domain;
    private String userID;
    private String access_token = "675c5e7f675c5e7f675c5e7f556728bf0e6675c675c5e7f38f08b5db2773cdc5fc078b0";
    public static String accessTokenByUser;
    public static String idByUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (accessTokenByUser != null){
            access_token = accessTokenByUser;
        }

        recyclerView = findViewById(R.id.recycler);
        EditText ed_userID = findViewById(R.id.ed_user_id);
        Button button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                friends.clear();
                getFriendsFromID(ed_userID.getText().toString());
            }
        });
    }

    private void extractJSON(String urlGetID){
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlGetID, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("error")){
                        JSONObject error = response.getJSONObject("error");
                        int errorCode = Integer.parseInt(error.getString("error_code"));
                        switch (errorCode){
                            case 15: Toast.makeText(getApplicationContext(), "Нет доступа, профиль приватный", Toast.LENGTH_SHORT).show();
                            break;
                            case 113: Toast.makeText(getApplicationContext(), "Неверный ID или пользователя не существует", Toast.LENGTH_SHORT).show();
                            break;
                            case 100: Toast.makeText(getApplicationContext(), "Пустое поле ID", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    JSONObject object = response.getJSONObject("response");
                    if (object.getString("count") == "0"){
                        Toast.makeText(getApplicationContext(), "У пользователя скрыт список друзей", Toast.LENGTH_SHORT).show();
                    }
                    JSONArray array = object.getJSONArray("items");
                    for(int i = 1; i <= array.length(); i++) {
                        JSONObject friend = array.getJSONObject(i);
                        firstName = friend.getString("first_name");
                        lastName = friend.getString("last_name");
                        try{
                            JSONObject city = friend.getJSONObject("city");
                            cityTitle = city.getString("title") + ", ";
                        } catch (Exception e){
                            cityTitle = "";
                        }
                        try{
                            bdate = friend.getString("bdate") + ", ";
                        } catch (Exception e){
                            bdate = "";
                        }
                        photo = friend.getString("photo_200");
                        domain = friend.getString("domain");
                        setData(firstName, lastName, cityTitle, bdate, photo, domain);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("tag", "Error: " + error.getMessage());
            }
        });
        queue.add(jsonObjectRequest);
    }

    private void getFriendsFromID(String domain){
        String url = URLGenerator.GenerateURLUserID(domain, access_token);
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray array = response.getJSONArray("response");
                    JSONObject object = array.getJSONObject(0);
                    userID = object.getString("id");
                    String url = URLGenerator.GenerateURLFriends(userID, access_token);
                    extractJSON(url);
                    Log.i("flag", userID);
                } catch (JSONException e) {
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }

    public void setData(String firstName, String lastName, String cityTitle, String bdate, String photo, String domain) {
        friends.add(new Friend(firstName, lastName, cityTitle, bdate, photo, domain));
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        FriendsAdapter adapter = new FriendsAdapter(getApplicationContext(), friends);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);
    }
}