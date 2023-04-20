package com.guide;

import java.util.*;

import static com.util.Helpers.*;

/**
 * Contains the chatbot, or, the user interface to the converter
 */
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

    private static String cleanUpQueryString(String query) {
        query = query.toLowerCase();
        if (query.endsWith("?"))
            query = query.substring(0, query.length()-1);
        query = query.trim();
        return query;
    }

    private boolean isNumberConversionQuery(String query) {
        return (query.startsWith(NUMBER_CONVERSION_QUERY_START));
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



    private String assignTermToNumeral(String[] arr) {
        alienTermsToNumerals.put(arr[0], arr[2]);
        return "";
    }

    /**
     * Works through {@code query} and calculates the number of credits
     * that one unit of the given goods is worth and stores it.
     *
     * <p>If no alien term is present, then the quantity of the goods is interpreted as 1.
     *
     * <p>Example: "glob glob Silver is 34 Credits"
     *
     * @param query Format: "[alien terms]* [goods] (is) [number] (credit(s))".
     * @return an empty String if successful
     */
    private String assignCreditsToGoods(String query) {
        List<String> parts = Arrays.asList(query.split(" "));
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

    /**
     *
     * @param query a number conversion query in the form "how much is [alien term]+"
     * @return the response containing the integer equivalent of the given sequence of terms
     */
    private String generateNumberConversionQueryResponse(String query) {
        // If we haven't defined any terms, stop here
        if (alienTermsToNumerals.isEmpty())
            return INVALID_QUERY_RESPONSE;

        String alienTerms = query.replace(NUMBER_CONVERSION_QUERY_START,"").trim();
        if (alienTerms.length() == 0) // If query contains no terms, stop here
            return INVALID_QUERY_RESPONSE;

        String[] arr = alienTerms.split(" ");
        // For each term get the Roman numeral
        StringBuilder romanNumerals = new StringBuilder(arr.length);
        for (String alienTerm : arr) {
            String romanNumeral = alienTermsToNumerals.get(alienTerm);
            if (romanNumeral == null)
                return String.format("I've never heard of %s", alienTerm);
            romanNumerals.append(romanNumeral);
        }

        int intEquivalent;
        try {
            intEquivalent = RomanNumerals.getInt(romanNumerals.toString().toUpperCase());
        }catch (NumberFormatException e){
            return "Violation of Roman number format: " + e.getMessage();
        }

        return alienTerms + " is " + intEquivalent;
    }

    private boolean isCreditsPerGoodsQuery(String query) {
        return (query.startsWith(CREDITS_GOODS_QUERY_START));
    }

    /**
     * @param query Format: "how many Credits is [alien terms]* [goods] (?)"
     * @return the response in the format "[alien terms] [goods] is [credits] Credits"
     */
    private String generateCreditsPerGoodsQueryResponse(String query) {
        // If we haven't stored any goods, stop here
        if (goodsToCredits.isEmpty())
            return "I know nothing about values of any goods";

        String alienAmountOfGoods = query.replace(CREDITS_GOODS_QUERY_START,"").trim();

        // Extract the alien terms and the goods parts
        int indexOfGoods = alienAmountOfGoods.lastIndexOf(" ")+1;
        String goods = alienAmountOfGoods.substring(indexOfGoods);

        boolean containsAlienTerms = indexOfGoods > 1;
        // If no alien terms are given, we assume one unit of the goods and return more quickly
        if (!containsAlienTerms)
            return getResponseString(goods, "", 1);

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

        return getResponseString(goods, alienTerms.concat(" "), romanAsInt);
    }

    /**
     *
     * @param goods the goods string, e.g. "Iron"
     * @param alienTerms the alien terms including a trailing space, or an empty string in case of no alien terms
     * @param amount the number of units of the goods. Must be 1 if no alien terms are passed
     * @return the credits per goods response in the form "(glob prok )Silver is X Credits"
     */
    private String getResponseString(String goods, String alienTerms, int amount) {
        // Calculate credits (credits stored for the goods x alien terms)
        float credits = goodsToCredits.get(goods) * amount;
        String ret = capitalizeFirstLetter(goods) + " is " + tryFormatAsInt(credits) + " Credits";

        if (amount == 1)
            alienTerms = ""; // just to be on the safe side

        // return "(glob prok )Silver is X Credits"
        return alienTerms + ret;
    }

    public static void main(String[] args) {
        Converter c = new Converter();

        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Welcome to Convo, the Intergalactic Numbers and Units Converter!\n\n" +
                "Type 'usage' for usage info and 'exit' to quit.\n");

        while (!exit) {
            String query = scanner.nextLine();

            if ("exit".equalsIgnoreCase(query)) {
                exit = true;
            } else if ("usage".equalsIgnoreCase(query)) {
                printUsageInfo();
            } else {
                String response = c.submitQuery(query);
                if (response.length() > 0)
                    System.out.printf("%s\n\n", response);
            }
        }

        System.out.println("Goodbye!");
    }

    public static void printUsageInfo() {
        printTermDefinitionInfo();
        printAssignCreditsToGoodsInfo();
        printNumberConversionInfo();
        printNoGoodsToCreditsInfo();
    }

    private static void printTermDefinitionInfo() {
        System.out.println("Define foreign terms for Roman numerals in the format\n" +
                "'[term] is [Roman numeral]'\n");
    }

    private static void printAssignCreditsToGoodsInfo() {
        System.out.println("Assign a number of credits to an amount of a good in the format\n" +
                "'([previously defined term1] [p. d. term2] [...]) [goods] (is) [Arabic number of] (credit(s))'\n");
    }

    private static void printNoGoodsToCreditsInfo() {
        System.out.println("Get the value in Credits of an amount of a goods by asking\n" +
                "'How many Credits is [p. d. term1] [term2] ... [goods] (?)'\n");
    }

    private static void printNumberConversionInfo() {
        System.out.println("Convert a sequence of foreign numeral terms to an Arabic number by asking\n" +
                "'How much is [p. d. term1] [term2] [...] (?)'\n");
    }
}
