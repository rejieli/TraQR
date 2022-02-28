package com.tech.startup.club.traqr.model;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class User {

    //password is not required to be stored here, as firebase takes care of that securely
    private String name;
    private String email;
    private final String uuid;
    private List<String> networks;

    public User(String name, String email, String uuid, List<String> networks) {
        this.name = name;
        this.email = email;
        this.uuid = uuid;
        this.networks = networks;
    }

    public User(String name, String email, String uuid) {
        this(name,email,uuid, Arrays.asList());
    }

    //MAIN Constructor
    public User(String name, String email) {
        this(name,email, UUID.randomUUID().toString(), Arrays.asList());
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUuid() {
        return uuid;
    }

    public List<String> getNetworks() {
        return networks;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void addNetwork(Network network) {
        networks.add(network.getNetworkID());
    }
}
