package com.util;

public class Helpers {
    /**
     * @return the number of digits in the integer
     */
    public static int getNoOfDigits(int i){
        return String.valueOf(i).length();
    }

    /**
     *
     * @return the most significant digit,
     * e.g., for 900, it will return 9.
     */
    public static int getMostSignDigit(int i) {
        return Character.digit(String.valueOf(i).charAt(0), 10);
    }

    public static int getFactor_DiffToSingleDigit(int i) {
        if (i <= 0) {
            return 1;
        }

        return (int) Math.pow(10, Helpers.getNoOfDigits(i) - 1);
    }
}
