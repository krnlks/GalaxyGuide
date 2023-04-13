package com.guide;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.util.Helpers;

import java.util.ArrayList;
import java.util.List;

import static com.util.Helpers.getFactor_DiffToSingleDigit;
import static com.util.Helpers.getMostSignDigit;

public class RomanNumerals {
    public static final int VAL_MIN = 1;
    public static final int VAL_MAX = 3999;

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
        Numerals.put("L", 50);
        Numerals.put("C", 100);
        Numerals.put("D", 500);
        Numerals.put("M", 1000);
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
            romanNumber = romanNumber.concat(getRomanNumberThatIsNx10ToTheK(i));
        }

        return romanNumber;
    }

    /**
     * @param i integer in the form of n × 10^k, where n is an integer between 1 and 9 (inclusive),
     * and k is an integer between 0 and 3 (inclusive). In other words, a digit [1...9] with 0 to 3
     * trailing zeros.
     *
     * @return the Roman number that represents {@code i}.
     */
    private static String getRomanNumberThatIsNx10ToTheK(int i) {
        checkValidNx10ToTheKNumber(i);

        int mostSignDigit = getMostSignDigit(i);
        // Memorize the factor
        int factorDiffFromPartToSingleDigit = getFactor_DiffToSingleDigit(i);
        // romNo = GetRomanNumber(9); <- „IX“
        String romanNumber = String.valueOf(Numerals.inverse().get(mostSignDigit));
        // romNo = Convert(romNo, reciproce(1/100)); <- „CM“
        romanNumber = scaleUpRomanNumber(romanNumber, factorDiffFromPartToSingleDigit);
        // convert() converts symbol by symbol (I -> C, X -> M)

        return null;
    }

    /**
     * Checks whether integer i is a digit with zero to three trailing zeros.
     * We need integers in this form as the building blocks to express Roman numbers.
     */
    private static void checkValidNx10ToTheKNumber(int i) {
        String numStr = Integer.toString(i);
        if (!numStr.matches("^[1-9]0{0,3}$"))
            throw new IllegalArgumentException();
    }

    /**
     * @param romanNumber a Roman number
     * @param factor the factor k by which {@code romanNumber} is to be scaled up
     * @return the scaled up number, i.e., with trailing zeros. Format: n × 10^k
     */
    public static String scaleUpRomanNumber(String romanNumber, int factor) {
        String scaledUpNumber = "";

        for (char romanNumeral : romanNumber.toCharArray()) {
            int romanNumeral_intEquivalent = Numerals.get(String.valueOf(romanNumeral));
            String scaledUpNumeral = Numerals.inverse().get(romanNumeral_intEquivalent * factor);
            scaledUpNumber = scaledUpNumber.concat(scaledUpNumeral);
        }

        return scaledUpNumber;
    }
}
