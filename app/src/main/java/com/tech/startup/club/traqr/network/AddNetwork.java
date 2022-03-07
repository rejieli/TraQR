package com.tech.startup.club.traqr.network;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tech.startup.club.traqr.QRCode.QRCodeGenerator;
import com.tech.startup.club.traqr.R;
import com.tech.startup.club.traqr.db.NetworkDB;
import com.tech.startup.club.traqr.model.Network;

public class AddNetwork extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_network2);

        //display variables
        final EditText networkName = (EditText)findViewById(R.id.networkName);
        final Button addNetworkButton = (Button) findViewById(R.id.addNetwork);

        //Getting logged in user
        mAuth = FirebaseAuth.getInstance();

        //Action listener for creating new button
        addNetworkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Creating new network
                    Network network = new Network(networkName.getText().toString(), mAuth.getUid());
                    NetworkDB.createNetwork(network,AddNetwork.this);

                    //Goes back to QR code generator
                    Intent intent = new Intent(getApplicationContext(), QRCodeGenerator.class);
                    startActivity(intent);

                    //TODO make sure the network names are unique

                }catch(Exception e){
                    //If Failed
                    Toast.makeText(AddNetwork.this, "Failed to create Network",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}