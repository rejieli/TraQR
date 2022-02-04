package com.tech.startup.club.traqr.homepage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.tech.startup.club.traqr.R;

public class Camera extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mAuth = FirebaseAuth.getInstance();

        TextView tv1 = (TextView)findViewById(R.id.textView);

        tv1.setText(mAuth.getCurrentUser().getUid());

    }
}