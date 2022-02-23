package com.tech.startup.club.traqr.homepage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.tech.startup.club.traqr.R;

public class Camera extends AppCompatActivity {
    private FirebaseAuth mAuth;
    ImageView imageview;
    Button btOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        imageview = findViewById(R.id.imageView3);
        btOpen = findViewById(R.id.bt_cam);
        mAuth = FirebaseAuth.getInstance();
        TextView tv1 = (TextView)findViewById(R.id.textcam);
        tv1.setText(mAuth.getCurrentUser().getUid());


    }
}