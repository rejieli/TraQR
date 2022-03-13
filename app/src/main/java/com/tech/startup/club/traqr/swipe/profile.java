package com.tech.startup.club.traqr.swipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.tech.startup.club.traqr.R;
import com.tech.startup.club.traqr.homepage.Camera;
import com.tech.startup.club.traqr.network.AddNetwork;
import com.tech.startup.club.traqr.welcome.welcome;

public class profile extends AppCompatActivity {
    private float x1;
    private float x2;
    private float y1;
    private float y2;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //CHECK IF USER IS SIGNED OUT
            if(FirebaseAuth.getInstance().getCurrentUser()==null) {
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
                    Intent intent = new Intent(profile.this,welcome.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    Toast.makeText(profile.this, "Successfully Signed Out!",
                            Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });

    }

    public boolean onTouchEvent(MotionEvent touchEvent){
        switch(touchEvent.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();
                if(x1 < x2){
                    Intent i = new Intent(profile.this, Camera.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}