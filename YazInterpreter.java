// Jong-Hyun Lee
// 06.15.2023
// CSE 142 
// Assessment #6
//
// Contains the equations to execute specific  
// commands (like convert, range, and repeat), 
// then prints the output either to the console
// or to a txt file as the user wishes.  
import java.util.*;
import java.io.*;
public class YazInterpreter {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner console = new Scanner(System.in);
        intro();
        boolean run = true;
        while (run) {
            System.out.print("(I)nterpret YazLang program, (V)iew output, (Q)uit? ");
            String answer = console.next();
            if (answer.equalsIgnoreCase("i")) {
               interpret(console);
            } else if (answer.equalsIgnoreCase("v")) {
               view(console);
            } else if (answer.equalsIgnoreCase("q")) {
               run = false;
            } 
        }
    }

    // Prints the instructions for the YazInterpreter program.
    public static void intro() {
        System.out.println("Welcome to YazInterpreter!");
        System.out.println("You may interpret a YazLang program and output");
        System.out.println("the results to a file or view a previously");
        System.out.println("interpreted YazLang program.\n");
    }

    // Executes the view option. 
    // Parameter:
    //      Scanner console - the Scanner object to get user input
    public static void view(Scanner console) throws FileNotFoundException {
        Scanner input = inputFile(console);
        System.out.println();
        while (input.hasNextLine()) {
            String line = input.nextLine();
            System.out.println(line);        
        }
        System.out.println();
    }    

    // Executes the interpret option. 
    // Parameter:
    //      Scanner console - the Scanner object to get user input
    public static void interpret(Scanner console) throws FileNotFoundException {
        Scanner input = inputFile(console);
        PrintStream output = outputFile(console);
        while (input.hasNextLine()) {
            String command = input.nextLine();  
            if(command.startsWith("Convert")) {
               convert(command, output);
            } else if (command.startsWith("Range")) {
               range(command, output);
            } else if (command.startsWith("Repeat")) {
               repeat(command, output);
            } 
        }
        System.out.println("YazLang interpreted and output to a file!");
    }
    
    // Executes the convert command. 
    // Parameter:
    //      String range - the variable being executed 
    //      PrintStream output - the PrintStream object 
    //                           to print the results into an output file
    public static void convert(String range, PrintStream output) {
        Scanner tokens = new Scanner(range);
        String command = tokens.next();
        int temp = tokens.nextInt();
        String unit = tokens.next();
        double result = 0;
        if (unit.equals("C")) {
            result = 1.8 * temp + 32;
            output.println((int)result + "F");
        } else if (unit.equals("F")) {
            result = (temp - 32) / 1.8;
            output.println((int)result + "C"); 
        }
    }

    // Executes the range command. 
    // Parameter:
    //      String range - the variable being executed 
    //      PrintStream output - the PrintStream object 
    //                           to print the results into an output file
    public static void range(String range, PrintStream output) {
        Scanner tokens = new Scanner(range);
        String command = tokens.next();
        int start = tokens.nextInt();
        int max = tokens.nextInt();
        int addends = tokens.nextInt();
        while (start <= max) {
            System.out.print(start + " ");
            start += addends;
        }
    }

    // Executes the repeat command. 
    // Parameter:
    //      String repeat - the variable being executed 
    //      PrintStream output - the PrintStream object 
    //                           to print the results into an output file
    public static void repeat(String repeat, PrintStream output) {
        Scanner tokens = new Scanner(repeat);
        String command = tokens.next();
        String result = "";
        while (tokens.hasNext()) {
            String letter = tokens.next();
            int num = tokens.nextInt();
            for (int i = 0; i < num; i++) {
                result += letter;
            }
        }
        result = result.replace("_"," ");
        output.println(result);
    }

    // Prompts the user for an input file name and returns the input scanner file.
    // Parameter:
    //      Scanner console - the Scanner object to get user input
    public static Scanner inputFile(Scanner console) throws FileNotFoundException {
        System.out.print("Input file name: ");
        String inputFile = console.next();
        File file = new File(inputFile);
        while (!file.exists()) {
            System.out.print("File not found. Try again: ");
            inputFile = console.next();
            file = new File(inputFile);
        }
        Scanner input = new Scanner(new File(inputFile));
        return input; 
    }

    // Prompts the user for an output file name and returns the output scanner file. 
    // Parameter:
    //      Scanner console - the Scanner object to get user input
    public static PrintStream outputFile(Scanner console) throws FileNotFoundException {
        System.out.print("Output file name: ");
        String outputFile = console.next();
        File file = new File(outputFile);
        PrintStream output = new PrintStream(new File(outputFile));
        return output; 
    }
}
