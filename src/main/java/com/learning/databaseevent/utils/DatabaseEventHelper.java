package com.target.coco.orders.standalone.helper;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class DatabaseEventHelper {
    private static final int SET_SIZE = 100000;

    public static void testHashSalt (){
        Set<String> testSet = new HashSet<>();
        for (int i = 0; i < SET_SIZE; i ++){
            String randomStr = Long.toHexString(Double.doubleToLongBits(Double.valueOf(uniqueIdentifier())));
            testSet.add(randomStr);
            System.out.println("random string : " + randomStr);
        }
        System.out.println("Size of the set : " + testSet.size());
        if (testSet.size() != SET_SIZE){
            System.out.println("problem");
        } else {
            System.out.println("No problem");
        }
    }

    public static String uniqueIdentifier (){
        Long nanoSecs = System.nanoTime();
        ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
        Double randomNumber = threadLocalRandom.nextDouble(1000000, 99999999);
        return nanoSecs + String.valueOf(randomNumber);
    }

    public static void main(String[] args){
        testHashSalt ();
    }
}
