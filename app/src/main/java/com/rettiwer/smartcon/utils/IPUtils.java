package com.rettiwer.smartcon.utils;

import android.text.TextUtils;
import android.util.Log;

import com.rettiwer.smartcon.models.Subnet;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IPUtils {

    public static String[] getNetworkAddress(String host_address, String mask_address) {
        String[] host = getPartedBinnaryAddress(host_address);
        String[] mask = getPartedBinnaryAddress(mask_address);

        int first_zero = 0;
        int which_part;
        for (which_part = mask.length; which_part > 0; which_part--) {
            first_zero = mask[which_part-1].lastIndexOf("1");
            if (first_zero == -1) continue;
            else break;
        }

        String[] network_address = host;
        for (int part_start = mask.length; which_part <= part_start; part_start--) {
            if (part_start != which_part)
                network_address[part_start-1] = network_address[part_start-1].replaceAll(".", "0");
            else {
                first_zero += 1;
                StringBuilder myName = new StringBuilder(network_address[part_start-1]);
                for (int firt_zero_end = network_address[part_start-1].length(); first_zero < firt_zero_end; firt_zero_end--) {
                    myName.setCharAt(firt_zero_end-1, '0');
                }
                network_address[part_start-1] = myName.toString();
            }
        }
        return network_address;
    }


    public static String getBroadcastAddress(String host_address, String mask_address) {
        String[] host = getPartedBinnaryAddress(host_address);
        String[] mask = getPartedBinnaryAddress(mask_address);

        int first_zero = 0;
        int which_part;
        for (which_part = mask.length; which_part > 0; which_part--) {
            first_zero = mask[which_part-1].lastIndexOf("1");
            if (first_zero == -1) continue;
            else break;
        }

        String[] network_address = host;
        for (int part_start = mask.length; which_part <= part_start; part_start--) {
            if (part_start != which_part)
                network_address[part_start-1] = network_address[part_start-1].replaceAll(".", "1");
            else {
                first_zero += 1;
                StringBuilder myName = new StringBuilder(network_address[part_start-1]);
                for (int firt_zero_end = network_address[part_start-1].length(); first_zero < firt_zero_end; firt_zero_end--) {
                    myName.setCharAt(firt_zero_end-1, '1');
                }
                network_address[part_start-1] = myName.toString();
            }
        }
        return getPartedAddressFromBinary(network_address);
    }


    public static int getHostAmount(String mask_address) {
        String[] mask = getPartedBinnaryAddress(mask_address);

        int zero_amount = 0;
        for (int which_part = mask.length; which_part > 0; which_part--) {
            for (int i = mask[which_part-1].length(); i > 0; i--) {
                if (mask[which_part-1].charAt(i-1) == '0') zero_amount++;
                else break;
            }
        }
        return (int) Math.pow(2, zero_amount) - 2;
    }

    public static int getSubnetsAmount(String mask_address) {
        String[] mask = getPartedBinnaryAddress(mask_address);
        int subnets_amount = 0;
        int which_part;
        for (which_part = mask.length; which_part > 0; which_part--) {
            int last_one = mask[which_part-1].lastIndexOf("1");
            if (last_one == -1) continue;
            else {
                for (int i = mask[which_part-1].length(); i > 0; i--) {
                    if (mask[which_part-1].charAt(i-1) == '1') subnets_amount++;
                }
                break;
            }
        }
        return (int) Math.pow(2, subnets_amount);
    }

    public static AddressClass getAddressClass(String address) {
        String first_octet = getPartedBinnaryAddress(address)[0];
        if (first_octet.startsWith("0"))
            return AddressClass.A;
        else if (first_octet.startsWith("10"))
            return AddressClass.B;
        else if (first_octet.startsWith("110"))
            return AddressClass.C;
        else if (first_octet.startsWith("1110"))
            return AddressClass.D;
        else if (first_octet.startsWith("1111"))
            return AddressClass.E;
        return null;
    }

    public static List<Subnet> getSubnets(String host_address, String mask_address) {
        String[] mask = getPartedBinnaryAddress(mask_address);

        int hosts_amount = 0;
        int subnets_amount = 0;
        int which_part;

        for (which_part = mask.length; which_part > 0; which_part--) {
            for (int i = mask[which_part-1].length(); i > 0; i--) {
                if (mask[which_part-1].charAt(i-1) == '0') hosts_amount++;
                else break;
            }
        }

        for (which_part = mask.length; which_part > 0; which_part--) {
            int last_one = mask[which_part-1].lastIndexOf("1");
            if (last_one == -1) continue;
            else {
                for (int i = mask[which_part-1].length(); i > 0; i--) {
                    if (mask[which_part-1].charAt(i-1) == '1') subnets_amount++;
                }
                break;
            }
        }

        hosts_amount = (int) Math.pow(2, hosts_amount);
        subnets_amount = (int) Math.pow(2, subnets_amount);

        List<Subnet> subnets = new ArrayList<>();


        String[] network_address = getNetworkAddress(host_address, mask_address);
        for (int i = 0; i < subnets_amount; i++) {
            Subnet s = new Subnet();
            s.setID(i);
            network_address[which_part-1] = String.format("%8s", NumeralSystemsUtils.toBin(BigInteger.valueOf(hosts_amount * i))).replace(' ', '0');
            s.setSubnetAddress(getPartedAddressFromBinary(network_address));

            network_address[which_part-1] = String.format("%8s", NumeralSystemsUtils.toBin(BigInteger.valueOf((hosts_amount * i) + 1))).replace(' ', '0');
            s.setFirstHostAddress(getPartedAddressFromBinary(network_address));

            network_address[which_part-1] = String.format("%8s", NumeralSystemsUtils.toBin(BigInteger.valueOf((hosts_amount * (i+1)) - 2))).replace(' ', '0');
            s.setLastHostAddress(getPartedAddressFromBinary(network_address));

            network_address[which_part-1] = String.format("%8s", NumeralSystemsUtils.toBin(BigInteger.valueOf((hosts_amount * (i+1)) - 1))).replace(' ', '0');
            s.setBroadcastAddress(getPartedAddressFromBinary(network_address));
            subnets.add(s);
        }
        return subnets;
    }

    public static List<Subnet> getSubnetsB(String host_address, String mask_address) {
        String[] mask = getPartedBinnaryAddress(mask_address);

        int first_one = 0;
        int address_part = 0;

        for (address_part = mask.length; address_part > 0; address_part--) {
            int last_one = mask[address_part-1].lastIndexOf("1");
            if (last_one == -1) continue;
            else {
                first_one = last_one+1;
                break;
            }
        }

        int subnets_amount = (int) Math.pow(2, mask[address_part-1].length() - mask[address_part-1].replace("1", "").length());

        List<Subnet> subnets = new ArrayList<>();

        String[] network_address = getNetworkAddress(host_address, mask_address);
        for (int i = 0; i < subnets_amount; i++) {
            String[] subnet = network_address.clone();
            Subnet s = new Subnet();
            s.setID(i);

            String subnet_part = subnet[address_part-1].substring(0,first_one-1);
            subnet_part = String.format("%"+ first_one + "s", NumeralSystemsUtils.toBin(BigInteger.valueOf(i))).replace(' ', '0');
            subnet_part += subnet[address_part-1].substring(first_one);

            subnet[address_part-1] = subnet_part;

            s.setSubnetAddress(getPartedAddressFromBinary(subnet));

            subnet[subnet.length-1] = subnet[subnet.length-1].substring(0, subnet[subnet.length-1].length()-1) + "1";
            s.setFirstHostAddress(getPartedAddressFromBinary(subnet));

            for(int j = address_part; j <= subnet.length; j++) {
                if (j == address_part && j == subnet.length) {
                    String part = subnet[address_part-1].substring(first_one,7).replaceAll("0", "1");
                    subnet[address_part-1] = subnet[address_part-1].substring(0, first_one) + part + 0;
                }
                else if (j == address_part) {
                    String part = subnet[address_part-1].substring(first_one,8).replaceAll("0", "1");
                    subnet[address_part-1] = subnet[address_part-1].substring(0, first_one) + part;
                }
                else if (j == subnet.length) {
                    String part = subnet[j-1].substring(0,7).replaceAll("0", "1");
                    subnet[j-1] = part + 0;
                }
                else {
                    subnet[j-1] = subnet[j-1].replaceAll("0", "1");
                }
            }
            s.setLastHostAddress(getPartedAddressFromBinary(subnet));

            subnet[subnet.length-1] = subnet[subnet.length-1].substring(0, subnet[subnet.length-1].length()-1) + "1";
            s.setBroadcastAddress(getPartedAddressFromBinary(subnet));
            subnets.add(s);
        }
        return subnets;
    }

    public static String[] getPartedBinnaryAddress(String address) {
        String[] parted_address = address.split("\\.");
        for (int i = 0; i < parted_address.length; i++) {
            parted_address[i] = String.format("%8s", NumeralSystemsUtils.toBin(new BigInteger(parted_address[i]))).replace(' ', '0');
        }
        return parted_address;
    }

    public static String[] getPartedAddress(String address) {
        return address.split("\\.");
    }

    public static String getPartedAddressFromBinary(String[] address) {
        String[] new_address = address.clone();
        for(int i = 0; i < new_address.length; i++) {
            new_address[i] = String.valueOf(Integer.parseInt(new_address[i], 2));
          //  address[i] = NumeralSystemsUtils.toDec(address[i], NumeralSystemsUtils.NumeralSystem.BIN);
        }
        return TextUtils.join(".", new_address);
    }

    public static boolean isAddressValid(String address) {
        Log.d("test", "1");
        if(address == "") return false;

        String[] splited_address = address.split("\\.");
        Log.d("test", "2");
        if (splited_address.length != 4) return false;

        for(String s : splited_address) {
            Log.d("test", "3");
            if (s == "") return false;

            if (s.length() < 0 && s.length() > 3) return false;
            Log.d("test", "4");
            if (Integer.valueOf(s) > 255 || Integer.valueOf(s) < 0) return false;
            Log.d("test", "6");
        }
        return true;
    }

    enum AddressClass {
        A,
        B,
        C,
        D,
        E
    }
}
