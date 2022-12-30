package com.triginandri.chatika;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.triginandri.chatika.Adapter.NewsAdapter;
import com.triginandri.chatika.Adapter.SavedAdapter;
import com.triginandri.chatika.Model.News;
import com.triginandri.chatika.Model.Saved;
import com.triginandri.chatika.helper.SharedPrefManager;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SavedMessage extends Fragment {

    private ArrayList<Saved> savedArrayList = new ArrayList<>();
    SavedAdapter adapter;
    RecyclerView rvSave;

    ImageButton btnProfil;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_saved_message, container, false);

        rvSave =v.findViewById(R.id.rv_saved);
        btnProfil = v.findViewById(R.id.btnProfil);


        LoadDataMessage();
//        savedArrayList.add(new Saved("laki","pembukaan test","tujuan test","pertanyaan test","penutupan test"));

        rvSave.setHasFixedSize(true);
        adapter = new SavedAdapter(savedArrayList, v.getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        rvSave.setLayoutManager(linearLayoutManager);
        rvSave.setMotionEventSplittingEnabled(false);
        rvSave.setAdapter(adapter);

        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DetailProfile.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }
        });


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        savedArrayList.clear();
    }

    public void LoadDataMessage(){

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();

        String json = sharedPreferences.getString(SharedPrefManager.KEY_MESSAGE, null);

        Type type = new TypeToken<ArrayList<Saved>>() {}.getType();

        savedArrayList = gson.fromJson(json, type);

        if (savedArrayList == null) {
            savedArrayList = new ArrayList<>();
        }

    }
}