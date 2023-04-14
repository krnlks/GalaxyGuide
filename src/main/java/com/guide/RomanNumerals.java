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
    private static BiMap<String, Integer> Numerals;
    static {
        Numerals = HashBiMap.create();
        Numerals.put("I", 1);
        Numerals.put("II", 2);
        Numerals.put("III", 3);
        Numerals.put("IV", 4);
        Numerals.put("V", 5);
        Numerals.put("VI", 6);
        Numerals.put("VII", 7);
        Numerals.put("VIII", 8);
        Numerals.put("IX", 9);
        Numerals.put("X", 10);
        Numerals.put("XX", 20);
        Numerals.put("XXX", 30);
        Numerals.put("XL", 40);
        Numerals.put("L", 50);
        Numerals.put("LX", 60);
        Numerals.put("LXX", 70);
        Numerals.put("LXXX", 80);
        Numerals.put("XC", 90);
        Numerals.put("C", 100);
        Numerals.put("CC", 200);
        Numerals.put("CCC", 300);
        Numerals.put("CD", 400);
        Numerals.put("D", 500);
        Numerals.put("DC", 600);
        Numerals.put("DCC", 700);
        Numerals.put("DCCC", 800);
        Numerals.put("CM", 900);
        Numerals.put("M", 1000);
        Numerals.put("MM", 2000);
        Numerals.put("MMM", 3000);
    }

    public static int getInt(String romanNumber) {
        if (romanNumber.length() == 0)
            throw new NumberFormatException("Empty string is not allowed");

        if (romanNumber.length() == 1)
            return Numerals.get(romanNumber.charAt(0));

        List<char[]> summands = extractSummands(romanNumber);

        return sumUpSummands(summands);
    }

    private static int sumUpSummands(List<char[]> summands) throws IllegalArgumentException  {
        if (summands == null || summands.size() == 0)
            throw new IllegalArgumentException();

        int sum = 0;

        for (char[] summand : summands) {
            switch (summand.length){
                case 1:
                    sum += Numerals.get(summand[0]);
                    break;
                case 2:
                    int addend = Numerals.get((summand)[1])
                            - Numerals.get((summand[0]));
                    sum += addend;
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }

        return sum;
    }

    public static List<char[]> extractSummands(String numerals) {
        return extractSummands(numerals.toCharArray());
    }

    /**
     * Here, a summand, or group, consists of either one or two symbols:
     * Two symbols if a small value symbol precedes a large value symbol.
     * One symbol otherwise.
     */
    public static List<char[]> extractSummands(char[] numeralsCA) {
        if (numeralsCA == null || numeralsCA.length == 0)
            throw new IllegalArgumentException();

        List<char[]> res = new ArrayList<>(numeralsCA.length);

        for (int i=0; i<numeralsCA.length; i++) {
            // Look at the two next symbols:
            // Small value preceding large value? Then that's a summand (group)
            if (i+1 < numeralsCA.length && Numerals.get(numeralsCA[i]) < Numerals.get(numeralsCA[i + 1])) {
                char[] cArr = new char[2];
                cArr[0] = numeralsCA[i];
                cArr[1] = numeralsCA[i + 1];
                res.add(cArr);
                i++;
            // Otherwise, the summand is only the next symbol
            } else {
                char[] cArr = new char[1];
                cArr[0] = numeralsCA[i];
                res.add(cArr);
            }
        }
        return res;
    }

    public static String getRomanNumber(int i) {
        checkValidNumber(i);

        if (Helpers.getNoOfDigits(i) == 1)
            return String.valueOf(Numerals.inverse().get(i));

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
            romanNumber = romanNumber.concat(Numerals.inverse().get(i));
        }

        return romanNumber;
    }
}
