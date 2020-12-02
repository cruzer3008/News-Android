package com.example.newslogin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DisplayNews extends AppCompatActivity {

    String newsDescription;
    String newsUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_news);

        TextView displayTitle = (TextView)findViewById(R.id.displayTitle);
        ImageView displayImage = (ImageView)findViewById(R.id.displayImage);
        TextView displayDate = (TextView)findViewById(R.id.displayDate);
        TextView displayContent = (TextView)findViewById(R.id.displayContent);

        String title = getIntent().getStringExtra("title");

        String imageUrl = getIntent().getStringExtra("imageUrl");
        Glide.with(this).load(imageUrl).into(displayImage);
        displayTitle.setText(title);

        String date = getIntent().getStringExtra("date");
        displayDate.setText(date.substring(0,10)+"   "+date.substring(11,19)+" GMT");

        String description = getIntent().getStringExtra("description");
        newsDescription = description;

        newsUrl = getIntent().getStringExtra("newsUrl");

        String content = getIntent().getStringExtra("content");
        if(content==null){
            displayContent.setText("Click on the headline to read more....");
        }
        else {
            displayContent.setText(content);
        }


    }

    public void goBack(View view) {
        Intent i = new Intent(DisplayNews.this,LandingPage.class);
        startActivity(i);
    }

    public void shareNews(View view) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody =  "HEADLINE- "+newsDescription+" "+newsUrl+" From Yash's News App";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    public void openNews(View view){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(newsUrl));
        startActivity(i);
    }

}