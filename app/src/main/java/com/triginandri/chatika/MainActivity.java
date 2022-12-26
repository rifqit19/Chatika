package com.triginandri.chatika;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.triginandri.chatika.helper.SharedPrefManager;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    AppCompatButton btnLogin, btnRegister;
    BottomNavigationView bnView;
    Home homeFragment = new Home();
    SavedMessage savedMessageFragment = new SavedMessage();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);
        bnView = findViewById(R.id.bn_main);
        bnView.setOnNavigationItemSelectedListener(this);
        bnView.setSelectedItemId(R.id.home);



        setupfunction();

    }

    private void setupfunction(){

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, Login.class));
            return;
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Login.class);
                startActivity(i);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, Register.class);
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main,homeFragment).commit();
                return true;

            case R.id.saved_message:
                getSupportFragmentManager().beginTransaction().replace(R.id.rl_main,savedMessageFragment).commit();
                return true;

        }
        return false;
    }

}