package com.tech.startup.club.traqr.swipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tech.startup.club.traqr.R;
import com.tech.startup.club.traqr.db.UserDB;
import com.tech.startup.club.traqr.homepage.Camera;
import com.tech.startup.club.traqr.network.AddNetwork;
import com.tech.startup.club.traqr.welcome.welcome;

import java.util.List;

public class profile extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private float x1;
    private float x2;
    private float y1;
    private float y2;
    private List<String> networks = UserDB.getUserNetworks();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //getting current networks
        UserDB.getUserNetworks(mAuth.getCurrentUser());

        //CHECK IF USER IS SIGNED OUT
        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(getApplicationContext(), welcome.class);
            startActivity(intent);
            Toast.makeText(getApplicationContext(), "Not signed in!",
                    Toast.LENGTH_SHORT).show();
        }

        //set logout button
        final Button logoutButton = (Button) findViewById(R.id.logout);

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //Sign out
                    mAuth.signOut();
                    //go back to home screen
                    Intent intent = new Intent(profile.this, welcome.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(profile.this, "Successfully Signed Out!",
                            Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        initDropDownMenu();
    }

    public void initDropDownMenu() {
        //Getting user db info
        networks = UserDB.getUserNetworks();

        if(networks.size()==0){
            networks.add("NO NETWORKS FOUND");
        }

        //drop down menu
        Spinner spin = findViewById(R.id.networkSelector);
        spin.setOnItemSelectedListener(this);
        System.out.println(UserDB.getUserNetworks());
        ArrayAdapter ad
                = new ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item,
                UserDB.getUserNetworks());

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
                android.R.layout
                        .simple_spinner_dropdown_item);

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        spin.setAdapter(ad);

    }

    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if (x1 < x2) {
                    Intent i = new Intent(profile.this, Camera.class);
                    startActivity(i);
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                }
                break;
        }
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(networks.get(position).equals("NO NETWORKS FOUND")) {
            UserDB.setSelectedNetwork("");
        }else{
            UserDB.setSelectedNetwork(UserDB.getUserNetworks().get(position));
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        UserDB.setSelectedNetwork("");
    }
}