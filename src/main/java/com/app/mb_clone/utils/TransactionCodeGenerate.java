package com.app.mb_clone.utils;

import java.util.Random;

public class TransactionCodeGenerate {
    public static String generateRandomString() {
        StringBuilder sb = new StringBuilder("FT");

        Random random = new Random();
        for (int i = 0; i < 14; i++) {
            int randomNumber = random.nextInt(10);
            sb.append(randomNumber);
        }

        return sb.toString();
    }
}
