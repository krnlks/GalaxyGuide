package com.guide;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {
    /**
     * A mapping from terms to Roman numerals.
     * Values must be defined in the chat before usage
     */
    Map<String,String> termsToNumerals;
    /**
     * A mapping from terms to Roman numerals.
     * Contains previously entered mappings.
     */
    Map<String, Float> metalsToCredits;

    public Converter(){
        termsToNumerals = new HashMap<>(7);
        metalsToCredits = new HashMap<>();
    }

    /**
     * @param input the input string (or, query string)
     * @return the response, if any
     */
    public String generateResponse(String input) {
        input = input.toLowerCase().trim();

        String[] arr = input.split(" ");
        if (isAssignment_termToNumeral(arr)) {
            return generateTermToNumeralAssignmentResponse(arr);
        }

        if (isAssignment_creditsToGoods(arr)) {
            return generateCreditsToGoodsAssignmentResponse(arr);
        }

        Matcher m = tryParseNumberConversionQuery(input);
        if (m.matches()){
            List<String> matches = new ArrayList<>();
            for (int i = 1; i <= m.groupCount(); i++) {
                if (m.group(i) != null) {
                    matches.add(m.group(i));
                }
            }
            return generateNumberConversionQueryResponse(matches.toArray(new String[0]));
        }

        if (isCreditsPerMetalQuery(input)) {
            return generateCreditsPerMetalQueryResponse();
        }

        return "";
    }

    /**
     * @return true if {@code arr} is a valid definition of a term,
     * i.e., a valid assignment of a term to a Roman numeral.
     * Definitions must be in the form "prok is V"
     */
    private boolean isAssignment_termToNumeral(String[] arr) {
        return (arr.length == 3
                && arr[1].equals("is")
                && Arrays.asList("i","v","x","l","c","d","m").contains(arr[2]));
    }

    /**
     * @return true if {@code arr} is a valid definition
     * of the value of one unit of a goods.
     * Definitions must be in the form "prok prok Silver is 34 Credits"
     */
    private boolean isAssignment_creditsToGoods(String[] arr) {
        return false;
    }

    private String generateTermToNumeralAssignmentResponse(String[] arr) {
        termsToNumerals.put(arr[0], arr[2]);
        return "";
    }

    /**
     * Format: "glob glob Silver is 34 Credits"
     */
    private String generateCreditsToGoodsAssignmentResponse(String[] arr) {
        String metal = "Silver";
        // TODO: credits = 34 / glob glob
        float credits = 0;
        metalsToCredits.put(metal, credits);
        return "";
    }

    private Matcher tryParseNumberConversionQuery(String input) {
        // Require at least one term and make question mark optional
        String numberConversionRegex = "how much is(?:\\s+(\\S+))+(?:\\s*\\?)?";
        Pattern p = Pattern.compile(numberConversionRegex, Pattern.CASE_INSENSITIVE);
        return p.matcher(input);
    }

    private String generateNumberConversionQueryResponse(String[] matches) {
        // Collect number terms
        StringBuilder romanNumerals = new StringBuilder(matches.length);
        for (int i = 0; i < matches.length; i++) {
            romanNumerals.append(termsToNumerals.get(matches[i]));
        }

        int result = RomanNumerals.getInt(romanNumerals.toString().toUpperCase());

        return String.join(" ", matches) + " is " + result;
    }

    private boolean isCreditsPerMetalQuery(String input) {
        return (input.split(" ").length > 5
                && input.startsWith("how many credits is")
                && input.endsWith("?"));
    }

    private String generateCreditsPerMetalQueryResponse() {
        return null;
    }

    public static void main(String[] args) {
        Converter c = new Converter();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Welcome to the Intergalactic Numbers and Units Converter!" +
                "Type 'usage' for usage info and 'exit' to quit.");

        while (!exit) {
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                exit = true;
            } else if ("usage".equalsIgnoreCase(input)) {
                printUsageInfo();
            } else {
                String response = c.generateResponse(input);
                if (response.length() > 0)
                    System.out.println("Response: " + response);
            }
        }

        System.out.println("Goodbye!");
    }

    private static void printUsageInfo() {
        System.out.println("Usage info:");
    }
}
