package com.tech.startup.club.traqr.db;

import android.content.Context;
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
import com.tech.startup.club.traqr.Signin.Sign_Up;
import com.tech.startup.club.traqr.model.Network;
import com.tech.startup.club.traqr.model.User;
import com.tech.startup.club.traqr.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDB {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    //ONLY ADDS USER TO DB, NOT USER DB
    public static void createUser(User user, Context context){
        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "Successfully created account!",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to create account.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

        public static void addUserNetworks(FirebaseUser user, Network network){
        //generating query
        Query query = db.collection("users").whereEqualTo("uuid", user.getUid());
        //list of users found in db
        List<User> foundUsers = new ArrayList<User>();

        //excuting query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //getting current networks
                        List<String> s = (List<String>) document.get("networks");
                        //adding new networks
                        s.add(network.getNetworkID());
                        //querying back into db
                        document.getReference().update("networks", s).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    //TODO TEST METHOD
    public static void removeNetworkFromUser(FirebaseUser user, Network network){
        //generating query
        Query query = db.collection("users").whereEqualTo("uuid", user.getUid());
        //list of users found in db
        List<User> foundUsers = new ArrayList<User>();

        //excuting query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //getting current networks
                        List<String> s = (List<String>) document.get("networks");
                        //removing new networks
                        s.remove(s.indexOf(network.getNetworkID()));
                        //querying back into db
                        document.getReference().update("networks", s).addOnCompleteListener(new OnCompleteListener<Void>() {
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

    //TODO TEST METHOD
    public static void getUserNetworks(FirebaseUser user){
        //generating query
        Query query = db.collection("users").whereEqualTo("uuid", user.getUid());
        //list of users found in db
        List<User> foundUsers = new ArrayList<User>();

        //excuting query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //getting current networks
                        List<String> s = (List<String>) document.get("networks");
                        break;
                    }
                }  else {
                    System.err.println("USER COULD NOT BE FOUND");
                }
            }
        });
    }

}
