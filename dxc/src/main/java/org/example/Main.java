package org.example;

import java.util.*;

public class Main {
    public static final int numChar = 44;
    private static final Character[] reference = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
    'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6',
    '7', '8', '9', '(', ')', '*', '+', ',', '-', '.', '/'};

    private static final Map<Character, Integer> indicesMap = populateIndices();

    private static Map<Character, Integer> populateIndices() {
        Map<Character, Integer> indices = new HashMap<>();
        for (int i = 0; i < numChar; i++) {
            indices.put(reference[i], i);
        }
        return indices;
    }

    private static String[] parseInput(String input) {
        try {
            String upperCaseInput = input.toUpperCase();
            String[] inputSplit = upperCaseInput.split(" ");

            if (inputSplit.length < 2) {
                if (inputSplit[0].equals("ENCODE") || inputSplit[0].equals("DECODE")) {
                    throw new Exception("You need to provide a string to encode/decode!");
                }
                if (!inputSplit[0].equals("END")) {
                    throw new Exception("Invalid command!");
                }
                return new String[]{inputSplit[0]};
            }
            else {
                return new String[]{inputSplit[0], upperCaseInput.substring(7)};
            }
        } catch(Exception e) {
            System.out.print(e.getMessage() + " ");
            return new String[]{"INVALID"};
        }
    }

    private static String encode(String plaintext) {
        char offsetChar = plaintext.charAt(0);
        int offsetPosition = indicesMap.get(offsetChar);

        StringBuilder encodedStr = new StringBuilder();

        for (int i = 1; i < plaintext.length(); i++) {
            Character ch = plaintext.charAt(i);

            if (ch.equals(' ')) {
                encodedStr.append(' ');
            }
            else {
                int chIndex = indicesMap.get(ch);
                int encodedIndex = ((chIndex - offsetPosition) % numChar + numChar) % numChar;
                encodedStr.append(reference[encodedIndex]);
            }
        }
        return (encodedStr.toString());
    }

    private static String decode(String plaintext) {
        char offsetChar = plaintext.charAt(0);
        int offsetPosition = indicesMap.get(offsetChar);

        StringBuilder decodedStr = new StringBuilder();

        for (int i = 1; i < plaintext.length(); i++) {
            Character ch = plaintext.charAt(i);

            if (ch.equals(' ')) {
                decodedStr.append(' ');
            }
            else {
                int chIndex = indicesMap.get(ch);
                int encodedIndex = (chIndex + offsetPosition) % numChar;
                decodedStr.append(reference[encodedIndex]);
            }
        }
        return (decodedStr.toString());
    }

    private static void handleInput(String[] parsedInput) {
        String command = parsedInput[0];

        if (command.equals("INVALID")) {
            System.out.println("Please try again with this input format: '[end/encode/decode] [plaintext string]'");
        }
        else if (command.equals("END")) {
            System.exit(0);
        }
        else {
            String plaintext = parsedInput[1];

            if (command.equals("ENCODE")) {
                System.out.println(encode(plaintext));
            }
            else if (command.equals("DECODE")) {
                System.out.println(decode(plaintext));
            }
        }
    }

    public static void main(String[] args) {
        Scanner reader = new Scanner(System.in);

        while (true) {
            System.out.print("Enter your instructions: ");
            String input = reader.nextLine();

            String[] parsedInput = parseInput(input);
            handleInput(parsedInput);
        }
    }
}