package com.tech.startup.club.traqr.db;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.protobuf.Timestamp;
import com.tech.startup.club.traqr.model.Item;

import java.util.HashMap;
import java.util.UUID;

public class ItemDB {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();

    //tracks user into
    public static void addItem(Item item, Context context){

        // Add a new document with a generated ID
        db.collection("items")
                .add(item)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(context, "Item successfully added",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to add item",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
