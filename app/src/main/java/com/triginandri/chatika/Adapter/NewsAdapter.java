package com.triginandri.chatika.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.triginandri.chatika.DetailNews;
import com.triginandri.chatika.Model.News;
import com.triginandri.chatika.R;
import com.triginandri.chatika.helper.ApiClient;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.MyViewHolder> {

    public NewsAdapter(List<News> newsList) {
        this.newsList = newsList;
    }

    List<News> newsList;

    @NonNull
    @Override
    public NewsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View iceCreamView = layoutInflater.inflate(R.layout.item_edu,null);
        MyViewHolder viewHolder = new MyViewHolder(iceCreamView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NewsAdapter.MyViewHolder holder, int position) {
        News news = newsList.get(position);

        holder.tv_name.setText(news.getTitle());
        holder.tv_sub.setText(news.getSubtitle());
//        holder.iv_thumb.setImageResource(baverages.getImage());
        Picasso.get().load(ApiClient.BASE_URL_IMAGE + news.getImage()).placeholder(R.drawable.ic_placeholder).into(holder.iv_thumb);
        holder.cv_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), DetailNews.class);
                i.putExtra("title", news.getTitle());
                i.putExtra("subtitle", news.getSubtitle());
                i.putExtra("image", ApiClient.BASE_URL_IMAGE+news.getImage());
                i.putExtra("content", news.getContent());
                view.getContext().startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public final TextView tv_name;
        public final ImageView iv_thumb;
        public final TextView tv_sub;
        public final CardView cv_menu;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_title);
            tv_sub = itemView.findViewById(R.id.tv_sub);
            iv_thumb = itemView.findViewById(R.id.iv_menu);
            cv_menu = itemView.findViewById(R.id.cv_menu);

        }
    }
}
