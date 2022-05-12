package com.tech.startup.club.traqr.model;

import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Item implements Serializable {

    private final String networkID;
    private final String itemID;
    private String name;
    private Timestamp lastScanned;
    private String lastScannedUserID;
    private HashMap<String, Object> fields;

    public Item(String networkID, String itemID, String name, Timestamp lastScanned, String lastScannedUserID, HashMap<String, Object> fields) {
        this.networkID = networkID;
        this.itemID = itemID;
        this.name = name;
        this.lastScanned = lastScanned;
        this.lastScannedUserID = lastScannedUserID;
        this.fields = fields;
    }

    public Item(String networkID, String name, String lastScannedUserID, HashMap<String, Object> fields) {
        this(networkID, UUID.randomUUID().toString(), name, Timestamp.now(), lastScannedUserID, fields);
    }

    public String getNetworkID() {
        return networkID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public Timestamp getLastScanned() {
        return lastScanned;
    }

    public String getLastScannedUserID() {
        return lastScannedUserID;
    }

    public HashMap<String, Object> getFields() {
        return fields;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLastScanned(Timestamp lastScanned) {
        this.lastScanned = lastScanned;
    }

    public void setLastScannedUserID(String lastScannedUserID) {
        this.lastScannedUserID = lastScannedUserID;
    }

    public void setFields(HashMap<String, Object> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "Item{" +
                "networkID='" + networkID + '\'' +
                ", itemID='" + itemID + '\'' +
                ", name='" + name + '\'' +
                ", lastScanned=" + lastScanned +
                ", lastScannedUserID='" + lastScannedUserID + '\'' +
                ", fields=" + fields +
                '}';
    }

    public static Item toItem(Map<String, Object> x){
        Item y = new Item((String)x.get("networkID"), (String) x.get("name"), (String) x.get("lastScannedUserID"), (HashMap<String, Object>) x.get("fields"));
        return y;
    }
}
