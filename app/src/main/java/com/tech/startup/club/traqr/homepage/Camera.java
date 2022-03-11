package com.tech.startup.club.traqr.homepage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.tech.startup.club.traqr.QRCode.QRCodeGenerator;
import com.tech.startup.club.traqr.R;

public class Camera extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Button qrCode;
    ImageView imageview;
    Button btOpenCam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        imageview = findViewById(R.id.imageView3);
        btOpenCam = findViewById(R.id.bt_cam);

        if(ContextCompat.checkSelfPermission(Camera.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Camera.this, new String[]{
                    Manifest.permission.CAMERA
            }, 100);
        }



        //temporary qrcode button
        qrCode = (Button) findViewById(R.id.qrcode);
        qrCode.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QRCodeGenerator.class);
                startActivity(intent);
            }
        });

        btOpenCam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                IntentIntegrator intentIntegrator = new IntentIntegrator(Camera.this
                );
                intentIntegrator.setBeepEnabled(true);
                intentIntegrator.setOrientationLocked(true);
                intentIntegrator.setCaptureActivity(Capture.class);
                intentIntegrator.initiateScan();
                }
            });
        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intRes = IntentIntegrator.parseActivityResult(requestCode,resultCode,data
        );

        if(intRes.getContents() != null){
            AlertDialog.Builder build = new AlertDialog.Builder(
                    Camera.this
            );
            build.setTitle("Result");
            build.setMessage(intRes.getContents());
            build.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            build.show();
        }else{
            Toast.makeText(getApplicationContext(), "No input",Toast.LENGTH_SHORT).show();
        }
    }
}

