package com.tech.startup.club.traqr.QRCode;

import android.util.Base64;

import com.tech.startup.club.traqr.db.UserDB;
import com.tech.startup.club.traqr.model.Item;
import com.tech.startup.club.traqr.model.Network;
import com.tech.startup.club.traqr.utils.Utils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class QREncrypt {

    private static String encrpytAlgo = "Blowfish";

    public static String encryptQRPlainText(String networkID, Item item) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("TraQR;" + networkID + ";" + item.getItemID());
        String encryptedData = Arrays.toString(encrypt(sb.toString(), networkID));
        return encryptedData;
    }

    public static String decryptQRPlainText(String encryptedText, String networkID) throws Exception {
        //checking all avalible networks
        List<String> avaliableNetworks = UserDB.getUserNetworks();
        //ensuring there is at least one network
        if(avaliableNetworks.size()==0){
            return "";
        }
        //checking all networks to see if item exist
        for(int i = 0; i < avaliableNetworks.size(); i++){
            try {
                System.out.println(encryptedText);
                System.out.println(Arrays.toString(Utils.stringToByteArray(encryptedText)));
                String code = decrypt(Utils.stringToByteArray(encryptedText), avaliableNetworks.get(i));
                System.out.println(code);
                if (code.length() > 6 && code.substring(0, 6).contains("TraQR")) {
                    System.out.println(code);
                    return code;
                }
            }catch(Exception e){
//                System.err.println("ERROR");
            }
        }
        return "";
    }


    private static byte[] encrypt(String strClearText,String strKey) throws Exception{
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

    private static String decrypt(byte[] strEncrypted,String strKey) throws Exception{
        String strData="";
            SecretKeySpec skeyspec=new SecretKeySpec(strKey.getBytes("UTF-8"),encrpytAlgo);
            Cipher cipher=Cipher.getInstance(encrpytAlgo);
            cipher.init(Cipher.DECRYPT_MODE, skeyspec);
            byte[] decrypted=cipher.doFinal(strEncrypted);
            strData=new String(decrypted);
        return strData;
    }



}
