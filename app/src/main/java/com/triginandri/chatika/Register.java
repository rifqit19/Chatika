package com.triginandri.chatika;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.triginandri.chatika.Model.User;
import com.triginandri.chatika.helper.ApiClient;
import com.triginandri.chatika.helper.ApiConfig;
import com.triginandri.chatika.helper.ApiInterface;
import com.triginandri.chatika.helper.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    TextInputEditText fldNama, fldNIM, fldProdi,fldUniv, fldEmail, fldPassword, fldCopassword;
    AppCompatButton btnRegister;
    ImageButton btnBack;
    private ProgressBar loadingPB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        setupView();
        setupFunction();

    }

    public void setupView(){

        fldNama = findViewById(R.id.fld_name);
        fldNIM = findViewById(R.id.fld_nim);
        fldProdi = findViewById(R.id.fld_prodi);
        fldUniv = findViewById(R.id.fld_univ);
        fldEmail = findViewById(R.id.fld_email);
        fldPassword = findViewById(R.id.fld_password);
        fldCopassword = findViewById(R.id.fld_copassword);
        btnRegister = findViewById(R.id.btn_register);
        btnBack = findViewById(R.id.btnBack);
        loadingPB = findViewById(R.id.idLoadingPB);

    }

    public void setupFunction(){

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = fldNama.getText().toString();
                String nim = fldNIM.getText().toString();
                String prodi = fldProdi.getText().toString();
                String univ = fldUniv.getText().toString();
                String pass = fldPassword.getText().toString();
                String repass = fldCopassword.getText().toString();
                String email = fldEmail.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    fldNama.setError("Nama tidak boleh kosong");
                    fldNama.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(nim)) {
                    fldNIM.setError("NIM tidak boleh kosong");
                    fldNIM.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(prodi)) {
                    fldProdi.setError("Program studi tidak boleh kosong");
                    fldProdi.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(univ)) {
                    fldUniv.setError("Universitas tidak boleh kosong");
                    fldUniv.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(pass)) {
                    fldPassword.setError("Password tidak boleh kosong");
                    fldPassword.requestFocus();
                    return;
                }
                if (TextUtils.isEmpty(repass)) {
                    fldCopassword.setError("Ulangi password anda");
                    fldCopassword.requestFocus();
                    return;
                }

                if (!repass.equals(pass)) {
                    fldCopassword.setError("Password tidak sesuai");
                    fldCopassword.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(email)) {
                    fldEmail.setError("Email tidak boleh kosong");
                    fldEmail.requestFocus();
                    return;
                }

                registerUser(name,nim,prodi,univ,pass,email);

            }
        });

    }

    private void registerUser(String name, String nim,String prodi, String univ, String password, String email){
        loadingPB.setVisibility(View.VISIBLE);

        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> registerUser = apiInterface.registerUser(name, nim, prodi, univ, password, email);

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
                            Toast.makeText(Register.this, error_msg, Toast.LENGTH_SHORT).show();
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

                            SharedPrefManager.getInstance(Register.this).userLogin(user_data);

                            startActivity(new Intent(Register.this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY));
                            finish();
                        }

                    } catch (JSONException e){
                        Toast.makeText(Register.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
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
                Toast.makeText(Register.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}