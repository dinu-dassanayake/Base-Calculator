import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean menu = true;
        int option;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("1. Enter Calculator\n2. Exit Program");
            option = sc.nextInt();
            String input = "";
            sc.nextLine();
            if (option == 1) {
                menu = false;
                System.out.println("Enter the input base for both Integers, Integer 1, operand, then Integer 2");
                input = sc.nextLine();

                /*
                //Checking for invalid amount of spaces
                int spaceCount;
                do {
                    spaceCount = 0;
                    for(int i = 0; i<input.length(); i++){
                        if(input.charAt(i)==' '){
                            spaceCount++;
                        }
                    }
                    if(spaceCount!=3){
                        System.out.println("Incorrect Number of Spaces, please try again.");
                        input = sc.nextLine();
                    }
                }while(spaceCount==3);
                 */

                System.out.println("Enter the output base between 2 and 10");
                int outputBase = sc.nextInt();
                sc.nextLine();
                String[] newArray = sorting(input);
                System.out.println(Arrays.toString(newArray));//remove

                //Check for invalid characters
                for (int i = 0; i < newArray.length; i++) {
                    if (i == 2) {
                        i++;
                    }
                    for (int h = 0; h < newArray[i].length(); h++) {
                        if ((!((newArray[i].charAt(h) >= '0') && (newArray[i].charAt(h) <= '9'))&&(newArray[i].charAt(h)!='-'))) {
                            System.out.println("Invalid Character, please re-enter whole string.");
                            input = sc.nextLine();
                            newArray = sorting(input);
                        }
                    }
                }
                int inputBase = Integer.parseInt(newArray[0]);

                //Check for Negative Inputs
                boolean negative = false;
                do {
                    if (newArray[1].charAt(0) == '-') {
                        negative = true;
                        System.out.println("Invalid Input 1");
                        newArray[1] = sc.next();
                    } else {
                        negative = false;
                    }
                    if (newArray[3].charAt(0) == '-') {
                        negative = true;
                        System.out.println("Invalid Input 2");
                        newArray[3] = sc.next();
                    } else {
                        negative = false;
                    }
                } while (negative);

                // Checking if input and output bases are between 2 and 10 (inclusive)
                while (((inputBase < 2) || (inputBase > 10))) {
                    System.out.println("Invalid Input Base, please enter it again.");
                    inputBase = sc.nextInt();
                    newArray[0] = String.valueOf(inputBase);
                    inputBase = Integer.parseInt(newArray[0]);
                }
                while (((outputBase < 2) || (outputBase > 10))) {
                    System.out.println("Invalid Output Base, please enter it again.");
                    outputBase = sc.nextInt();
                }
                System.out.println(Arrays.toString(newArray));

                // Checking if valid base
                boolean repeat = true;
                do {
                    int q = Integer.parseInt(newArray[1].substring(0));
                    String intString = String.valueOf(q);//??
                    for (int i = 0; i <= newArray[1].length() - 1; i++) {
                        if (Integer.parseInt(intString.substring(i, i + 1)) >= inputBase) {
                            System.out.println("Integer 1 is not in the valid base, please re-enter a valid integer.");
                            newArray[1] = sc.next();
                            break;
                        } else {
                            repeat = false;
                        }
                    }
                }while(repeat);
                repeat = true;
                do {
                    int q = Integer.parseInt(newArray[3].substring(0));
                    String intString = String.valueOf(q);
                    for (int k = 0; k <= newArray[3].length() - 1; k++) {
                        if (Integer.parseInt(intString.substring(k, k + 1)) >= inputBase) {
                            System.out.println("Integer 2 is not in the valid base, please re-enter a valid integer.");
                            newArray[3] = sc.next();
                            break;
                        } else {
                            repeat = false;
                        }
                    }
                }while(repeat);

                // Checking if both ints are positive
                int temp = Integer.parseInt(newArray[1].substring(0));
                while (temp < 0) { //CAN THEY BE ZERO
                    System.out.println("First Int is Invalid, please re-enter a valid integer.");
                    newArray[1] = sc.next();
                    temp = Integer.parseInt(newArray[1]);
                }
                temp = Integer.parseInt(newArray[3].substring(0));
                while (temp < 0) {
                    System.out.println("Second Int is Invalid, please re-enter a valid integer.");
                    newArray[3] = sc.next();
                    temp = Integer.parseInt(newArray[3]);
                }


                // Checking Correct Character
                while (!((newArray[2].equals("+")) || (newArray[2].equals("-")) || (newArray[2].equals("*")) || (newArray[2].equals("/")) || (newArray[2].equals("%")))) {
                    System.out.println("Invalid Character, please re-enter whole string.");
                    input = sc.nextLine();
                    newArray = sorting(input);
                }

                // Declaring both integers
                int word = 1;
                int[] int1 = convertArray(newArray, word);
                word = 3;
                int[] int2 = convertArray(newArray, word);

                //Declaring both integers value to base 10
                int[] base10 = base10(int1, int2, Integer.parseInt(newArray[0]));// Inputs converted to base10
                System.out.println(Arrays.toString(base10));

                // Base 10 answer after operation
                int[] baseTenOutput = operations(base10, newArray);

                // Final Output
                int finalAns = outputConverter(outputBase, baseTenOutput);
                int finalRemainder = remainderOutputConverter(outputBase, baseTenOutput);
                if (baseTenOutput[1] == 0) {  //Remainder
                    System.out.println("Answer: " + finalAns);
                } else {
                    System.out.println("Answer: " + finalAns + " R " + finalRemainder);
                }
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
}