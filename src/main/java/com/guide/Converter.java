package com.guide;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {
    /**
     * A mapping from terms to Roman numerals.
     * Contains previously entered mappings.
     */
    Map<String,String> termsToNumerals;

    public Converter(){
        termsToNumerals = new HashMap<>(7);
    }

    /**
     * @param input the input string (or, query string)
     * @return the response, if any
     */
    public String generateResponse(String input) {
        input = input.toLowerCase().trim();

        String[] arr = input.split(" ");
        if (isDefinition(arr)) {
            termsToNumerals.put(arr[0], arr[2]);
            return "";
        }

        Matcher m = tryParseNumberConversionQuery(input);
        if (m.matches()){
            List<String> matches = new ArrayList<>();
            while (m.find()){
                matches.add(m.group());
            }
            return generateNumberConversionQueryResponse(matches.toArray(new String[0]));
        }

        boolean isCreditsPerMetalQuery = (arr.length > 5
                && input.startsWith("how many credits is")
                && input.endsWith("?"));
        if (isCreditsPerMetalQuery) {
            return generateCreditsPerMetalQueryResponse();
        }

        return "";
    }

    private Matcher tryParseNumberConversionQuery(String input) {
        // Require at least one term and make question mark optional
        String numberConversionRegex = "how much is(?:\\s+(\\S+))+(?:\\s*\\?)?";
        Pattern p = Pattern.compile(numberConversionRegex, Pattern.CASE_INSENSITIVE);
        return p.matcher(input);
    }

    /**
     * @return true if {@code arr} is a valid definition of a term,
     * i.e., a valid assignment of a term to a Roman numeral
     */
    private boolean isDefinition(String[] arr) {
        return (arr.length == 3
                && arr[1].equals("is")
                && Arrays.asList("i","v","x","l","c","d","m").contains(arr[2]));
    }

    private String generateCreditsPerMetalQueryResponse() {
        return null;
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

    public static void main(String[] args) {
        Converter c = new Converter();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Welcome to the Intergalactic Numbers and Units Converter! Type 'exit' to quit.");

        while (!exit) {
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                exit = true;
            } else {
                String response = c.generateResponse(input);
                if (response.length() > 0)
                    System.out.println("Response: " + response);
            }
        }

        System.out.println("Goodbye!");
    }
}
