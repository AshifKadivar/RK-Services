package com.ask.rk_services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ask.rk_services.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    ActivityLoginBinding binding;


//    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://rk-services-ab452-default-rtdb.firebaseio.com/");

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();


//        SharedPreferences preferences = getSharedPreferences("Login", MODE_PRIVATE);
//
//        String value = preferences.getString("Login", "");
//        if (value.equals("OK")) {
//            startActivity(new Intent(LoginActivity.this, LauncherActivity.class));
//            finish();
//
//        }else {
//            SharedPreferences.Editor editor = preferences.edit();
//            editor.putString("Login","OK");
//            editor.apply();
//        }

        binding.btnForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });

        binding.btnLoginClickForSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));

            }
        });

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String email = binding.loginEmail.getText().toString();
                final String password = binding.loginPassword.getText().toString();


                if (email.isEmpty() || password.isEmpty()) {

                    Toast.makeText(LoginActivity.this, "Please enter your email or password", Toast.LENGTH_SHORT).show();

                } else {

                    auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(LoginActivity.this,LauncherActivity.class));
                                finish();

                            } else {
                                Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

//                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                            if (snapshot.hasChild(phoneNumber)) {
//
//                                final String getPassword = snapshot.child(phoneNumber).child("password").getValue(String.class);
//
//                                if (getPassword.equals(password)) {
//                                    Toast.makeText(LoginActivity.this, "Successfully Logged in", Toast.LENGTH_SHORT).show();
//
//                                    startActivity(new Intent(LoginActivity.this,LauncherActivity.class));
//                                    finish();
//
//                                } else {
//
//                                    Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
//                                }
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });

                }

            }
        });
    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = auth.getCurrentUser();
//        if(currentUser != null){
//            startActivity(new Intent(LoginActivity.this,LauncherActivity.class));
//            finish();
//        }
//    }

    private void forgotPassword() {
        if (binding.loginEmail.getText().toString().trim().length() > 0)
            FirebaseAuth.getInstance().sendPasswordResetEmail(binding.loginEmail.getText().toString())
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Email Sent", Toast.LENGTH_SHORT).show();
//                            Snackbar.make(view.findViewById(R.id.layout), "Email Sent", Snackbar.LENGTH_LONG).show();
                        } else
                            Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show();
//                            Snackbar.make(view.findViewById(R.id.layout), "Something went wrong", Snackbar.LENGTH_LONG).show();
                    });
    }

}