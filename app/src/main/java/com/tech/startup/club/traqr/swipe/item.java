package com.tech.startup.club.traqr.swipe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.tech.startup.club.traqr.R;
import com.tech.startup.club.traqr.homepage.Camera;

public class item extends AppCompatActivity {
    float x1;
    float x2;
    float y1;
    float y2;
    private Button addItem;


    //Button to switch to add new item activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        addItem = findViewById(R.id.addNewItem);
        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(item.this, AddItem.class);
                startActivity(i);
            }
        });
    }

    //swipe back to main homepage
        public boolean onTouchEvent (MotionEvent touchEvent){
            switch (touchEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    x1 = touchEvent.getX();
                    y1 = touchEvent.getY();
                    break;
                case MotionEvent.ACTION_UP:
                    x2 = touchEvent.getX();
                    y2 = touchEvent.getY();
                    if (x1 > x2) {
                        Intent i = new Intent(item.this, Camera.class);
                        startActivity(i);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                    break;
            }
            return false;
        }
    }