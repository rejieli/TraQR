package com.tech.startup.club.traqr.swipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        System.out.println("HELLLOO");

        //Getting passed in variables
        Item item = (Item) bundle.getSerializable("value");
        System.out.println(item.toString());
        //display variables
        final TextView data = (TextView) findViewById(R.id.data);

        data.setText(item.toString());

    }
}