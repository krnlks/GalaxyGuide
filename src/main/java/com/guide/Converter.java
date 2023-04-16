package com.guide;

import java.util.*;

public class Converter {
    Map<String,String> termsToNumerals;

    public Converter(){
        termsToNumerals = new HashMap<>(7);
    }

    /**
     * @return the response, if any
     */
    public String generateResponse(String input) {
        input = input.toLowerCase().trim();

        String[] arr = input.split(" ");
        if (isDefinition(arr)) {
            termsToNumerals.put(arr[0], arr[2]);
            return "";
        }

        boolean isNumberConversionQuery = (arr.length > 4
                && input.substring(0,12).equals("how much is ")
                && arr[arr.length-1].equals("?"));
        if (isNumberConversionQuery){
            String numberTerms = input.substring(12,input.length()-2);
            return generateNumberConversionQueryResponse(numberTerms);
        }

        boolean isCreditsPerMetalQuery = (arr.length > 5
                && input.substring(0,12).equals("how many credits is")
                && arr[arr.length-1].equals("?"));
        if (isCreditsPerMetalQuery) {
            return generateCreditsPerMetalQueryResponse();
        }

        return "";
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

    private String generateNumberConversionQueryResponse(String input) {
        String[] arr = input.split(" ");

        // Collect number terms
        StringBuilder sb = new StringBuilder(arr.length);
        for (int i = 0; i < arr.length; i++) {
            sb.append(termsToNumerals.get(arr[i]));
        }

        // Convert Roman to Arabic number
        int result = RomanNumerals.getInt(sb.toString().toUpperCase());

        return input + " is " + result;
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
