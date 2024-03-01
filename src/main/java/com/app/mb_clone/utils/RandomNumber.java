package com.app.mb_clone.utils;

import java.util.Random;

public class RandomNumber {
    public static String getRandomNumber(int length) {
        Random random = new Random();
        StringBuilder number = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int digit = random.nextInt(length);
            number.append(digit);
        }

        return number.toString();
    }

}
