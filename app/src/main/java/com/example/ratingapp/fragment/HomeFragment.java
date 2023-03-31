package com.example.ratingapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ratingapp.R;
import com.example.ratingapp.adapter.HomeAdapter;
import com.example.ratingapp.model.Content;
import com.example.ratingapp.retrofit.RetrofitInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Content> contentList;

    public HomeFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.rcvHome);

        contentList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);

        getAllContent();
        return view;
    }

    private void getAllContent(){
        RetrofitInterface.retrofitInterface.getAllContent().enqueue(new Callback<List<Content>>() {
            @Override
            public void onResponse(Call<List<Content>> call, Response<List<Content>> response) {
                contentList = response.body();
                HomeAdapter homeAdapter = new HomeAdapter(getContext(), contentList);
                recyclerView.setAdapter(homeAdapter);
            }

            @Override
            public void onFailure(Call<List<Content>> call, Throwable t) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_DeviceDefault_Dialog);
                builder.setTitle("Error");
                builder.setMessage("No Internet. Please try again");
                builder.setPositiveButton("OK", (dialog, i) -> {});
                builder.show();
            }
        });
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        recyclerView = view.findViewById(R.id.rcvHome);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        recyclerView.setHasFixedSize(true);
//        HomeAdapter homeAdapter = new HomeAdapter(getContext(), contentList);
//        recyclerView.setAdapter(homeAdapter);
//        homeAdapter.notifyDataSetChanged();
//    }
}