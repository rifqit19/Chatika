package com.triginandri.chatika;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.triginandri.chatika.Model.User;
import com.triginandri.chatika.helper.ApiClient;
import com.triginandri.chatika.helper.ApiInterface;
import com.triginandri.chatika.helper.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {


    TextView tvRegister;
    TextInputEditText fldEmail, fldPassword;
    AppCompatButton btnLogin;
    private ProgressBar loadingPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupView();
        setupFunction();

    }

    public void setupView(){

        tvRegister = findViewById(R.id.tvRegister);
        fldEmail = findViewById(R.id.fld_email);
        fldPassword = findViewById(R.id.fld_password);
        btnLogin = findViewById(R.id.btn_login);
        loadingPB = findViewById(R.id.idLoadingPB);

    }

    public void setupFunction(){

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = fldEmail.getText().toString();
                String pass = fldPassword.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    fldEmail.setError("Email tidak boleh kosong");
                    fldEmail.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    fldPassword.setError("Password tidak boleh kosong");
                    fldPassword.requestFocus();
                    return;
                }

                loginUser(email,pass);
            }
        });

    }

    private void loginUser(String email, String password){
        loadingPB.setVisibility(View.VISIBLE);

        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> registerUser = apiInterface.loginUser(email, password);

        registerUser.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loadingPB.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {

                        String respon = response.body().string();
                        Log.e(TAG, "Response " + respon);
                        JSONObject jsonObj = new JSONObject(respon);

                        String error_sts = jsonObj.getString("error");

                        if(error_sts == "true"){
                            String error_msg = jsonObj.getString("error_msg");
                            Toast.makeText(Login.this, error_msg, Toast.LENGTH_SHORT).show();
                        }else{
                            JSONObject user = jsonObj.getJSONObject("user");
                            User user_data = new User(
                                    user.getInt("id"),
                                    user.getString("name"),
                                    user.getString("nim"),
                                    user.getString("prodi"),
                                    user.getString("univ"),
                                    user.getString("email"),
                                    user.getString("key")
                            );

                            SharedPrefManager.getInstance(Login.this).userLogin(user_data);
//                            Toast.makeText(Login.this, users.getName(), Toast.LENGTH_SHORT).show();

                            startActivity(new Intent(Login.this, MainActivity.class));
                            Login.super.finish();
                        }

                    } catch (JSONException e){
                        Toast.makeText(Login.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "Souldn't get json from server.");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void
                        run() {
                            Toast.makeText(getApplicationContext(), "Couldn't get json from server. Check LoCat for possible errors!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loadingPB.setVisibility(View.GONE);
                Toast.makeText(Login.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}