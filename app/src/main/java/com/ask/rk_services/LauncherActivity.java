package com.ask.rk_services;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * First activity of the app.
 * <p>
 * Responsible for checking if the user is logged in or not and call
 * the AuthenticationActivity or MainActivity depending on that.
 */
public class LauncherActivity extends AppCompatActivity {

//    int triedTypes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().hide();

        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            checkUserAccType();
        } else {
            Intent intent = new Intent(LauncherActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

        startService(new Intent(LauncherActivity.this, onAppKilledMechanical.class));
        startService(new Intent(LauncherActivity.this,onAppKilledCustomer.class));

    }

    private void checkUserAccType() {

        String userID;
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(userID).child("accountType");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    String type = snapshot.getValue().toString();

                    Toast.makeText(LauncherActivity.this, type, Toast.LENGTH_LONG).show();


                    if (type.equals("Mechanical")) {

                        DatabaseReference userData = FirebaseDatabase.getInstance().getReference().child("UserData").child("Mechanical").child(userID);
                        userData.setValue(true);

                        Intent intent = new Intent(LauncherActivity.this, MechanicalMapsActivity.class);
                        startActivity(intent);
                        finish();

                    } else if (type.equals("Customers")) {

                        DatabaseReference userData = FirebaseDatabase.getInstance().getReference().child("UserData").child("Customers").child(userID);
                        userData.setValue(true);

                        Intent intent = new Intent(LauncherActivity.this, CustomerMapsActivity.class);
                        startActivity(intent);
                        finish();

                    } else {

                        Intent intent = new Intent(LauncherActivity.this, DetailsActivity.class);
                        startActivity(intent);
                        finish();
                        Toast.makeText(LauncherActivity.this, "Please Fill Details", Toast.LENGTH_LONG).show();
                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    /**
     * Check user account type, either customer or driver.
     * If it doesn't have a type then start the DetailsActivity for the
     * user to be able to pick one.
     */
//    private void checkUserAccType() {
//        String userID;
//
//        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
//        DatabaseReference mCustomerDatabase = FirebaseDatabase.getInstance().getReference().child("users").child("Customers").child(userID);
//        mCustomerDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
////                    startApis("Customers");
//                    Intent intent = new Intent(getApplication(), CustomerMapsActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    checkNoType();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NotNull DatabaseError databaseError) {
//            }
//        });
//        DatabaseReference mDriverDatabase = FirebaseDatabase.getInstance().getReference().child("users").child("Mechanical").child(userID);
//        mDriverDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists() && dataSnapshot.getChildrenCount() > 0) {
////                    startApis("Drivers");
//                    Intent intent = new Intent(getApplication(), MechanicalMapsActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    startActivity(intent);
//                    finish();
//                } else {
//                    checkNoType();
//                }
//            }
//
//            @Override
//            public void onCancelled(@NotNull DatabaseError databaseError) {
//            }
//        });
//    }
//
//    /**
//     * checks if both types have not been fetched meaning the DetailsActivity must be called
//     */
//    void checkNoType() {
//        triedTypes++;
//        if (triedTypes == 2) {
//            Intent intent = new Intent(getApplication(), DetailsActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(intent);
//            finish();
//        }
//    }

    /**
     * starts up onesignal and stripe apis
     * @param type - type of the user (customer, driver)
     */
//    void startApis(String type) {
//        OneSignal.startInit(this).init();
//        OneSignal.sendTag("User_ID", FirebaseAuth.getInstance().getCurrentUser().getUid());
//        OneSignal.setEmail(FirebaseAuth.getInstance().getCurrentUser().getEmail());
//        //OneSignal.setInFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification);
//        OneSignal.idsAvailable((userId, registrationId) -> FirebaseDatabase.getInstance().getReference().child("Users").child(type).child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("notificationKey").setValue(userId));
//        PaymentConfiguration.init(
//                getApplicationContext(),
//                getResources().getString(R.string.publishablekey)
//        );
//    }
}
