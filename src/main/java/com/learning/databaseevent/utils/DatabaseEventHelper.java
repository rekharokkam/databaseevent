package com.learning.databaseevent.utils;

import java.util.concurrent.ThreadLocalRandom;

public class DatabaseEventHelper {
    private static final int SET_SIZE = 100000;

    public static String uniqueIdentifier (){
        Long nanoSecs = System.nanoTime();
        Double randomNumber = ThreadLocalRandom.current().nextDouble(1000000, 99999999);
        String uniqueIdentifier = nanoSecs + String.valueOf(randomNumber);
        String uniqueHashIdentifier = Long.toHexString(Double.doubleToLongBits(Double.valueOf(uniqueIdentifier)));
        return uniqueHashIdentifier;
    }

    public static void main(String[] args){
        uniqueIdentifier ();
    }
}
