package com.rettiwer.smartcon.models;

/**
 * Created by retti on 27.05.2018.
 */

public class Network {
    private String network_address;
    private String mask_address;
    private String broadcast_address;
    private int hosts_amount;
    private int subnets_amount;
    private String address_class;

    public String getNetworkAddress() {
        return network_address;
    }

    public void setNetworkAddress(String network_address) {
        this.network_address = network_address;
    }

    public String getMaskAddress() {
        return mask_address;
    }

    public void setMaskAddress(String mask_address) {
        this.mask_address = mask_address;
    }

    public String getBroadcastAddress() {
        return broadcast_address;
    }

    public void setBroadcastAddress(String broadcast_address) {
        this.broadcast_address = broadcast_address;
    }

    public int getHostsAmount() {
        return hosts_amount;
    }

    public void setHostsAmount(int hosts_amount) {
        this.hosts_amount = hosts_amount;
    }

    public int getSubnetsAmount() {
        return subnets_amount;
    }

    public void setSubnetsAmount(int subnets_amount) {
        this.subnets_amount = subnets_amount;
    }

    public String getAddress_class() {
        return address_class;
    }

    public void setAddress_class(String address_class) {
        this.address_class = address_class;
    }
}
