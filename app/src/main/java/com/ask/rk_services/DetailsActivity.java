package com.ask.rk_services;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;
import com.ask.rk_services.databinding.ActivityDetailsBinding;
import com.ask.rk_services.databinding.ActivitySignUpBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class DetailsActivity extends AppCompatActivity {

    ActivityDetailsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getSupportActionBar().hide();

        binding.radioRealButtonGroup.setPosition(0, false);

        binding.signupEmail.setText(getIntent().getStringExtra("email"));
        binding.signupPassword.setText(getIntent().getStringExtra("password"));

        binding.btnSignUpClickForLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(DetailsActivity.this, LoginActivity.class));

            }
        });

        binding.btnGetOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fullName = binding.signupFullName.getText().toString();
                final String email =  getIntent().getStringExtra("email");
                final String phoneNumber = binding.signupPhoneNumber.getText().toString();
                final String password = getIntent().getStringExtra("password");

                String userID;
                userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

                String accountType;
                int selectId = binding.radioRealButtonGroup.getPosition();

                if (selectId == 1) {

                    accountType = "Mechanical";

                } else {

                    accountType = "Customers";

                }
                if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {

                    Toast.makeText(DetailsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();

                }

                else {


                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (snapshot.hasChild(userID)) {

//                                Toast.makeText(DetailsActivity.this, "Phone is already register", Toast.LENGTH_SHORT).show();

                            } else {

                                databaseReference.child(userID).child("fullname").setValue(fullName);
                                databaseReference.child(userID).child("email").setValue(email);
                                databaseReference.child(userID).child("phonenumber").setValue(phoneNumber);
                                databaseReference.child(userID).child("password").setValue(password);
                                databaseReference.child(userID).child("accountType").setValue(accountType);

                                Intent intent = new Intent(getApplicationContext(),LauncherActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();

                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });







                }


            }
        });



    }


//    //set visibility
//                                binding.btnGetOTP.setVisibility(View.INVISIBLE);
//                                binding.progressBar.setVisibility(View.VISIBLE);
//    //verify phone number
//    PhoneAuthOptions options =
//            PhoneAuthOptions.newBuilder()
//                    .setPhoneNumber("+91"+binding.signupPhoneNumber.getText().toString())
//                    .setTimeout(60L, TimeUnit.SECONDS)
//                    .setActivity(DetailsActivity.this)
//                    .setCallbacks(new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                        @Override
//                        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
//                            binding.progressBar.setVisibility(View.GONE);
//                            binding.btnGetOTP.setVisibility(View.VISIBLE);
//                        }
//
//                        @Override
//                        public void onVerificationFailed(@NonNull FirebaseException e) {
//                            binding.progressBar.setVisibility(View.GONE);
//                            binding.btnGetOTP.setVisibility(View.VISIBLE);
//                            Toast.makeText(DetailsActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
//                            binding.progressBar.setVisibility(View.GONE);
//                            binding.btnGetOTP.setVisibility(View.VISIBLE);
//                            //action
//
//                            Intent intent = new Intent(getApplicationContext(),VerifyOTPActivity.class);
//                            intent.putExtra("mobile",binding.signupPhoneNumber.getText().toString());
//                            intent.putExtra("verificationId",verificationId);
//                            startActivity(intent);
//                            finish();
//
//                        }
//                    })
//                    .build();
//                                PhoneAuthProvider.verifyPhoneNumber(options);


}