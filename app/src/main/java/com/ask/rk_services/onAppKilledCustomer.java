package com.ask.rk_services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.firebase.geofire.GeoFire;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class onAppKilledCustomer extends Service {


    @androidx.annotation.Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);

        String customerId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference customerRequest = FirebaseDatabase.getInstance().getReference("CustomerRequest");

        GeoFire geoFireCustomerRequest = new GeoFire(customerRequest);
        geoFireCustomerRequest.removeLocation(customerId);

    }
}
