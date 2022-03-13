package com.tech.startup.club.traqr.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Network {

    private String networkName;
    private final String networkID;
    private String manager;
    private List<String> authUsers;

    public Network(String networkName, String manager, String networkID, List<String> authUsers){
        this.networkName = networkName;
        this.manager = manager;
        this.networkID = networkID;
        authUsers = new ArrayList<String>();
    }

    public Network(String networkName, String manager, String networkID){
        this(networkName, manager, networkID, Arrays.asList());
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

    public List<String> getAuthUsers() {
        return authUsers;
    }

    public void setAuthUsers(List<String> authUsers) {
        this.authUsers = authUsers;
    }

    @Override
    public String toString() {
        return "Network{" +
                "networkName='" + networkName + '\'' +
                ", networkID='" + networkID + '\'' +
                ", manager='" + manager + '\'' +
                ", authUsers=" + authUsers +
                '}';
    }
}
