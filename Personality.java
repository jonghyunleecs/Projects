// Jong-Hyun Lee
// 06.30.2023
//
// Sorts through the response of the input file 
// and calculates the percentage of responses 
// that are B to determine the personality of 
// each person and prints the output in a txt file. 
import java.util.*;
import java.io.*;
public class Personality {
    public static final int NUM_DIMENSIONS = 4;
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        intro();
        Scanner input = inputFile(console);
        PrintStream output = outputFile(console);
        while (input.hasNextLine()) {
            String name = input.nextLine();
            String responses = input.nextLine();
            int[] countA = getCounts(responses, 'A');
            int[] countB = getCounts(responses, 'B');
            int[] percentage = getPercentage(countA, countB); 
            String personality = getPersonality(percentage);
            output.println(name + ": " + Arrays.toString(percentage) + " = " + personality);
        }
    }

    // Prints the instructions for personality program.
    public static void intro() {
        System.out.println("This program processes a file of answers to the");
        System.out.println("Keirsey Temperament Sorter. It converts the");
        System.out.println("various A and B answers for each person into");
        System.out.println("a sequence of B-percentages and then into a");
        System.out.println("four-letter personality type.\n");
    } 

    // Sorts the response and returns the number of response for each dimension as an array.
    // Parameters:
    //      String responses - the responses to the questionnaire 
    //      char letter - the type of response being sorted (for example: A or B)
    public static int[] getCounts (String responses, char letter) {
        int[] counts = new int[NUM_DIMENSIONS];
        for (int i = 0; i < responses.length(); i++) {
            char response = responses.charAt(i);
            if (Character.toUpperCase(response) == letter) {
                if (i % 7 == 0) {
                    counts[0]++;
                } else if (i % 7 == 1 || i % 7 == 2) {
                    counts[1]++;
                } else if (i % 7 == 3 || i % 7 == 4) {
                    counts[2]++;
                } else if (i % 7 == 5 || i % 7 == 6) {
                    counts[3]++;
                }
            }
        }
        return counts;
    }

    // Calculates the percentage of responses that were B 
    // and returns the rounded percent as an array. 
    // Parameters:
    //      int[] countA - the responses for A
    //      int[] countB - the responses for B
    public static int[] getPercentage (int countA[], int countB[]) {
        int[] roundedPercentage = new int[NUM_DIMENSIONS];
        for (int i = 0; i < NUM_DIMENSIONS; i++) {
            double rawPercentage = (double)countB[i] / (countA[i] + countB[i]) * 100;
            roundedPercentage[i] = (int)Math.round(rawPercentage);
        }
        return roundedPercentage;
    }

    // Determines the personality by the percentage and returns the result as a string.
    // Parameter:
    //      int[] percentage - the percentage of responses that were B.
    public static String getPersonality(int[] percentage) {
        String[] aSide = {"E","S","T","J"};
        String[] bSide = {"I","N","F","P"};
        String personality = "";
        for (int i = 0; i < NUM_DIMENSIONS; i++) {
            if (percentage[i] > 50) {
                personality += aSide[i];
            } else if (percentage[i] == 0 ) {
                personality += "X";
            } else if (percentage[i] < 50) {
                personality += bSide[i];
            } 
        }
        return personality; 
    } 
    
    // Prompts the user for an input file name and returns the input scanner file.
    // Parameter:
    //      Scanner console - the Scanner object to get user input
    public static Scanner inputFile(Scanner console) throws FileNotFoundException {
        System.out.print("input file name: ");
        String inputFile = console.next();
        File file = new File(inputFile);
        while (!file.exists()) {
            System.out.print("File not found. Try again: ");
            inputFile = console.next();
            file = new File(inputFile);
        }
        Scanner input = new Scanner(file);
        return input; 
    }

    // Prompts the user for an output file name and returns the output scanner file. 
    // Parameter:
    //      Scanner console - the Scanner object to get user input
    public static PrintStream outputFile(Scanner console) throws FileNotFoundException {
        System.out.print("output file name: ");
        String outputFile = console.next();
        File file = new File(outputFile);
        PrintStream output = new PrintStream(file);
        return output; 
    }
}
