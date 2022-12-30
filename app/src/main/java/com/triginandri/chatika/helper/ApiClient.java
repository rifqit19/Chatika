package com.triginandri.chatika.helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {


//    public static final String BASE_URL = "http://10.0.2.2:80/Chatika/users/";
//    public static final String BASE_URL_IMAGE = "http://10.0.2.2:80/Chatika/users/images/";
    public static final String BASE_URL = "http://172.20.10.7/Chatika/users/";
    public static final String BASE_URL_IMAGE = "http://172.20.10.7/Chatika/users/images/";

    private static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
