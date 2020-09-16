package com.rettiwer.smartcon.utils;

import java.math.BigInteger;

/**
 * Created by retti on 28.04.2018.
 */

public class NumeralSystemsUtils {

    public static String toBin(BigInteger integer) {
        return integer.toString(2);
    }

    public static String toHex(BigInteger integer) {
        return integer.toString(16);
    }

    public static String toOct(BigInteger integer) {
        return integer.toString(8);
    }

    public static String toDec(String integer, NumeralSystem numeralSystem) {
        BigInteger bigInteger;
        switch (numeralSystem) {
            case BIN:
                bigInteger = new BigInteger(integer, 2);
                return bigInteger.toString();
            case HEX:
                bigInteger = new BigInteger(integer, 16);
                return bigInteger.toString();
            case OCT:
                bigInteger = new BigInteger(integer, 8);
                return bigInteger.toString();
        }
        return null;
    }

    enum NumeralSystem {
        HEX,
        DEC,
        BIN,
        OCT
    }
}
