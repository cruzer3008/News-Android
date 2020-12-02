package com.example.newslogin.ui.technology;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newslogin.Adapter;
import com.example.newslogin.Api;
import com.example.newslogin.Article;
import com.example.newslogin.News;
import com.example.newslogin.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TechnologyFragment extends Fragment {
    RecyclerView recyclerView;
    Adapter adapter;
    ProgressDialog progressDialog;

    String BASE_URL = "https://newsapi.org/v2/";
    String country, API_KEY, category, query;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_technology, container, false);

        recyclerView = (RecyclerView)root.getRootView().findViewById(R.id.headlinesRecyclerView);

        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        country = "in";
        API_KEY = "dbc9fb36ade44bc286dc3bdd2f56a5a6";
        category = "technology";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api newsAPI = retrofit.create(Api.class);
        Call<News> call = newsAPI.getNews(country,API_KEY,category,query);

        progressDialog = new ProgressDialog(getContext());
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                String headline;
                TextView headlinesTextView;
                List<Article> newsList = response.body().getArticles();
                Log.i("Articles",newsList.get(1).getUrl());
                adapter = new Adapter(getContext(),newsList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
            }
        });
    }
}
