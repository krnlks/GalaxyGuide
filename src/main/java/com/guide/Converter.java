package com.guide;

import java.util.*;

import static com.util.Helpers.capitalizeFirstLetter;
import static com.util.Helpers.tryFormatAsInt;

public class Converter {
    /**
     * A mapping from terms to Roman numerals.
     * Values must be defined in the chat before usage
     */
    Map<String,String> alienTermsToNumerals;
    /**
     * A mapping from terms to Roman numerals.
     * Contains previously entered mappings.
     */
    Map<String, Float> goodsToCredits;

    private static final String NUMBER_CONVERSION_QUERY_START = "how much is ";
    private static final String CREDITS_GOODS_QUERY_START = "how many credits is ";
    private static final String INVALID_QUERY_RESPONSE = "I have no idea what you are talking about";

    public Converter(){
        alienTermsToNumerals = new HashMap<>(7);
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

        return INVALID_QUERY_RESPONSE;
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
        alienTermsToNumerals.put(arr[0], arr[2]);
        return "";
    }

    /**
     * Works through {@code input} and calculates the number of credits
     * that one unit of the given goods is worth and stores it.
     *
     * <p>If no alien term is present, then the quantity of the goods is interpreted as 1.
     *
     * <p>Example: "glob glob Silver is 34 Credits"
     *
     * @param input Format: "[alien terms]* [goods] (is) [number] (credit(s))".
     * @return an empty String if successful
     */
    private String assignCreditsToGoods(String input) {
        List<String> parts = Arrays.asList(input.split(" "));
        var iter_parts = parts.iterator();

        StringBuilder romanNumber = new StringBuilder();

        // Collect all alien terms
        String term = "";
        while (iter_parts.hasNext()) {
            term = iter_parts.next();
            var numeral = alienTermsToNumerals.get(term);
            if (numeral == null)
                break;
            romanNumber.append(numeral);
        }

        // Get the goods
        String goods = term;
        if (goods == null)
            throw new IllegalArgumentException(invalidCreditsToGoodsAssignmentString());

        String nextElem = iter_parts.next();
        if (nextElem.equalsIgnoreCase("is"))
            nextElem = iter_parts.next(); // We're not interested in "is"

        float credits;
        try {
            credits = Float.parseFloat(nextElem);
        }catch (NumberFormatException e){
            return invalidCreditsToGoodsAssignmentString();
        }

        // Get integer representation of Roman number
        int romanAsInt =  RomanNumerals.getInt(romanNumber.toString().toUpperCase());

        // Calculate credits for one unit of the goods
        credits /= romanAsInt;

        goodsToCredits.put(goods, credits);
        return "";
    }

    private String invalidCreditsToGoodsAssignmentString() {
        return "A valid credits to goods assignment has the form\n" +
                "[alien terms]* [goods] (is) [number] credit(s)";
    }

    private String generateNumberConversionQueryResponse(String input) {
        String alienTerms = input.replace(NUMBER_CONVERSION_QUERY_START,"").trim();

        String[] arr = alienTerms.split(" ");
        // For each term get the Roman numeral
        StringBuilder romanNumerals = new StringBuilder(arr.length);
        for (String s : arr) {
            String foo = alienTermsToNumerals.get(s);
            if (foo == null)
                return String.format("I've never heard of %s", s);
            romanNumerals.append(foo);
        }

        int intEquivalent;
        try {
            intEquivalent = RomanNumerals.getInt(romanNumerals.toString().toUpperCase());
        }catch (NumberFormatException e){
            return "Violation of Roman number format: " + e.getMessage();
        }

        return alienTerms + " is " + intEquivalent;
    }

    private boolean isCreditsPerGoodsQuery(String input) {
        return (input.startsWith(CREDITS_GOODS_QUERY_START));
    }

    /**
     * @param input Format: "how many Credits is [alien terms]* [goods] (?)"
     * @return the response in the format "[alien terms] [goods] is [credits] Credits"
     */
    private String generateCreditsPerGoodsQueryResponse(String input) {
        String alienAmountOfGoods = input.replace(CREDITS_GOODS_QUERY_START,"").trim();

        // Extract the alien terms and the goods parts
        int indexOfGoods = alienAmountOfGoods.lastIndexOf(" ")+1;
        String goods = alienAmountOfGoods.substring(indexOfGoods);
        String alienTerms = alienAmountOfGoods.substring(0, indexOfGoods-1);
        
        // Convert alien terms to Roman number
        String[] arr = alienTerms.split(" ");
        StringBuilder romanNumber = new StringBuilder();
        for (String term : arr) {
            String numeral = alienTermsToNumerals.get(term);
            if (numeral == null)
                return INVALID_QUERY_RESPONSE;

            romanNumber.append(numeral);
        }

        // Get integer representation of Roman number
        int romanAsInt =  RomanNumerals.getInt(romanNumber.toString().toUpperCase());

        // Calculate credits (credits stored for the goods x alien terms)
        float credits = goodsToCredits.get(goods) * romanAsInt;

        // return "glob prok Silver is 68 Credits"
        return alienTerms + " " + capitalizeFirstLetter(goods) + " is " + tryFormatAsInt(credits) + " Credits";
    }

    public static void main(String[] args) {
        Converter c = new Converter();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Welcome to the Intergalactic Numbers and Units Converter!\n" +
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
