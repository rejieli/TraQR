package com.tech.startup.club.traqr.Signin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tech.startup.club.traqr.R;
import com.tech.startup.club.traqr.db.UserDB;
import com.tech.startup.club.traqr.homepage.Camera;
import com.tech.startup.club.traqr.model.User;
import com.tech.startup.club.traqr.ui.login.LoginActivity;

public class Sign_Up extends AppCompatActivity {

    private static final String TAG = "EmailPassword";
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        final EditText usernameEditText = (EditText)findViewById(R.id.username);
        final EditText emailEditText = (EditText)findViewById(R.id.email);
        final EditText passwordEditText = (EditText)findViewById(R.id.password);
        final EditText confirmPasswordEditText = (EditText)findViewById(R.id.confirm_password);
        final Button signInButton = (Button) findViewById(R.id.signUp);

        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                try {
                    if (passwordEditText.getText().toString().equals(confirmPasswordEditText.getText().toString())) {
                        //TODO if user exists
                        signUp(usernameEditText.getText().toString(),emailEditText.getText().toString(), passwordEditText.getText().toString());
                    }
                    else {
                        Toast.makeText(Sign_Up.this, "Passwords do not match",
                                Toast.LENGTH_SHORT).show();

                        passwordEditText.setText("");
                        confirmPasswordEditText.setText("");
                    }
                    /*Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);*/
                }catch(Exception e){

                }

            }
        });
    }

    private void signUp(String name, String email, String password) {
        final EditText usernameEditText = (EditText)findViewById(R.id.username);
        final EditText emailEditText = (EditText)findViewById(R.id.email);
        final EditText passwordEditText = (EditText)findViewById(R.id.password);
        final EditText confirmPasswordEditText = (EditText)findViewById(R.id.confirm_password);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //log
                            Log.d(TAG, "signInWithEmail:success");

                            //gets users auth
                            FirebaseUser user = mAuth.getCurrentUser();

                            //Create new User in DB (NOT PART OF AUTHENTICATION)
                            User newUser = new User(name, email);
                            UserDB.createUser(newUser, Sign_Up.this);

                            //Notify user of successful creation of new user
                            Toast.makeText(Sign_Up.this, user.getUid(),
                                    Toast.LENGTH_SHORT).show();

                            //Start new intent (start camera class)
                            Intent intent = new Intent(getApplicationContext(), Camera.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Sign_Up.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                            //clear usernames
                            usernameEditText.setText("");
                            emailEditText.setText("");
                            passwordEditText.setText("");
                            confirmPasswordEditText.setText("");
                        }
                    }
                });
    }

}