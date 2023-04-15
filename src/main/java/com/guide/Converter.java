package com.guide;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Converter {
    Map<String,String> termsToNumerals;

    public Converter(){
        termsToNumerals = new HashMap<>(7);
    }

    public String generateResponse(String input) {
        input = input.toLowerCase().trim();

        String[] arr = input.split(" ");
        boolean isDefinition = (arr.length == 3
                && arr [1] == "is"
                && Arrays.asList("i","v","x","l","c","d","m").contains(arr[2]));
        if (isDefinition) {
            termsToNumerals.put(arr[0], arr[2]);
            return "";
        }

        boolean isNumberConversionQuery = (arr.length > 4
                && input.substring(0,11).equals("how much is")
                && arr[arr.length-1] == "?");
        if (isNumberConversionQuery){
            return "It's that much!";
        }
        if (input.contains("how many credits is")) {
            return "That's so and so many credits!";
        }

        return "";
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
