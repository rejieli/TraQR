package com.tech.startup.club.traqr.db;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.tech.startup.club.traqr.Signin.Sign_Up;

import java.util.Arrays;
import java.util.HashMap;

public class userDB {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    //tracks user into
    public static void trackUser(FirebaseUser user, String name, Context context){
        HashMap<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("email", user.getEmail());
        userData.put("uuid", user.getUid());
        userData.put("networks", Arrays.asList());

        // Add a new document with a generated ID
        db.collection("users")
                .add(userData)
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

}
