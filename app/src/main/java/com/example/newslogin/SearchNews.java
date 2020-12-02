package com.example.newslogin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchNews extends AppCompatActivity {

    String BASE_URL = "https://newsapi.org/v2/";
    String country, API_KEY, category,query;
    ProgressDialog progressDialog;
    Adapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);
        query = getIntent().getStringExtra("searchQuery");
        API_KEY = "dbc9fb36ade44bc286dc3bdd2f56a5a6";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api newsAPI = retrofit.create(Api.class);
        Call<News> call = newsAPI.getNews(country,API_KEY,category,query);

        recyclerView = (RecyclerView)findViewById(R.id.headlinesRecyclerView);

        progressDialog = new ProgressDialog(SearchNews.this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialog);
        progressDialog.getWindow().setBackgroundDrawableResource(
                android.R.color.transparent
        );
        final LinearLayoutManager layoutManager;
        layoutManager = new LinearLayoutManager(SearchNews.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response) {
                List<Article> newsList = response.body().getArticles();
                TextView errorMessage = findViewById(R.id.errorMessage);
                if(newsList.size()==0)
                {
                    errorMessage.setText("No articles found for the searched topic");
                }
                adapter = new Adapter(SearchNews.this,newsList);
                recyclerView.setAdapter(adapter);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<News> call, Throwable t) {
                Toast.makeText(SearchNews.this, "Error Encountered", Toast.LENGTH_SHORT).show();
            }
        });
    }






    public void goBack(View view) {
        Intent i = new Intent(SearchNews.this,LandingPage.class);
        startActivity(i);
    }
}