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

    public static String capitalizeFirstLetter (String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }

    public static String tryFormatAsInt(float f)
    {
        if(f == (int) f)
            return String.format("%d",(int)f);
        else
            return String.format("%s",f);
    }

    public static boolean containsNumber(String str) {
        return str.matches(".*\\d.*");
    }
}
