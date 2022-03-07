package com.tech.startup.club.traqr.db;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tech.startup.club.traqr.QRCode.QRCodeGenerator;
import com.tech.startup.club.traqr.Signin.Sign_Up;
import com.tech.startup.club.traqr.model.Network;

import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class NetworkDB {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static void createNetwork(Network network, Context context){

        // Add a new network
        db.collection("networks")
                .add(network)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "Successfully created Network",
                                Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to create Network",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }



}
