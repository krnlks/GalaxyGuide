package com.guide;

import java.util.*;

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
    Map<String, Float> goodsToCredits;

    private static final String NUMBER_CONVERSION_QUERY_START = "how much is ";
    private static final String CREDITS_GOODS_QUERY_START = "how many credits is ";

    public Converter(){
        termsToNumerals = new HashMap<>(7);
        goodsToCredits = new HashMap<>();
    }

    /**
     * @param query the input string (or, query string)
     * @return the response, if any
     */
    public String submitQuery(String query) {
        query = cleanUpQueryString(query);

        String[] arr = query.split(" ");
        if (isAssignment_termToNumeral(arr))
            return assignTermToNumeral(arr);

        if (isAssignment_creditsToGoods(query))
            return assignCreditsToGoods(query);

        if (isNumberConversionQuery(query))
            return generateNumberConversionQueryResponse(query);

        if (isCreditsPerGoodsQuery(query))
            return generateCreditsPerGoodsQueryResponse(query);

        return "I have no idea what you are talking about";
    }

    private static String cleanUpQueryString(String input) {
        input = input.toLowerCase();
        if (input.endsWith("?"))
            input = input.substring(0, input.length()-1);
        input = input.trim();
        return input;
    }

    private boolean isNumberConversionQuery(String input) {
        return (input.startsWith(NUMBER_CONVERSION_QUERY_START));
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
     * Format: "prok prok Silver is 34 Credits"
     */
    private boolean isAssignment_creditsToGoods(String query) {
        return (!query.startsWith(NUMBER_CONVERSION_QUERY_START)
                && !query.startsWith(CREDITS_GOODS_QUERY_START)
                && containsNumber(query)
                && query.contains("credit")
                );
    }

    private boolean containsNumber(String str) {
        return str.matches(".*\\d.*");
    }

    private String assignTermToNumeral(String[] arr) {
        termsToNumerals.put(arr[0], arr[2]);
        return "";
    }

    /**
     * Format: "glob glob Silver is 34 Credits"
     */
    private String assignCreditsToGoods(String input) {
        String goods = "Silver";
        // TODO: credits = 34 / glob glob
        float credits = 0;
        goodsToCredits.put(goods, credits);
        return "";
    }

    private String generateNumberConversionQueryResponse(String input) {
        input = input.replace(NUMBER_CONVERSION_QUERY_START,"");

        String[] arr = input.split(" ");
        // For each term get the Roman numeral
        StringBuilder romanNumerals = new StringBuilder(arr.length);
        for (String s : arr) {
            romanNumerals.append(termsToNumerals.get(s));
        }

        int result = RomanNumerals.getInt(romanNumerals.toString().toUpperCase());

        return input + " is " + result;
    }

    private boolean isCreditsPerGoodsQuery(String input) {
        return (input.startsWith(CREDITS_GOODS_QUERY_START));
    }

    private String generateCreditsPerGoodsQueryResponse(String input) {
        input = input.replace(CREDITS_GOODS_QUERY_START,"");
        // TODO: Implement generateCreditsPerGoodsQueryResponse()
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
                String response = c.submitQuery(input);
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
