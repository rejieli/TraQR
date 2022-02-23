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

import com.tech.startup.club.traqr.R;

public class Camera extends AppCompatActivity {
    ImageView imageview;
    Button btOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        imageview = findViewById(R.id.imageView3);
        btOpen = findViewById(R.id.bt_open);

        btOpen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent open = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(open, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap photo = (Bitmap)data.getExtras().get("data");
        imageview.setImageBitmap(photo);
    }
}