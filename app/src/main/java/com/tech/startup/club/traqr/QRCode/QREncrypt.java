package com.tech.startup.club.traqr.QRCode;

import android.util.Base64;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class QREncrypt {

    private static String encrpytAlgo = "Blowfish";

    //maybe convert to NetworkID Class
    public static byte[] encrypt(String strClearText,String strKey) throws Exception{
        byte[] encrypted = new byte[0];

        try {
            SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("UTF-8"),encrpytAlgo);
            Cipher cipher= Cipher.getInstance(encrpytAlgo);
            cipher.init(Cipher.ENCRYPT_MODE, skeyspec);
            encrypted=cipher.doFinal(strClearText.getBytes(StandardCharsets.UTF_8));

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return encrypted;
    }

    public static String decrypt(byte[] strEncrypted,String strKey) throws Exception{
        String strData="";

        try {
            SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("UTF-8"),encrpytAlgo);
            Cipher cipher=Cipher.getInstance(encrpytAlgo);
            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
            byte[] decrypted=cipher.doFinal(strEncrypted);
            strData=new String(decrypted);

        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(e);
        }
        return strData;
    }


}
