package com.dam.recyclerjsonvolley;

import static com.dam.recyclerjsonvolley.Nodes.*;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_URL);
        String creator = intent.getStringExtra(EXTRA_CREATOR);
        int likes = intent.getIntExtra(EXTRA_LIKES, 0);

        ImageView imageView = findViewById(R.id.ivDetailImage);
        TextView tvCreator = findViewById(R.id.tvDetailCreator);
        TextView tvLikes = findViewById(R.id.tvDetailLikes);

        tvCreator.setText(creator);
        tvLikes.setText("Likes : " + likes);

        Glide.with(this)
                .load(imageUrl)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

    }
}