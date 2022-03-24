package com.tech.startup.club.traqr.swipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.tech.startup.club.traqr.QRCode.QREncrypt;
import com.tech.startup.club.traqr.R;
import com.tech.startup.club.traqr.db.ItemDB;
import com.tech.startup.club.traqr.homepage.Camera;
import com.tech.startup.club.traqr.model.Item;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

public class AddItem extends AppCompatActivity {
    private EditText itemName;
    private Button save;
    private Button addNewField;
    private FirebaseAuth mAuth;
    private EditText field1;
    private EditText field2;
    private EditText field3;
    private EditText field4;
    private EditText field5;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = findViewById(R.id.ItemNameField);
        save = findViewById(R.id.SaveButton);
        addNewField = findViewById(R.id.AddNewInfoField);

        mAuth = FirebaseAuth.getInstance();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = itemName.getText().toString();
                HashMap<String, Object> map = new HashMap<>();
                Item item = new Item("925889db-22f5-4d58-9887-e927742ae40f", name, mAuth.getCurrentUser().getUid(), map);
                ItemDB.addItem(item, AddItem.this);
                Intent i = new Intent(AddItem.this, item.class);
                startActivity(i);
                try {
                    String encrypt = QREncrypt.encryptQRPlainText("925889db-22f5-4d58-9887-e927742ae40f", item);
                    System.out.println("encrypt: " + encrypt);
//                    String decrypt = QREncrypt.decryptQRPlainText(encrypt, "925889db-22f5-4d58-9887-e927742ae40f");
                    System.out.println("decrypt: " + Arrays.toString(encrypt.getBytes(StandardCharsets.UTF_8)));
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        addNewField.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}