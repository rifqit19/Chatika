package com.triginandri.chatika.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.triginandri.chatika.Login;
import com.triginandri.chatika.Model.Saved;
import com.triginandri.chatika.Model.User;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPrefManager {

    //the constants
    public static final String SHARED_PREF_NAME = "chatikasPref";
    private static final String KEY_ID = "chatika_id";
    private static final String KEY_NAME = "chatika_name";
    private static final String KEY_NIM = "chatika_nim";
    private static final String KEY_PRODI = "chatika_prodi";
    private static final String KEY_UNIV = "chatika_univ";
    private static final String KEY_EMAIL = "chatika_email";
    private static final String KEY_KEY = "chatika_key";
    public final static String KEY_MESSAGE = "chatike_message";


    private static SharedPrefManager mInstance;
    private static Context mCtx;

    private SharedPrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    public void SaveMessage(){

        SharedPreferences sharedPreferences =  mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();

        List<Saved> savedList = new ArrayList<>();
        String json = gson.toJson(savedList);
        editor.putString(KEY_MESSAGE, json);
        editor.apply();
    }



    public void userLogin(User user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ID, user.getId());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_NIM, user.getNim());
        editor.putString(KEY_PRODI, user.getProdi());
        editor.putString(KEY_UNIV, user.getUniv());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_KEY, user.getKey());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_NAME, null) != null;
    }

    public User getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getInt(KEY_ID, -1),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_NIM, null),
                sharedPreferences.getString(KEY_PRODI, null),
                sharedPreferences.getString(KEY_UNIV, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_KEY, null)
        );
    }

    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, Login.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
    }
}
