package com.tech.startup.club.traqr.swipe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.tech.startup.club.traqr.R;
import com.tech.startup.club.traqr.model.Item;

public class ItemData extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_data);

        //Getting passed in variables
        Item item = (Item) getIntent().getSerializableExtra("Item");

        //display variables
        final TextView data = (TextView) findViewById(R.id.data);

        data.setText(item.toString());

    }
}