
// Created by Dallin.
//This program reads this spacific file format and splits up the data into a more readable format and processes the numbers for percentage and avrages.
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // File Var.
        File LetterFrequenctFile = new File("letter_frequency.csv");
        // TheStrings list has all of the strings but seperated after each ,
        List<String> TheStrings = new ArrayList<>();
        try (Scanner scanner = new Scanner(LetterFrequenctFile)) {
            // Adds and seperates the text file into TheStrings after each ,
            while (scanner.hasNextLine()) {
                String Line = scanner.nextLine();

                int StartInt = 0;
                for (int i = 0; i < Line.length(); i++) {
                    if (Line.charAt(i) == ',') {
                        TheStrings.add(Line.substring(StartInt, i).trim());
                        StartInt = i + 1;
                    }
                }
                TheStrings.add(Line.substring(StartInt).trim());
            }

            // Variables for printing TheString list
            int TotalFrequency = 0;
            float TotalPrecent = 0;
            int MaxChar = 1;

            // Prints out and adds up the totalfrequency and totalprecent.
            for (int i = 0; i < TheStrings.size(); i++) {
                // Prints the first two parts of the line which is Letter and Frequency.
                if (MaxChar < 3) {
                    // Checks if MaxChar is on the second line and trys to add it to the total
                    if (MaxChar == 2) {
                        try {
                            TotalFrequency += Integer.parseInt(TheStrings.get(i).replace("\"", "").trim());
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    }
                    System.out.print(TheStrings.get(i).replace("\"", "") + " ");
                } else {
                    System.out.println(TheStrings.get(i).replace("\"", ""));
                    // Resets the max char to 0 so it can reiterate through the println process.
                    MaxChar = 0;
                }

                MaxChar++;

                // Adds the TotalPrecent together if it is on TheString List.
                if (MaxChar == 1) {
                    try {
                        TotalPrecent += Float.parseFloat(TheStrings.get(i).replace("\"", "").trim());
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }

            // Devides total frequency to be an avrage and then prints the TotalFrequency
            // and TotalPrecent.
            TotalFrequency = TotalFrequency / ((TheStrings.size() - 3) / 3);
            System.out.println("Avrage Frequency is " + TotalFrequency + " Total Precentages " + TotalPrecent);

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}