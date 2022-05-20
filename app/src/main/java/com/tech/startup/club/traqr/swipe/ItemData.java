package com.tech.startup.club.traqr.swipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.tech.startup.club.traqr.R;
import com.tech.startup.club.traqr.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ItemData extends AppCompatActivity {

    private static FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    String itemId, networkId, userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_data);
        mAuth = FirebaseAuth.getInstance();
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();

        //Getting passed in variables
//        Item item = (Item) bundle.getSerializable("value");
//        System.out.println(item.toString());
        //display variables
        String x = bundle.getString("value");
        String[] tests = x.split(";");
        System.out.println(x);
        itemId = tests[2];
        networkId = tests[1];
        userId = mAuth.getCurrentUser().getUid();
        scanItemInfo(itemId, networkId, userId, ItemData.this);

    }

    public String getItemID() {
        return(itemId);
    }

    //Getting item info
    public void scanItemInfo(String itemID, String networkID, String userID, Context context){
        //query to find network
        Query query = db.collection("items").whereEqualTo("itemID", itemID);//TODO check matching network ID
        //list of users found in db
        //List<Item> foundItems = new ArrayList<Item>();
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

                        //System.out.println(document.get("name"));
                        //System.out.println("HELLOs");
                        Item newItem = Item.toItem(document.getData());

                        //Passing info to new intent * need to fix
                        final TextView name = (TextView)findViewById(R.id.name);
                        name.setText(newItem.getName());
                        final TextView networkID = (TextView)findViewById(R.id.networkID);
                        networkID.setText(newItem.getNetworkID());
                        final TextView lastScannedUser = (TextView)findViewById(R.id.lastScannedUser);
                        lastScannedUser.setText(newItem.getLastScannedUserID());
                        final TextView fields = (TextView)findViewById(R.id.fields);
                        fields.setText(newItem.getFields().toString());
                        /*String tempField = "";
                        for (int i = 0; i < newItem.fields.size(); i++) {
                            tempField += newItem.fields[] + ",\n";
                        }*/
                        break;
                    }
                }  else {
                    System.err.println("ITEM COULD NOT BE FOUND");
                }
            }
        });
    }
}