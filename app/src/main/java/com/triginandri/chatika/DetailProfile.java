package com.triginandri.chatika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.triginandri.chatika.Model.User;
import com.triginandri.chatika.helper.SharedPrefManager;

import java.util.ArrayList;

public class DetailProfile extends AppCompatActivity {

    TextInputEditText fldNama, fldNIM, fldProdi,fldUniv, fldEmail;
    AppCompatButton btnLogout;
    ImageButton btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_profile);

        setupView();
        setupFunction();

    }

    public void setupView(){

        fldNama = findViewById(R.id.fld_name);
        fldNIM = findViewById(R.id.fld_nim);
        fldProdi = findViewById(R.id.fld_prodi);
        fldUniv = findViewById(R.id.fld_univ);
        fldEmail = findViewById(R.id.fld_email);
        btnLogout = findViewById(R.id.btn_logout);
        btnBack = findViewById(R.id.btnBack);

        User user = SharedPrefManager.getInstance(this).getUser();

        fldNama.setText(user.getName());
        fldNIM.setText(user.getNim());
        fldProdi.setText(user.getProdi());
        fldUniv.setText(user.getUniv());
        fldEmail.setText(user.getEmail());





    }


    public void setupFunction(){

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(DetailProfile.this).logout();
                DetailProfile.super.onDestroy();
//                onDestroy();
//                DetailProfile.super.finish();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}