package com.example.ratingapp.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Toast;

import com.example.ratingapp.databinding.ActivityRegisterBinding;
import com.example.ratingapp.model.User;
import com.example.ratingapp.retrofit.RetrofitInterface;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private ActivityRegisterBinding binding;
    private FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fAuth = FirebaseAuth.getInstance();

        binding.txtLogin.setOnClickListener(view -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        binding.btnRegister.setOnClickListener(view -> {
            register();
        });

    }

    private void register(){
        String email = binding.edtEmail.getText().toString().trim();
        String password = binding.edtPassword.getText().toString().trim();
        String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();

        if(email.isEmpty()){
            binding.edtEmail.setError("Please input your email");
            binding.edtEmail.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.edtEmail.setError("Email invalid");
            binding.edtEmail.requestFocus();
        }else if(password.isEmpty()){
            binding.edtPassword.setError("Please input your password");
            binding.edtPassword.requestFocus();
        }else if(!password.equals(confirmPassword)){
            binding.edtConfirmPassword.setError("Password does not match");
            binding.edtConfirmPassword.requestFocus();
        }
        else{
            HashMap<String, String> map = new HashMap<>();
            map.put("email", email);
            map.put("password", password);
            RetrofitInterface.retrofitInterface.registerUser(map).enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
                            if(task.isSuccessful()){
                                if(fUser.isEmailVerified()){
                                    Toast.makeText(RegisterActivity.this, "Email has been registered", Toast.LENGTH_SHORT).show();
                                }else{
                                    fUser.sendEmailVerification();
                                    Toast.makeText(RegisterActivity.this, "Please check email for verifying", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                    finish();
                                }
                            }else{
                                Toast.makeText(RegisterActivity.this, "Something wrong! Please try again.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
                @Override
                public void onFailure(Call<User> call, Throwable t) {

                }
            });

        }
    }
}