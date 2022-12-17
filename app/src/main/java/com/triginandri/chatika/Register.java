package com.triginandri.chatika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity {

    TextInputEditText fldNama, fldNIM, fldProdi, fldEmail, fldPassword, fldCopassword;
    AppCompatButton btnRegister;
    ImageButton btnBack;


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
        fldEmail = findViewById(R.id.fld_email);
        fldPassword = findViewById(R.id.fld_password);
        fldCopassword = findViewById(R.id.fld_copassword);
        btnRegister = findViewById(R.id.btn_register);
        btnBack = findViewById(R.id.btnBack);

    }

    public void setupFunction(){

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }


}