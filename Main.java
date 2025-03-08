
// Created by Dallin.
//This program reads this spacific file format and splits up the data into a more readable format and processes the numbers for percentage and avrages.
//The second part of this program is a small game where it writes the player data into a playerfile.txt and can rewrite it for game progression/change.
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    // Main function.
    public static void main(String[] args) {
        ReadFileFormat("letter_frequency.csv");

        // Seperates lines in the terminal for better readability.
        System.out.println("\n \n \n");

        InitializeGame();
    }

    // Scanner for inputs in the terminal.
    public static Scanner Scan;

    // File for PlayerFile.txt
    public static File PlayerFile = new File("PlayerFile.txt");
    public static FileWriter writeFile;

    // List for of each string inthe PLayerFile.txt
    public static List<String> PlayerData;

    // Arrays of Names, Home Lands, Colors, and Patron Gods.
    public static String[] RandNames = { "Oddesyus", "Dallin", "Mark", "George", "Ethan", "Plato", "Donatello",
            "Michelangelo",
            "Raphael", "Leonardo" };

    public static String[] RandHomeLands = { "Ithica", "Idaho", "Greece", "A Cave", "Italy", "Troy", "Olympus",
            "A Island",
            "Rome", "Phaeacia" };
    public static String[] FavoriteColorsList = { "White", "Blue", "Red", "green" };
    public static String[] PatronGodsList = { "Zues", "Hades", "Posidon", "Athena" };

    // Number of players playing.
    public static int PlayerCount;

    // Variables for Player that are written to the PlayerFile.txt
    public static String PlayerName;
    public static String HomeLand;
    public static int Age;
    public static int FavoriteColor;
    public static int PatronGod;
    public static int DaysAtSea;

    public static void InitializeGame() {
        // Initializes scanner.
        Scan = new Scanner(System.in);

        // Rewrites the file to be blank.
        try {
            writeFile = new FileWriter(PlayerFile, false);
        } catch (Exception e) {
        }

        System.out.println("How many Players are playing?");
        System.out.println("You may have a max of 10");

        // Gets input and sets player count if it is within 1-10.
        int PlInput = CheckScan();
        PlayerCount = PlInput;

        if (PlInput <= 0 && PlInput > 10) {
            System.err.println("Incorrect input please try again.");
            InitializeGame();
            return;
        }

        // Initalizes the playerdata to a new ArrayList.
        // So it can be used to accses Lines on playerfile.txt
        PlayerData = new ArrayList<>();
        // Foreach player it will try to register and write the data to PlayerFile.txt
        for (int i = 0; i < PlayerCount; i++) {
            // Regesters the Players
            PlayerRegistration(i);
            // Tells if you should move on to the next player or if evreyone is inputed.
            if (i < PlayerCount - 1) {
                System.out.println("We got the info. Now for the next player.");
            } else {
                System.out.println("That should be it, all " + PlayerCount + " Players have been inputed.");
                System.out.println("Starting the Game");
            }
            // Writes the data from Player Registration to the PlayerFile.txt
            try {
                if (PlayerFile.createNewFile()) {
                    System.out.println("Created PlayerFile");
                }
                try {
                    writeFile = new FileWriter(PlayerFile, true);
                    writeFile.write("p" + (i + 1) + " Name : " + PlayerName + "\n");
                    writeFile.write("p" + (i + 1) + " Home Land : " + HomeLand + "\n");
                    writeFile.write("p" + (i + 1) + " Color : " + FavoriteColor + "\n");
                    writeFile.write("p" + (i + 1) + " Patron God : " + PatronGod + "\n");
                    writeFile.write("p" + (i + 1) + " Days at Sea : 0\n");
                    writeFile.close();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } catch (Exception e) {
            }
        }

        // Adds each line from PlayerFile to PlayerData.
        File PF = new File(PlayerFile.getName());
        try (Scanner scanner = new Scanner(PF)) {
            while (scanner.hasNextLine()) {
                String Line = scanner.nextLine();
                PlayerData.add(Line.trim());
            }
        } catch (Exception e) {
        }

        // Goes to main menu after setting up.
        MainMenu();
    }

    // Sets the variables for the player so they can be written to playerfile.txt
    // then will reuse the variables.
    public static void PlayerRegistration(int CurrentPlayer) {
        System.out.println("Name For Player " + (CurrentPlayer + 1));
        PlayerName = CheckScanString();

        // Checks if it should auto fill the player data.
        boolean AutoFilling = PlayerName.equals("Auto Fill");

        // Auto Fills the player data if needed.
        if (AutoFilling) {
            PlayerName = GetRandomString(RandNames);
            HomeLand = GetRandomString(RandHomeLands);
            FavoriteColor = new Random().nextInt(4) + 1;
            PatronGod = new Random().nextInt(4) + 1;
            AutoFilling = true;
            return;
        }

        // Lets user manualy input player info.
        System.out.println("Hello " + PlayerName + " Please enter your Home Land.");
        HomeLand = CheckScanString();
        System.out.println("What is your age?");
        Age = CheckScan();
        Boolean KeepChecking = true;
        while (KeepChecking) {
            System.out.println("What is your Favorite Color?");
            System.out.println(" 1 : White");
            System.out.println(" 2 : Blue");
            System.out.println(" 3 : Red");
            System.out.println(" 4 : Green");
            FavoriteColor = CheckScan();
            if (FavoriteColor > 0 && FavoriteColor < 5) {
                KeepChecking = false;
            }
        }
        KeepChecking = true;
        while (KeepChecking) {
            System.out.println("What is your patron god?");
            System.out.println(" 1 : Zues");
            System.out.println(" 2 : Hades");
            System.out.println(" 3 : Posidon");
            System.out.println(" 4 : Athena");
            PatronGod = CheckScan();
            if (PatronGod > 0 && PatronGod < 5) {
                KeepChecking = false;
            }
        }
    }

    public static void MainMenu() {
        // Will Repeat so it can keep playing.
        while (true) {
            for (int p = 0; p < PlayerCount; p++) {
                System.out.println("It is " + GetPlayerData(p, 0) + "'s turn who is player " + (p + 1));
                System.out.println("1 : Set Sail");
                System.out.println("2 : Recrute");
                // Sets Sail for current Player.
                if (CheckScan() == 1) {
                    SetSail(p);
                }
                if (CheckScan() == 2) {

                }
            }
        }
    }

    public static void SetSail(int CurrentPlayer) {
        // Sets rowing to be itself plus a random number from 0 to 7.
        try {
            WritePlayerData(CurrentPlayer, 4,
                    String.valueOf(new Random().nextInt(7) + Integer.parseInt(GetPlayerData(CurrentPlayer, 4))));
        } catch (Exception e) {
            // TODO: handle exception
        }
        // Prints all of the current players stats before finishing.
        System.out.println(GetPlayerData(CurrentPlayer, 0));
        System.out.println(GetPlayerData(CurrentPlayer, 1));
        System.out.println(GetPlayerData(CurrentPlayer, 2));
        System.out.println(GetPlayerData(CurrentPlayer, 3));
        System.out.println(GetPlayerData(CurrentPlayer, 4));
    }

    // Gets the line of data for specified player.
    public static String GetPlayerData(int Player, int Row) {
        String PlayerDat = PlayerData.get(5 * Player + Row).split(": ")[1];
        return PlayerDat;
    }

    // Sets the line of data for specified player.
    public static void WritePlayerData(int Player, int Row, String Write) {
        String PlayerDat = PlayerData.get(5 * Player + Row).split(":")[0];
        PlayerData.set(5 * Player + Row, PlayerDat + ": " + Write);
        try {
            Files.write(PlayerFile.toPath(), PlayerData);
        } catch (Exception e) {
        }
    }

    // Gets a random string from an array like the RandNames String[].
    public static String GetRandomString(String[] Names) {
        Random Rand = new Random();

        return Names[Rand.nextInt(Names.length)];
    }

    // Checsk and returns int input from scaner.
    public static int CheckScan() {
        while (!Scan.hasNextInt()) {
            System.out.println("Invalid Entry. Please try again.");
            Scan.next();
        }
        int Val = Scan.nextInt();
        Scan.nextLine();
        return Val;
    }

    // Checks and returns String input from scaner.
    public static String CheckScanString() {
        while (!Scan.hasNextLine()) {
            System.out.println("Invalid Entry. Please try again.");
            Scan.next();
        }
        return Scan.nextLine().replaceAll("[^a-zA-Z]", " ");
    }

    public static void ReadFileFormat(String F) {
        // File Var.
        File LetterFrequenctFile = new File(F);
        // TheStrings list has all of the strings but seperated after each ,
        List<String> TheStrings = new ArrayList<>();
        try (Scanner scanner = new Scanner(LetterFrequenctFile)) {
            // Adds and seperates the text file into TheStrings after each ,
            while (scanner.hasNextLine()) {
                String Line = scanner.nextLine();

                TheStrings.add(Line.split(",")[0].trim());

                TheStrings.add(Line.split(",")[1].trim());

                TheStrings.add(Line.split(",")[2].trim());
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