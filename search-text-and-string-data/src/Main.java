// Kurtis Ivey
// CS 201 boyer moore search assignment

import java.util.*;

public class Main {

    // array of all 50 US states
    static String[] states = {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
            "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky",
            "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi",
            "Missouri", "Montana", "Nebraska", "Nevada", "NewHampshire", "NewJersey", "NewMexico", "NewYork",
            "NorthCarolina", "NorthDakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", "RhodeIsland",
            "SouthCarolina", "SouthDakota", "Tennessee", "Texas", "Utah", "Vermont", "Virginia", "Washington",
            "WestVirginia", "Wisconsin", "Wyoming"
    };

    static String megaTxt = String.join("", states).toLowerCase(); // mash all states together, lowercased
    // input will be lowercased too, so matching always works regardless of casing

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // scanner for user input
        boolean exit = false; // loop controller

        while (!exit) {
            // print menu
            System.out.println("\nMenu of boyer moore search algo in 50 us states~~");
            System.out.println("1. Display the text");
            System.out.println("2. Search");
            System.out.println("3. Exit program");
            System.out.print("Enter your choice: ");

            if (scanner.hasNextInt()) { // user typed a number
                int selection = scanner.nextInt();
                scanner.nextLine();
                if (selection < 1 || selection > 3) { // make sure it's a valid menu choice
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                    continue;
                }

                switch (selection) {
                    case 1:
                        // print states all joined (with capitalization) , didn't want all lowercased megatext to show
                        System.out.println(String.join("", states));
                        break;

                    case 2:
                        System.out.print("enter the text pattern you want to search for: ");
                        String pattern = scanner.nextLine().toLowerCase(); // make input lowercase to match megaTxt
                        List<Integer> matchIndexes = searchWithBoyerMooreAlgo(megaTxt, pattern);

                        if (matchIndexes.isEmpty()) {
                            System.out.println("nothing found"); // no matches
                        } else {
                            System.out.println("Found at: " + matchIndexes); // show all match indexes
                        }
                        break;

                    case 3:
                        System.out.println("exiting now, bye"); // self-explanatory
                        exit = true; // kill loop
                        break;
                }

            } else {
                // if they typed something that's not a number
                System.out.println("Invalid input. Please enter a number between 1 and 3 per the menu options.");
                scanner.next(); // skips invalid input so user can enter again
            }
        }
        scanner.close(); // close scanner
    }

    // Boyer-Moore search using bad character rule
    static List<Integer> searchWithBoyerMooreAlgo(String text, String pattern) {
        List<Integer> matches = new ArrayList<>(); // store where pattern is found
        Map<Character, Integer> badCharTable = buildBadCharTable(pattern); // build skip table for bad chars

        int textLength = text.length();
        int patternLength = pattern.length();
        int jump = 0; // how far we're jumping the pattern over the text

        // keep going till pattern can't fit in remaining text
        while (jump <= textLength - patternLength) {
            int i = patternLength - 1; // start at end of pattern

            // compare pattern to text right to left
            while (i >= 0 && pattern.charAt(i) == text.charAt(jump + i)) {
                i--; // matching, keep moving left
            }

            if (i < 0) {
                matches.add(jump); // found match, save first index of pattern found in arraylist

                // jump using char right after match if possible
                if (jump + patternLength < textLength) {
                    char nextChar = text.charAt(jump + patternLength - 1); // char right after pattern in text
                    int lastSeen = badCharTable.getOrDefault(nextChar, -1); // get last index of that char in pattern
                    jump += patternLength - lastSeen; // skip ahead based on that
                } else {
                    jump += 1; // no char after match, just move 1
                }

            } else {
                // mismatch, figure out how far to jump using bad char rule
                char badChar = text.charAt(jump + i); // bad char in text
                int lastSeen = badCharTable.getOrDefault(badChar, -1); // where it last showed up in pattern
                jump += Math.max(1, i - lastSeen); // jump at least 1, or more if we can
            }
        }

        return matches; // return list of all match start indexes
    }

    // save where each char in pattern was last seen
    static Map<Character, Integer> buildBadCharTable(String pattern) {
        Map<Character, Integer> table = new HashMap<>(); // hashmap to store char:index

        for (int i = 0; i < pattern.length(); i++) {
            table.put(pattern.charAt(i), i); // overwrite each time so only last index is saved
        }
        return table;
    }
}
