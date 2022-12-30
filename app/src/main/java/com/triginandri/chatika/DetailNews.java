package com.triginandri.chatika;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;
import com.triginandri.chatika.helper.ApiClient;

public class DetailNews extends AppCompatActivity {

    TextView tvTitle,tvSub, tvContent;
    ImageView iv_detail;

    String title,sub,image,content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        title = getIntent().getExtras().getString("title");
        sub = getIntent().getExtras().getString("subtitle");
        image = getIntent().getExtras().getString("image");
        content = getIntent().getExtras().getString("content");

        setupToolbar();
        setupCollapsingToolbar();

        iv_detail = findViewById(R.id.iv_detail);
        tvTitle = findViewById(R.id.tv_title);
        tvSub = findViewById(R.id.tv_sub);
        tvContent = findViewById(R.id.tv_content);

        tvTitle.setText(title);
        tvSub.setText(sub);
        tvContent.setText(content);
        Picasso.get().load(image).placeholder(R.drawable.ic_placeholder).into(iv_detail);



    }

    public void setupToolbar(){

        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.ic_back_white);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    public void setupCollapsingToolbar(){
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(title);

        collapsingToolbarLayout.setCollapsedTitleTextColor(
                ContextCompat.getColor(this, R.color.white));
        collapsingToolbarLayout.setExpandedTitleColor(
                ContextCompat.getColor(this, R.color.transparent));

    }
}