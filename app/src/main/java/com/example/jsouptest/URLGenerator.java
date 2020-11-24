package com.example.jsouptest;

import android.net.Uri;

public class URLGenerator {


    public static String GenerateURLFriends(String userID, String access_token){
        Uri uri = Uri.parse("https://api.vk.com/method/friends.get").buildUpon()
                .appendQueryParameter("user_id", userID)
                .appendQueryParameter("v", "5.21")
                .appendQueryParameter("order", "name")
                .appendQueryParameter("fields", "name,city,bdate,photo_200,domain")
                .appendQueryParameter("access_token", access_token).build();
        return uri.toString();
    }

    public static String GenerateURLUserID(String userDomain, String access_token){
        Uri uri = Uri.parse("https://api.vk.com/method/users.get").buildUpon()
                .appendQueryParameter("user_ids", userDomain)
                .appendQueryParameter("v", "5.89")
                .appendQueryParameter("access_token", access_token).build();
        return uri.toString();
    }
}
