package com.example.newslogin;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<Article> articles;

    public Adapter(Context context, List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Article a = articles.get(position);
        holder.tvTitle.setText(a.getTitle());
        holder.tvDescription.setText(a.getDescription());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,DisplayNews.class);
                i.putExtra("title", a.getTitle());
                i.putExtra("imageUrl",a.getUrlToImage());
                i.putExtra("date",a.getPublishedAt());
                i.putExtra("content",a.getContent());
                i.putExtra("description",a.getDescription());
                i.putExtra("newsUrl",a.getUrl());
                v.getContext().startActivity(i);
            }
        });

        String imgURL = a.getUrlToImage();
        if(imgURL!=null)
            Glide.with(context).load(imgURL).into(holder.newsImage);
        else{
            holder.newsImage.setImageResource(R.drawable.ic_menu_camera);
        }
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle, tvDescription;
        ImageView newsImage;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            newsImage = itemView.findViewById(R.id.newsImage);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
