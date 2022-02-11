package com.tech.startup.club.traqr.homepage;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import com.google.firebase.auth.FirebaseAuth;
import com.tech.startup.club.traqr.R;
import java.lang.Object;
public class Camera extends Object {

    private FirebaseAuth mAuth;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        mAuth = FirebaseAuth.getInstance();

        TextView tv1 = (TextView)findViewById(R.id.textView);

        tv1.setText(mAuth.getCurrentUser().getUid());

    }

    /** A safe way to get an instance of the Camera object. */
    public static Camera getCameraInstance(){
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        }
        catch (Exception e){
            // Camera is not available (in use or does not exist)
        }
        return c; // returns null if camera is unavailable
    }

}