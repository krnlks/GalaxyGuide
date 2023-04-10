package com.guide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RomanNumerals {
    private static Map<Character, Integer> Numerals;
    static {
        Numerals = new HashMap<>();
        Numerals.put('I', 1);
        Numerals.put('V', 5);
        Numerals.put('X', 10);
        Numerals.put('L', 50);
        Numerals.put('C', 100);
        Numerals.put('D', 500);
        Numerals.put('M', 1000);
    }

    public static int toInt(String numerals) {
        if (numerals.length() == 0)
            throw new NumberFormatException("Empty string is not allowed");

        if (numerals.length() == 1)
            return Numerals.get(numerals.charAt(0));

        List<char[]> summands = extractSummands(numerals);

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

}
