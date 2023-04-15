package com.guide;

import java.util.Scanner;

public class Converter {
    public static String input(String input) {
        input = input.toLowerCase().trim();

        if (input.contains("how much is")) {
            return "It's that much!";
        } else if (input.contains("how many credits is")) {
            return "That's so and so many credits!";
        }

        return "";
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        System.out.println("Welcome to the Intergalactic Numbers and Units Converter! Type 'exit' to quit.");

        while (!exit) {
            String input = scanner.nextLine();

            if ("exit".equalsIgnoreCase(input)) {
                exit = true;
            } else {
                String response = input(input);
                if (response.length() > 0)
                    System.out.println("Response: " + response);
            }
        }

        System.out.println("Goodbye!");
    }
}
