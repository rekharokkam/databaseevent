package com.learning.databaseevent.utils;

import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
//        uniqueIdentifier ();

        List<String> numbers = Arrays.asList("one", "two", "3", "4", "five", "six", "seven", "eight", "nine", "ten");

        int numberOfOrders = numbers.size();
        int numberOfRecordsPerSet = 3;
        int reminder = numberOfOrders % numberOfRecordsPerSet;
        int result = numberOfOrders / numberOfRecordsPerSet;
        int numOfPages = result + ( (reminder > 0) ? 1 : 0);
        System.out.println("number of pages : " + numOfPages);

        int startIndex = 0, endIndex = 0;

        for (int i = 0; i < numOfPages; i ++) {
            startIndex = endIndex;
            endIndex = ( (startIndex + numberOfRecordsPerSet) > numberOfOrders) ? numberOfOrders : (startIndex + numberOfRecordsPerSet);

            System.out.println("page Number : " + i + " , startIndex : " + startIndex + " , endIndex : " + endIndex);
            List<String> nunbers_per_page = numbers.subList(startIndex, endIndex);
            System.out.println("each sub-list : " + nunbers_per_page);
        }
    }
}
