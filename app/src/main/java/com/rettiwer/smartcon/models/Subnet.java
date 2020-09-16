package com.rettiwer.smartcon.models;

/**
 * Created by retti on 28.04.2018.
 */

public class Subnet {
    private int id;
    private String subnet_address;
    private String broadcast_address;
    private String first_host_address;
    private String last_host_address;

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getSubnetAddress() {
        return subnet_address;
    }

    public void setSubnetAddress(String subnet_address) {
        this.subnet_address = subnet_address;
    }

    public String getBroadcastAddress() {
        return broadcast_address;
    }

    public void setBroadcastAddress(String broadcast_address) {
        this.broadcast_address = broadcast_address;
    }

    public String getFirstHostAddress() {
        return first_host_address;
    }

    public void setFirstHostAddress(String first_host_address) {
        this.first_host_address = first_host_address;
    }

    public String getLastHostAddress() {
        return last_host_address;
    }

    public void setLastHostAddress(String last_host_address) {
        this.last_host_address = last_host_address;
    }
}
