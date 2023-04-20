package com.guide;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.util.Helpers;

import java.util.ArrayList;
import java.util.List;

public class RomanNumerals {
    public static final int VAL_MIN = 1;
    public static final int VAL_MAX = 3999;

    /**
     * The complete building blocks to express any Roman number between
     * 1 and 3999 through concatenation of the defined symbols.
     */
    private static BiMap<String, Integer> Numbers;
    static {
        Numbers = HashBiMap.create();
        Numbers.put("I", 1);
        Numbers.put("II", 2);
        Numbers.put("III", 3);
        Numbers.put("IV", 4);
        Numbers.put("V", 5);
        Numbers.put("VI", 6);
        Numbers.put("VII", 7);
        Numbers.put("VIII", 8);
        Numbers.put("IX", 9);
        Numbers.put("X", 10);
        Numbers.put("XX", 20);
        Numbers.put("XXX", 30);
        Numbers.put("XL", 40);
        Numbers.put("L", 50);
        Numbers.put("LX", 60);
        Numbers.put("LXX", 70);
        Numbers.put("LXXX", 80);
        Numbers.put("XC", 90);
        Numbers.put("C", 100);
        Numbers.put("CC", 200);
        Numbers.put("CCC", 300);
        Numbers.put("CD", 400);
        Numbers.put("D", 500);
        Numbers.put("DC", 600);
        Numbers.put("DCC", 700);
        Numbers.put("DCCC", 800);
        Numbers.put("CM", 900);
        Numbers.put("M", 1000);
        Numbers.put("MM", 2000);
        Numbers.put("MMM", 3000);
    }

    /** Convert Roman to Arabic number */
    public static int getInt(String romanNumber) {
        checkValidRomanNumber(romanNumber);

        if (romanNumber.length() == 0)
            throw new NumberFormatException("Empty string is not allowed");

        if (romanNumber.length() == 1)
            return Numbers.get(romanNumber);

        List<String> summands = splitUpIntoRomanSummands(romanNumber);

        return sumUpSummands(summands);
    }

    private static void checkValidRomanNumber(String romanNumber) {
        if (romanNumber.contains("VV")
         || romanNumber.contains("LL")
         || romanNumber.contains("DD"))
            throw new NumberFormatException("V, L, and D cannot be repeated");

        if (romanNumber.contains("IIII")
                || romanNumber.contains("XXXX")
                || romanNumber.contains("CCCC")
                || romanNumber.contains("MMMM"))
            throw new NumberFormatException("No symbol can be repeated more than thrice");

        if (romanNumber.contains("IL")
                || romanNumber.contains("IC")
                || romanNumber.contains("ID")
                || romanNumber.contains("IM"))
            throw new NumberFormatException("I can be subtracted from V and X only");

        if (romanNumber.contains("XD")
                || romanNumber.contains("XM"))
            throw new NumberFormatException("X can be subtracted from L and C only");

        if (romanNumber.contains("VX")
                || romanNumber.contains("VL")
                || romanNumber.contains("VC")
                || romanNumber.contains("VD")
                || romanNumber.contains("VM")
                || romanNumber.contains("LC")
                || romanNumber.contains("LD")
                || romanNumber.contains("LM")
                || romanNumber.contains("DM"))
            throw new NumberFormatException("V, L, and D cannot be subtracted");

        if (romanNumber.contains("IIV")
                || romanNumber.contains("IIX")
                || romanNumber.contains("XXL")
                || romanNumber.contains("XXC")
                || romanNumber.contains("CCD")
                || romanNumber.contains("CCM"))
            throw new NumberFormatException("Only one small-value symbol may be subtracted\n" +
                    "from any large-value symbol");
    }

    private static int sumUpSummands(List<String> summands) throws IllegalArgumentException  {
        if (summands == null || summands.size() == 0)
            throw new IllegalArgumentException();

        int sum = 0;

        for (String summand : summands) {
            switch (summand.length()){
                case 1:
                    sum += Numbers.get(summand);
                    break;
                case 2:
                    int addend = Numbers.get(summand.substring(1,2))
                            - Numbers.get(summand.substring(0,1));
                    sum += addend;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        return sum;
    }

    public static List<String> splitUpIntoRomanSummands(String numerals) {
        if (numerals == null || numerals.length() == 0)
            throw new IllegalArgumentException();

        List<String> res = new ArrayList<>(numerals.length());

        String next = "";
        for (int i = 0; i < numerals.length(); i++) {
            // Already add the next symbol
            next = numerals.substring(i, i+1);
            // Small value preceding large value? Then that's our next summand
            if (i+1 < numerals.length() && Numbers.get(numerals.substring(i, i+1)) < Numbers.get(numerals.substring(i+1, i+2))) {
                next = next.concat(numerals.substring(i+1, i+2));
                i++;
            }
            res.add(next);
        }
        return res;
    }

    public static String getRomanNumber(int i) {
        checkValidNumber(i);

        if (Helpers.getNoOfDigits(i) == 1)
            return String.valueOf(Numbers.inverse().get(i));

        List<Integer> parts = getParts(i);

        return getRomanNumber(parts);
    }

    private static void checkValidNumber(int i) {
        if (i < VAL_MIN)
            throw new IllegalArgumentException(
                    "Numbers smaller than " + VAL_MIN + " cannot be expressed through Roman numerals");

        if (i > VAL_MAX)
            throw new IllegalArgumentException(
                    "Numbers larger than " + VAL_MAX + " cannot be expressed through Roman numerals");
    }

    /**
     * @return Parts, or components, of an integer.
     * Resulting parts are in the form of a digit [1...9] with 0 to 3 trailing zeros.
     * These can be concatenated to express a Roman number.
     */
    public static List<Integer> getParts(int i) {
        checkValidNumber(i);

        List<Integer> parts = new ArrayList<>(String.valueOf(i).length());

        int placeValue = 1;
        while (i > 0){
            int part = i % 10;
            if (part > 0)
                parts.add(0, part * placeValue);
            placeValue *= 10;
            i /= 10;
        }

        return parts;
    }

    /**
     *
     * @param parts integers in the form of n × 10^k, where n is an integer between 1 and 9 (inclusive),
     *              and k is an integer between 0 and 3 (inclusive)
     * @return the Roman number that is the concatenation of the Roman numerals of {@code parts}
     */
    public static String getRomanNumber(List<Integer> parts) {
        String romanNumber = "";

        for (int i : parts){
            romanNumber = romanNumber.concat(Numbers.inverse().get(i));
        }

        return romanNumber;
    }
}
