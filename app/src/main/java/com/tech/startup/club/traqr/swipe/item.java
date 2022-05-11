package com.tech.startup.club.traqr.swipe;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.tech.startup.club.traqr.R;
import com.tech.startup.club.traqr.homepage.Camera;
import com.tech.startup.club.traqr.utils.ItemInfo;

public class item extends AppCompatActivity {
    float x1;
    float x2;
    float y1;
    float y2;
    private Button addItem;
    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;


    //Button to switch to add new item activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        addItem = findViewById(R.id.addNewItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(item.this, AddItem.class);
                startActivity(i);
            }
        });

        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirestoreList = findViewById(R.id.firestore_list);

        //Query
        Query query = firebaseFirestore.collection("items");

        // Configure recycler adapter options:
//  * query is the Query object defined above.
//  * Chat.class instructs the adapter to convert each DocumentSnapshot to a Chat object
        FirestoreRecyclerOptions<ItemInfo> options = new FirestoreRecyclerOptions.Builder<ItemInfo>()
                .setQuery(query, ItemInfo.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<ItemInfo, ItemViewHolder>(options) {
            @Override
            public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single, parent, false);
                return new ItemViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull ItemInfo model) {
                holder.list_itemid.setText(model.getItemID());
                holder.list_lastScannedUserID.setText(model.getLastScannedUserId());
                holder.list_name.setText(model.getName());
            }
        };

        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);
    }

    //swipe back to main homepage
        public boolean onTouchEvent (MotionEvent touchEvent){
            switch (touchEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x1 = touchEvent.getX();
                    y1 = touchEvent.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    x2 = touchEvent.getX();
                    y2 = touchEvent.getY();
                    if (x1 > x2) {
                        Intent i = new Intent(item.this, Camera.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                    break;
            }
            return false;
        }

    private class ItemViewHolder extends RecyclerView.ViewHolder{

        private TextView list_itemid;
        private TextView list_lastScannedUserID;
        private TextView list_name;

        public ItemViewHolder(@NonNull View itemView){
            super(itemView);

            list_itemid = itemView.findViewById(R.id.list_itemid);
            list_lastScannedUserID = itemView.findViewById(R.id.list_lastScannedUserID);
            list_name = itemView.findViewById(R.id.list_name);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }
}