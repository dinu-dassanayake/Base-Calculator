import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean menu = true;
        int option;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("\nBase Calculator\n1. Enter Calculator\n2. Exit Program");
            option = sc.nextInt();
            String input = "";
            sc.nextLine();
            if (option == 1) {
                menu = false;
                boolean validInput = true;
                System.out.println("Enter the input base for both Integers, Integer 1, operand, then Integer 2");
                input = sc.nextLine().trim();
                System.out.println("Enter the output base between 2 and 10");
                int outputBase = sc.nextInt();
                sc.nextLine();
                while (!(outputBase >= 2 && outputBase <= 10)) {
                    System.out.println("Invalid Output Base, please re-enter.");
                    outputBase = sc.nextInt();
                    sc.nextLine();
                }
                String[] newArray = new String[4];
                int count = 0;
                do {
                    validInput = true;
                    int inputBase = 0;

                    if(count>0) {
                        input = rebuildInput(newArray);
                    }

                    // Checking for invalid amount of spaces
                    while (!validSpaceCount(input)) {
                        System.out.println("Incorrect Number of Spaces, please try again.");
                        input = sc.nextLine().trim();
                        validInput = false;
                    }

                    newArray = sorting(input);
                    // Check for invalid characters
                    while (!validCharacters(newArray)) {
                        System.out.println("Invalid Character, please re-enter whole string.");
                        input = sc.nextLine().trim();
                        newArray = sorting(input);
                        validInput = false;
                    }
                    newArray = sorting(input);

                    // Checking correct operator
                    while(!validOperation(newArray[2])) {
                        System.out.println("Invalid Operator, please re-enter whole string.");
                        input = sc.nextLine();
                        newArray = sorting(input);
                        validInput = false;
                    }

                    // Extracting and validating input base
                    inputBase = Integer.parseInt(newArray[0]);
                    while (!validBase(inputBase)) {
                        System.out.println("Invalid Input Base, please enter it again.");
                        newArray[0] = sc.nextLine();
                        inputBase = Integer.parseInt(newArray[0]);
                        validInput = false;
                    }

                    // Check for negative inputs
                    while (negativeNum(newArray[1])) {
                        System.out.println("Invalid Input 1");
                        newArray[1] = sc.nextLine();
                        validInput = false;
                    }
                    while (negativeNum(newArray[3])) {
                        System.out.println("Invalid Input 2");
                        newArray[3] = sc.nextLine();
                        validInput = false;
                    }

                    // Checking if integers are in valid base
                    while (!validInteger(newArray[1], inputBase)) {
                        System.out.println("Integer 1 is not in the valid base, please re-enter a valid integer.");
                        newArray[1] = sc.nextLine();
                        validInput = false;
                    }
                    while (!validInteger(newArray[3], inputBase)) {
                        System.out.println("Integer 2 is not in the valid base, please re-enter a valid integer.");
                        newArray[3] = sc.nextLine();
                        validInput = false;
                    }

                    // Checking if both integers are positive
                    while(!isPositive(newArray[1]) || !isPositive(newArray[3])) {
                        System.out.println("Integers must be positive, please re-enter the integers.");
                        newArray[1] = sc.nextLine();
                        newArray[3] = sc.nextLine();
                        validInput = false;
                    }

                    count++;
                } while (!validInput);

                // Declaring both integers
                int word = 1;
                int[] int1 = convertArray(newArray, word);
                word = 3;
                int[] int2 = convertArray(newArray, word);

                //Declaring both integers value to base 10
                int[] base10 = base10(int1, int2, Integer.parseInt(newArray[0]));// Inputs converted to base10

                // Base 10 answer after operation
                int[] baseTenOutput = operations(base10, newArray);

                // Final Output
                int finalAns = outputConverter(outputBase, baseTenOutput);
                int finalRemainder = remainderOutputConverter(outputBase, baseTenOutput);
                if (baseTenOutput[1] == 0) {  //Remainder
                    System.out.println("Answer: " + finalAns + "\n");
                } else {
                    System.out.println("Answer: " + finalAns + " R " + finalRemainder + "\n");
                }
                menu = true;
            } else if (option == 2) {
                menu = false;
                System.out.println("bye!");
            } else {
                System.out.println("Invalid Input");
            }
        } while (menu);
    }

    public static String[] sorting(String input) {
        String[] parts = new String[4];
        int spaceCount = 0;
        int index = 0;
        String current = "";

        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i) == ' ') {
                spaceCount++;
                parts[index] = current;
                current = "";
                index++;
                while ((i < input.length() - 1) && (input.charAt(i + 1) == ' ')) {
                    i++;
                }
            } else {
                current += input.charAt(i);
            }
        }
        parts[index] = current;
        if (index != 3) {
            System.out.println("Invalid input. Please retry.");
        }
        return parts;
    }

    public static int[] convertArray(String[] input, int word) {
        int[] spliced = new int[input[word].length()];
        for (int i = 0; i < input[word].length(); i++) {
            spliced[i] = Integer.parseInt(input[word].substring(i, i + 1));
        }
        return spliced;
    }

    public static int[] base10(int[] int1, int[] int2, int base) {
        int convert1 = 0;
        int convert2 = 0;
        int[] b10 = new int[2];
        for (int i = 0; i < int1.length; i++) {
            convert1 += (int) (int1[i] * Math.pow(base, int1.length - i - 1));
        }
        b10[0] = convert1;

        for (int k = 0; k < int2.length; k++) {
            convert2 += (int) (int2[k] * Math.pow(base, int2.length - k - 1));
        }
        b10[1] = convert2;

        return b10;
    }

    public static int[] operations(int[] base10, String[] input) {
        int[] result = new int[2];
        int remainder = 0;
        switch (input[2]) {
            case "+":
                result[0] = base10[0] + base10[1];
                break;
            case "-":
                result[0] = base10[0] - base10[1];
                break;
            case "*":
                result[0] = base10[0] * base10[1];
                break;
            case "/":
                if (base10[0] > base10[1]) {
                    result[0] = base10[0] / base10[1];
                    result[1] = base10[0] % base10[1];
                } else {
                    result[0] = base10[0] / base10[1];
                    result[1] = base10[0] % base10[1];
                }
                break;
            case "%":
                result[0] = base10[0] % base10[1];
                break;
            default:
                System.out.println("something happened");
        }
        return result;
    }

    public static int outputConverter(int outputBase, int[] a) {
        int initial = Math.abs(a[0]);
        int remainder;
        int result = 0;
        int multiplier = 1;

        if (a[0] > 0) {
            while (initial > 0) {
                remainder = initial % outputBase;
                result += remainder * multiplier;
                initial = initial / outputBase;
                multiplier = multiplier * 10;
            }
            return result;
        } else {
            while (initial > 0) {
                remainder = initial % outputBase;
                result += remainder * multiplier;
                initial = initial / outputBase;
                multiplier = multiplier * 10;
            }
            return result - (result * 2);
        }
    }

    public static int remainderOutputConverter(int outputBase, int[] a) {
        int initial = Math.abs(a[1]);
        int remainder;
        int result = 0;
        int multiplier = 1;

        while (initial > 0) {
            remainder = initial % outputBase;
            result += remainder * multiplier;
            initial = initial / outputBase;
            multiplier = multiplier * 10;
        }
        return result;
    }

    public static boolean validSpaceCount(String input) {
        int spaceCount = 0;
        input = input.trim();
        for (int i = 0; i < input.length(); i++) {
            if (input.charAt(i) == ' ') {
                spaceCount++;
            }
        }
        return spaceCount == 3;
    }

    public static boolean validBase(int base) {
        return (base >= 2) && (base <= 10);
    }

    public static boolean validCharacters(String[] newArray) {
        for (int i = 0; i < newArray.length; i++) {
            if (i == 2) {
                i++;
            }
            for (int h = 0; h < newArray[i].length(); h++) {
                if ((!((newArray[i].charAt(h) >= '0') && (newArray[i].charAt(h) <= '9')) && (newArray[i].charAt(h) != '-'))) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean negativeNum(String str) {
        return str.charAt(0) == '-';
    }

    public static boolean validInteger(String str, int inputBase) {
        for (int i = 0; i <= str.length() - 1; i++) {
            if (Integer.parseInt(str.substring(i, i + 1)) >= inputBase) {
                return false;
            }
        }
        return true;
    }

    public static boolean isPositive(String str) {
        return Integer.parseInt(str) >= 0;
    }

    public static boolean validOperation(String str) {
        return str.equals("+") || str.equals("-") || str.equals("*") || str.equals("/") || str.equals("%");
    }
    public static String rebuildInput(String[] newArray){
        String input = "";
        for(int i = 0; i<newArray.length; i++){
            if(i==3){
                input+=newArray[i];
            } else{
                input+=newArray[i] + " ";
            }
        }
        return input;
    }
}