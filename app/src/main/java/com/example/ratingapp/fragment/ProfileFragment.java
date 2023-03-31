package com.example.ratingapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ratingapp.R;
import com.example.ratingapp.authentication.LoginActivity;

public class ProfileFragment extends Fragment {

    private Button btnLogout;
    private TextView txtEmail;
    public ProfileFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        btnLogout = view.findViewById(R.id.btnLogout);
        txtEmail = view.findViewById(R.id.txtEmail);

        btnLogout.setOnClickListener(view1 -> {
            logout();
        });

        return view;
    }

    private void logout(){
        SharedPreferences preferences = this.getContext().getSharedPreferences("checkbox", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("remember", "false");
        editor.apply();

        startActivity(new Intent(getContext(), LoginActivity.class));
        getActivity().finish();
    }
}