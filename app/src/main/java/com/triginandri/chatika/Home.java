package com.triginandri.chatika;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.triginandri.chatika.helper.SharedPrefManager;

public class Home extends Fragment {


    ImageButton btnProfil;
    Button btnMessage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        btnProfil = v.findViewById(R.id.btnProfil);
        btnMessage = v.findViewById(R.id.btn_message);

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(getActivity(), Login.class);
//                startActivity(i);
                Toast.makeText(getActivity(), "open Profile", Toast.LENGTH_SHORT).show();
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPrefManager.getInstance(getContext()).logout();
                getActivity().finish();
//                Intent i = new Intent(getActivity(), Login.class);
//                startActivity(i);
            }
        });

        return v;
    }


}