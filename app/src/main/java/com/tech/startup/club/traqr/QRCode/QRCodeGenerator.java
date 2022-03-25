package com.tech.startup.club.traqr.QRCode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tech.startup.club.traqr.R;
import com.tech.startup.club.traqr.network.AddNetwork;
import com.tech.startup.club.traqr.swipe.AddItem;
import com.tech.startup.club.traqr.ui.login.LoginActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;
import androidmads.library.qrgenearator.QRGSaver;

public class QRCodeGenerator extends AppCompatActivity {
    private EditText qrvalue;
    private ImageView qrImage;
    private String savePath = Environment.getExternalStorageDirectory().getPath() + "/QRCode/";
    private Bitmap bitmap;
    private QRGEncoder qrgEncoder;
    private AppCompatActivity activity;
    public static final String TAG = "ERROR";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode_generator);

        qrImage = findViewById(R.id.qrPlaceHolder);

        String data = getIntent().getStringExtra("Encrypt");
        if (data.length() > 0) {
            WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = manager.getDefaultDisplay();
            Point point = new Point();
            display.getSize(point);
            int width = point.x;
            int height = point.y;
            int smallerDimension = Math.min(width, height);
            smallerDimension = smallerDimension * 3 / 4;
            QRGEncoder qrgEncoder = new QRGEncoder(data, null, QRGContents.Type.TEXT, smallerDimension);
            try {
                // Getting QR-Code as Bitmap
                bitmap = qrgEncoder.getBitmap();
                // Setting Bitmap to ImageView
                qrImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                Log.v(TAG, e.toString());
            }

        } else {
            qrvalue.setError("Error");
        }

        //Action Listener for save button
        findViewById(R.id.save_barcode).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        saveToInternalStorage(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
                }
            }
        });

        //Action listener for create network button
        findViewById(R.id.addNetwork).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddNetwork.class);
                startActivity(intent);
            }
        });
}
    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

}
