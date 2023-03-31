package com.example.ratingapp.page;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.ratingapp.databinding.ActivityDetailContentBinding;
import com.example.ratingapp.retrofit.RetrofitInterface;
import com.squareup.picasso.Picasso;

public class DetailContentActivity extends AppCompatActivity {

    private ActivityDetailContentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailContentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(view -> {
            finish();
        });

        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        String createDate = getIntent().getStringExtra("createDate");

        binding.txtTitle.setText(title);
        binding.txtContent.setText(content);
        binding.txtTime.setText(createDate);
        Picasso.get().load(getIntent().getStringExtra("image")).fit().into(binding.imgContent);
    }

}