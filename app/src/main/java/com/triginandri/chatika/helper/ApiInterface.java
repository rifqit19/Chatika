package com.triginandri.chatika.helper;

import com.triginandri.chatika.Model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

//    @FormUrlEncoded
//    @POST("register.php")
//
//        // on below line we are creating a method to post our data.
//    Call<User> register(@Body User dataModal);

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> registerUser(@Field("name") String name, @Field("nim")String nim, @Field("prodi")String prodi, @Field("univ")String univ, @Field("password")String password, @Field("email")String email);

    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> loginUser(@Field("email") String email, @Field("password")String password);

}
