package com.triginandri.chatika;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.triginandri.chatika.Adapter.NewsAdapter;
import com.triginandri.chatika.Model.News;
import com.triginandri.chatika.Model.User;
import com.triginandri.chatika.helper.ApiClient;
import com.triginandri.chatika.helper.ApiInterface;
import com.triginandri.chatika.helper.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends Fragment {


    ImageButton btnProfil;
    Button btnMessage;
    RecyclerView rvNews;
    private ProgressBar loadingPB;


    private ArrayList<News> newsArrayList = new ArrayList<>();
    NewsAdapter adapter;


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
        loadingPB = v.findViewById(R.id.idLoadingPB);
        rvNews = v.findViewById(R.id.rv_news);


        getNews();

        rvNews.setHasFixedSize(true);
        adapter = new NewsAdapter(newsArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL, false);
        rvNews.setLayoutManager(linearLayoutManager);
//        rv_menu.setNestedScrollingEnabled(false);
        rvNews.setMotionEventSplittingEnabled(false);
        rvNews.setAdapter(adapter);


        btnProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), DetailProfile.class).addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SendMessage.class);
                startActivity(i);
            }
        });

        return v;
    }

    private void getNews(){
        loadingPB.setVisibility(View.VISIBLE);

        ApiInterface apiInterface;
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseBody> getNews = apiInterface.getNews();

        getNews.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                loadingPB.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    try {

                        newsArrayList.clear();
                        String respon = response.body().string();
                        Log.e(TAG, "Response " + respon);
                        JSONObject jsonObj = new JSONObject(respon);

//                        String error_sts = jsonObj.getString("error");

                        JSONArray news = jsonObj.getJSONArray("results");

                        for(int i = 0; i < news.length(); i++){
                            JSONObject c = news.getJSONObject(i);

                            Integer id = c.getInt("id");
                            String title = c.getString("title");
                            String subtitle = c.getString("subtitle");
                            String content = c.getString("content");
                            String image = c.getString("image");

                            News n = new News();
                            n.setId(id);
                            n.setTitle(title);
                            n.setSubtitle(subtitle);
                            n.setContent(content);
                            n.setImage(image);
                            newsArrayList.add(n);

                            Log.e(TAG, "News " + c);
                        }

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e){
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.e(TAG, "Souldn't get json from server.");
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void
                        run() {
                            Toast.makeText(getActivity(), "Couldn't get json from server. Check LoCat for possible errors!", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                loadingPB.setVisibility(View.GONE);
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}