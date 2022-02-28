package com.tech.startup.club.traqr.model;

import java.util.HashMap;
import java.util.UUID;

public class Network {

    private String networkName;
    private final String networkID;
    private String manager;

    public Network(String networkName, String manager, String networkID){
        this.networkName = networkName;
        this.manager = manager;
        this.networkID = networkID;
    }

    public Network(String networkName, String manager){
        this(networkName,manager, UUID.randomUUID().toString());
    }

    public String getNetworkName(){
        return networkName;
    }

    public String getNetworkID(){
        return networkID;
    }

    public String getManager(){
        return manager;
    }

    public void setNetworkName(String networkName){
        this.networkName = networkName;
    }

    public void setManager(String manager){
        this.manager = manager;
    }

    public HashMap<String, Object> toHashMap(){
        HashMap<String, Object> map = new HashMap<>();
        map.put("networkName", networkName);
        map.put("networkID", networkID);
        map.put("manager", manager);
        return map;
    }

}
