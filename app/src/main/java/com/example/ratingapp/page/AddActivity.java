package com.example.ratingapp.page;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.ratingapp.databinding.ActivityAddBinding;
import com.example.ratingapp.model.Content;
import com.example.ratingapp.retrofit.RetrofitInterface;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddActivity extends AppCompatActivity {

    private ActivityAddBinding binding;
    private int SELECT_PICTURE = 200;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBack.setOnClickListener(view -> {
            finish();
        });

        binding.btnSubmit.setOnClickListener(view -> {
            addNewContent();
        });

        binding.txtBrowseImage.setOnClickListener(view -> {
            imageChooser();
        });
    }

    private void addNewContent(){

        String title = binding.edtTitle.getText().toString().trim();
        String content = binding.edtTitle.getText().toString().trim();
        String image = String.valueOf(binding.imgBrowseImage.getDrawable());

        if(title.isEmpty()){
            binding.edtTitle.setError("Please input title");
            binding.edtTitle.requestFocus();
        }else if(content.isEmpty()){
            binding.edtContent.setError("Please input content");
            binding.edtContent.requestFocus();
        }else if(image == null){
            Toast.makeText(this, "Please browse image", Toast.LENGTH_SHORT).show();
        }else {
            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getInstance().format(calendar.getTime());



            StorageReference storageReference = FirebaseStorage.getInstance().getReference(title);
            storageReference.putFile(imageUri).addOnSuccessListener(taskSnapshot ->
                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                HashMap<String, String> map = new HashMap<>();
                map.put("image", String.valueOf(uri));
                map.put("title", title);
                map.put("content", content);
                map.put("createDate", currentDate);

                RetrofitInterface.retrofitInterface.addNewContent(map).enqueue(new Callback<Content>() {
                    @Override
                    public void onResponse(Call<Content> call, Response<Content> response) {
                        Toast.makeText(AddActivity.this, "Post successful", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Content> call, Throwable t) {
                        Log.d("Error: ", t.getMessage());
                    }
                });
            }));
        }
    }

    private void imageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == SELECT_PICTURE && data != null && data.getData() != null){
            imageUri = data.getData();
            binding.imgBrowseImage.setImageURI(imageUri);
            binding.txtBrowseImage.setVisibility(View.GONE);
        }
    }
}