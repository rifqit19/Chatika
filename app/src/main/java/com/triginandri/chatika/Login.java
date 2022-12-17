package com.triginandri.chatika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;

public class Login extends AppCompatActivity {


    TextView tvRegister;
    TextInputEditText fldEmail, fldPassword;
    AppCompatButton btnLogin;


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

    }

    public void setupFunction(){

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });

    }


}