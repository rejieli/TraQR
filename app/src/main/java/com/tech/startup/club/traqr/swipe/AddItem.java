package com.tech.startup.club.traqr.swipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tech.startup.club.traqr.QRCode.QRCodeGenerator;
import com.tech.startup.club.traqr.QRCode.QREncrypt;
import com.tech.startup.club.traqr.R;
import com.tech.startup.club.traqr.db.ItemDB;
import com.tech.startup.club.traqr.db.UserDB;
import com.tech.startup.club.traqr.homepage.Camera;
import com.tech.startup.club.traqr.model.Item;
import com.tech.startup.club.traqr.network.AddNetwork;
import com.tech.startup.club.traqr.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
                String networkID = UserDB.getSelectedNetwork();
                if(UserDB.getSelectedNetwork().equals("")){
                    Toast.makeText(AddItem.this, "NO NETWORK SELECTED",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                String name = itemName.getText().toString();
                HashMap<String, Object> map = new HashMap<>();
                Item item = new Item(networkID, name, mAuth.getCurrentUser().getUid(), map);
                ItemDB.addItem(item, AddItem.this);
                try {
                    String encrypt = QREncrypt.encryptQRPlainText(networkID, item);
                    System.out.println("encrypt: " + encrypt);
                    String decrypt = QREncrypt.decryptQRPlainText(encrypt, networkID);
                    Intent i = new Intent(AddItem.this, QRCodeGenerator.class);
                    i.putExtra("Encrypt", encrypt);
                    startActivity(i);

                    System.out.println("decrypt: " + decrypt);
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