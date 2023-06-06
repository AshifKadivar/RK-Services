package com.ask.rk_services;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.ask.rk_services.databinding.ActivitySignUpBinding;

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
import com.google.firebase.database.ValueEventListener;


public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;


//    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://rk-services-ab452-default-rtdb.firebaseio.com/");

    FirebaseAuth auth;

//    private SegmentedButtonGroup mRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();

//        mRadioGroup = findViewById(R.id.radioRealButtonGroup);
//
//        mRadioGroup.setPosition(0, false);



        binding.btnSignUpClickForLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));

            }
        });

        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                userSignup();


//                final String fullName = binding.signupFullName.getText().toString();
//                final String email = binding.signupEmail.getText().toString();
//                final String phoneNumber = binding.signupPhoneNumber.getText().toString();
//                final String password = binding.signupPassword.getText().toString();
//
////                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
////                String userID;
////                userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//
//                final String accountType;
//                int selectId = mRadioGroup.getPosition();
//
//                if (selectId == 1) {
//                    accountType = "Drivers";
//                } else {
//                    accountType = "Customers";
//                }
//                if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
//
//                    Toast.makeText(SignUpActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
//
//                }
//
//                else {
//
//                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                            if (snapshot.hasChild(phoneNumber)) {
//
//                                Toast.makeText(SignUpActivity.this, "Phone is already register", Toast.LENGTH_SHORT).show();
//
//                            } else {
//
//                                databaseReference.child("users").child(phoneNumber).child("fullname").setValue(fullName);
//                                databaseReference.child("users").child(phoneNumber).child("email").setValue(email);
//                                databaseReference.child("users").child(phoneNumber).child("phonenumber").setValue(phoneNumber);
//                                databaseReference.child("users").child(phoneNumber).child("password").setValue(password);
//                                databaseReference.child("users").child(phoneNumber).child("accountType").setValue(accountType);
//
//
//
//
//                            }
//
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError error) {
//
//                        }
//                    });
//
//
//                }
//
//
            }
        });

    }

    private void userSignup() {

        final String email = binding.signupEmail.getText().toString();
        final String password = binding.signupPassword.getText().toString();

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) {

                    Toast.makeText(SignUpActivity.this, "User signup successfully", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
                    intent.putExtra("email",email);
                    intent.putExtra("password",password);
                    startActivity(intent);

                } else {
                    Toast.makeText(SignUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    }