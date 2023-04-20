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
    private static final String CREDITS_GOOD_QUERY_START = "how many credits is ";
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

        if (isAssignment_creditsToGood(query))
            return assignCreditsToGood(query);

        if (isNumberConversionQuery(query))
            return generateNumberConversionQueryResponse(query);

        if (isCreditsPerGoodQuery(query))
            return generateCreditsPerGoodQueryResponse(query);

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
     * of the value of one unit of a good.
     * Format: "prok prok Silver is 34 Credits"
     */
    private boolean isAssignment_creditsToGood(String query) {
        return (!query.startsWith(NUMBER_CONVERSION_QUERY_START)
                && !query.startsWith(CREDITS_GOOD_QUERY_START)
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
     * that one unit of the given good is worth and stores it.
     *
     * <p>If no alien term is present, then the quantity of the good is interpreted as 1.
     *
     * <p>Example: "glob glob Silver is 34 Credits"
     *
     * @param query Format: "[alien terms]* [good] (is) [number] (credit(s))".
     * @return an empty String if successful
     */
    private String assignCreditsToGood(String query) {
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

        // Get the good
        String good = term;
        if (good == null)
            throw new IllegalArgumentException(invalidCreditsToGoodAssignmentString());

        String nextElem = iter_parts.next();
        if (nextElem.equalsIgnoreCase("is"))
            nextElem = iter_parts.next(); // We're not interested in "is"

        float credits;
        try {
            credits = Float.parseFloat(nextElem);
        }catch (NumberFormatException e){
            return invalidCreditsToGoodAssignmentString();
        }

        // If there were alien terms,
        if (!romanNumber.isEmpty()){
            // get the Roman number's integer representation
            int romanAsInt = RomanNumerals.getInt(romanNumber.toString().toUpperCase());
            // and calculate credits for one unit of the good
            credits /= romanAsInt;
        }

        goodsToCredits.put(good, credits);
        return "";
    }

    private String invalidCreditsToGoodAssignmentString() {
        return "A valid credits to good assignment has the form\n" +
                "[alien terms]* [good] (is) [number] credit(s)";
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

    private boolean isCreditsPerGoodQuery(String query) {
        return (query.startsWith(CREDITS_GOOD_QUERY_START));
    }

    /**
     * @param query Format: "how many Credits is [alien terms]* [good] (?)"
     * @return the response in the format "[alien terms] [good] is [credits] Credits"
     */
    private String generateCreditsPerGoodQueryResponse(String query) {
        // If we haven't stored any goods, stop here
        if (goodsToCredits.isEmpty())
            return "I know nothing about values of any goods";

        String alienAmountOfGood = query.replace(CREDITS_GOOD_QUERY_START,"").trim();

        // Extract the alien terms part and the good part
        int indexOfGood = alienAmountOfGood.lastIndexOf(" ")+1;
        String good = alienAmountOfGood.substring(indexOfGood);

        boolean containsAlienTerms = indexOfGood > 1;
        // If no alien terms are given, we assume one unit of the good and return more quickly
        if (!containsAlienTerms)
            return getResponseString(good, "", 1);

        String alienTerms = alienAmountOfGood.substring(0, indexOfGood-1);
        
        // Convert alien terms to Roman number
        String[] arr = alienTerms.split(" ");
        StringBuilder romanNumber = new StringBuilder();
        for (String term : arr) {
            String numeral = alienTermsToNumerals.get(term);
            if (numeral == null)
                return String.format("% has not been defined", term);

            romanNumber.append(numeral);
        }

        // Get integer representation of Roman number
        int romanAsInt =  RomanNumerals.getInt(romanNumber.toString().toUpperCase());

        return getResponseString(good, alienTerms.concat(" "), romanAsInt);
    }

    /**
     *
     * @param good the good, e.g. "Iron"
     * @param alienTerms the alien terms including a trailing space, or an empty string in case of no alien terms
     * @param amount the number of units of the good. Must be 1 if no alien terms are passed
     * @return the credits per good response in the form "(glob prok )Silver is X Credits"
     */
    private String getResponseString(String good, String alienTerms, int amount) {
        // Calculate credits (credits stored for the good x alien terms)
        float credits = goodsToCredits.get(good) * amount;
        String ret = capitalizeFirstLetter(good) + " is " + tryFormatAsInt(credits) + " Credits";

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
        printAssignCreditsToGoodInfo();
        printNumberConversionInfo();
        printCreditsPerAmountOfGoodInfo();
    }

    private static void printTermDefinitionInfo() {
        System.out.println("Define foreign terms for Roman numerals in the format\n" +
                "'[term] is [Roman numeral]'\n");
    }

    private static void printAssignCreditsToGoodInfo() {
        System.out.println("Assign a number of credits to an amount of a good in the format\n" +
                "'([previously defined term1] [p. d. term2] [...]) [good] (is) [Arabic number of] (credit(s))'\n");
    }

    private static void printCreditsPerAmountOfGoodInfo() {
        System.out.println("Get the value in Credits of an amount of a good by asking\n" +
                "'How many Credits is [p. d. term1] [term2] ... [good] (?)'\n");
    }

    private static void printNumberConversionInfo() {
        System.out.println("Convert a sequence of foreign numeral terms to an Arabic number by asking\n" +
                "'How much is [p. d. term1] [term2] [...] (?)'\n");
    }
}
