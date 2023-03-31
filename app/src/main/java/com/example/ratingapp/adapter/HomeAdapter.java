package com.example.ratingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ratingapp.R;
import com.example.ratingapp.model.Content;
import com.example.ratingapp.page.DetailContentActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{

    Context context;
    List<Content> contentList;

    public HomeAdapter(Context context, List<Content> contentList){
        this.context = context;
        this.contentList = contentList;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.home_item, parent, false);
        return new HomeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {
        Content content = contentList.get(position);
        holder.txtTitle.setText(content.getTitle());
        holder.txtDate.setText(content.getCreateDate());
        Picasso.get().load(content.getImage()).fit().into(holder.imgContent);
        holder.cardView.setOnClickListener(view -> {
            Intent intent = new Intent(context, DetailContentActivity.class);
            intent.putExtra("id", content.getId());
            intent.putExtra("title", content.getTitle());
            intent.putExtra("content", content.getContent());
            intent.putExtra("image", content.getImage());
            intent.putExtra("createDate", content.getCreateDate());
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public static class HomeViewHolder extends RecyclerView.ViewHolder{

        TextView txtTitle, txtDate;
        ImageView imgContent;
        CardView cardView;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);

            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtDate = itemView.findViewById(R.id.txtCreateDate);
            cardView = itemView.findViewById(R.id.cardView);
            imgContent = itemView.findViewById(R.id.imgContent);
        }
    }
}
