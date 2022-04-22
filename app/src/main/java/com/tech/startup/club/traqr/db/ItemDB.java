package com.tech.startup.club.traqr.db;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tech.startup.club.traqr.homepage.Camera;
import com.tech.startup.club.traqr.model.Item;
import com.tech.startup.club.traqr.model.Network;
import com.tech.startup.club.traqr.swipe.ItemData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ItemDB {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static List<Item> items = new ArrayList<Item>();

    //Adds item into db
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

    public static void queryAllItemsInSelectedDB(Context context){
        //query to find network
        if(UserDB.getSelectedNetwork().equals("")) return;
        Query query = db.collection("items").whereEqualTo("networkID", UserDB.getSelectedNetwork());//TODO check matching network ID
        //clearing current items
        items = new ArrayList<Item>();

        //TODO Add loading animation

        //excuting query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //getting item
                        Item item = document.toObject(Item.class);
                        items.add(item);
                    }
                }  else {
                    System.err.println("ITEMS COULD NOT BE FOUND");
                }
            }
        });
    }

    //Getting item info
    public static void scanItemInfo(String itemID, String networkID, String userID, Context context){
        //query to find network
        Query query = db.collection("items").whereEqualTo("itemID", itemID);//TODO check matching network ID
        //list of users found in db
        List<Item> foundItems = new ArrayList<Item>();
        System.out.println(itemID);
        //TODO Add loading animation
        //excuting query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {

                        //changing lastScanned timestamp
                        document.getReference().update("lastScanned", Timestamp.now()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (!task.isSuccessful()) {
                                    System.out.println("ERROR UPDATING TIME STAMP");
                                }
                            }
                        });
                        System.out.println(document.get("name"));
                        Item item = Item.toItem(document.getData());

                        //Passing info to new intent
                        Intent intent = new Intent(context.getApplicationContext(), ItemData.class);
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("value", item);
                        intent.putExtra("Item", bundle);
                        context.startActivity(intent);
                        break;
                    }
                }  else {
                    System.err.println("ITEM COULD NOT BE FOUND");
                }
            }
        });
    }

    //Edit item info
    public static void updateItemInfo(Item item, Context context){
        //query to find network
        Query query = db.collection("items").whereEqualTo("itemID", item.getItemID());//TODO check matching network ID
        //list of users found in db
        List<Item> foundItems = new ArrayList<Item>();

        //excuting query
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        //getting current auth users

                        //querying back into db
                        document.getReference().update("name", item.getName(), "fields", item.getFields()).addOnCompleteListener(new OnCompleteListener<Void>() {
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
                    System.err.println("ITEM COULD NOT BE FOUND");
                }
            }
        });
    }
}
