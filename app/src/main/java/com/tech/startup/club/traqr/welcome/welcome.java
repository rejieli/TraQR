package com.tech.startup.club.traqr.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tech.startup.club.traqr.R;
import com.tech.startup.club.traqr.Signin.Sign_Up;
import com.tech.startup.club.traqr.db.UserDB;
import com.tech.startup.club.traqr.homepage.Camera;
import com.tech.startup.club.traqr.ui.login.LoginActivity;

public class welcome extends AppCompatActivity {

    private Button login;
    private Button signIn;
    private Button signUp;
    private Button qrCode;
    //Test comment

    private static final int RC_SIGN_IN = 100;//TODO change
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private GoogleSignInClient mGoogleSignInClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient  = GoogleSignIn.getClient(this,gso);

        //check if user is already logged in
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user!=null){//if there is user logged in
            //getting user data
            UserDB.getUserNetworks(user);
            Intent intent = new Intent(getApplicationContext(), Camera.class);
            startActivity(intent);
        }//otherwise, keep on same page

        //login
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.activity_item_data);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //sign Up
        signUp = (Button) findViewById(R.id.signUp);
        signUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sign_Up.class);
                startActivity(intent);
            }
        });
    }

}