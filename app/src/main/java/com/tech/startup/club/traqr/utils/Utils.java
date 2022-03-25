package com.tech.startup.club.traqr.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.tech.startup.club.traqr.swipe.profile;
import com.tech.startup.club.traqr.welcome.welcome;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class Utils {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static List<String> convertObjectToList(Object obj) {
        List<String> list = new ArrayList<>();
        if (obj.getClass().isArray()) {
            list = Arrays.asList((String[])obj);
        } else if (obj instanceof Collection) {
            list = new ArrayList<String>((Collection<String>)obj);
        }
        return list;
    }

    public static byte[] stringToByteArray(String s){
        //Convert to array
        List<String> list = Arrays.asList(s.substring(1, s.length() - 1).split(", "));
        byte[] byteArray = new byte[list.size()];

        //to byte array
        for(int i = 0; i < list.size(); i++){
            byteArray[i] = (byte) Integer.parseInt(list.get(i));
        }
        return byteArray;

    }


}
