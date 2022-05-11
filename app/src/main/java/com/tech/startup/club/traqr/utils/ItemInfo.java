package com.tech.startup.club.traqr.utils;

public class ItemInfo {
    private String itemID;
//    private String lastScanned;
    private String lastScannedUserId;
    private String name;

    public ItemInfo(){

    }
    public ItemInfo(String itemId, String lastScanned, String lastScannedUserId, String name){
        this.itemID = itemId;
//        this.lastScanned = lastScanned;
        this.lastScannedUserId = lastScannedUserId;
        this.name = name;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

//    public String getLastScanned() {
//        return lastScanned.toString();
//    }
//
//    public void setLastScanned(String lastScanned) {
//        this.lastScanned = lastScanned;
//    }

    public String getLastScannedUserId() {
        return lastScannedUserId;
    }

    public void setLastScannedUserId(String lastScannedUserId) {
        this.lastScannedUserId = lastScannedUserId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
