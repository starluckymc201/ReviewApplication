package com.example.ratingapp.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.ratingapp.page.MainActivity;
import com.example.ratingapp.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fAuth = FirebaseAuth.getInstance();

        binding.btnLogin.setOnClickListener(view -> {
            login();
        });

        binding.txtSignUp.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });

        binding.txtForgotPassword.setOnClickListener(view -> {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        String checkbox = preferences.getString("remember", "");

        if(checkbox.equals("true")){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finishAffinity();
        }
        else if(checkbox.equals("false")){
        }

        binding.cbRememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(compoundButton.isChecked()){

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "true");
                    editor.apply();
                }
                else if(!compoundButton.isChecked()){

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember", "false");
                    editor.apply();
                }
            }
        });

    }

    private void login(){
        String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();

        if(email.isEmpty()){
            binding.edtEmail.setError("Please input your email");
            binding.edtEmail.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edtEmail.setError("Email invalid");
            binding.edtEmail.requestFocus();
        }else if(password.isEmpty()){
            binding.edtPassword.setError("Please input your password");
            binding.edtPassword.requestFocus();
        }else{

            fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
                        if(fUser.isEmailVerified()){
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }else{
                            Toast.makeText(LoginActivity.this, "Your account does not exist or verify", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(LoginActivity.this, "Wrong email or password! Please try again!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}