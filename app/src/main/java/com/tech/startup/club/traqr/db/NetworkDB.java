package com.tech.startup.club.traqr.db;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tech.startup.club.traqr.QRCode.QRCodeGenerator;
import com.tech.startup.club.traqr.Signin.Sign_Up;
import com.tech.startup.club.traqr.model.Network;
import com.tech.startup.club.traqr.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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

    //TODO TEST FUNCTION
    public static void addAuthUserNetwork(Network network, User user){
        //query to find network
        Query query = db.collection("networks").whereEqualTo("networkID", network.getNetworkID());
        //list of users found in db
        List<Network> foundNetworks = new ArrayList<Network>();

        //excuting query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //getting current auth users
                        List<String> s = (List<String>) document.get("authUsers");
                        //adding auth user
                        s.add(user.getUuid());
                        //querying back into db
                        document.getReference().update("authUsers", s).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    System.out.println("ERROR");
                                }
                            }
                        });
                        break;
                    }
                }  else {
                    System.err.println("USER COULD NOT BE FOUND");
                }
            }
        });
    }

    //TODO MAKE FUNCTION
    public static void getNetworkNames(List<String> networkId){

    }

    //TODO TEST FUNCTION
    public static void removeAuthUserNetwork(String networkID, String userID){
        //query to find network
        Query query = db.collection("networks").whereEqualTo("networkID", networkID);
        //list of users found in db
        List<Network> foundNetworks = new ArrayList<Network>();

        //excuting query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //getting current auth users
                        List<String> s = (List<String>) document.get("authUsers");
                        //removing new networks
                        s.remove(s.indexOf(userID));
                        //querying back into db
                        document.getReference().update("authUsers", s).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    System.out.println("ERROR");
                                }
                            }
                        });
                        break;
                    }
                }  else {
                    System.err.println("USER COULD NOT BE FOUND");
                }
            }
        });
    }

}
