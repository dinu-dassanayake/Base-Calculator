import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean menu = true;
        int option = 0;
        Scanner sc = new Scanner(System.in);
        do {
            System.out.println("1. Enter Calculator\n2. Exit Program");
            option = sc.nextInt();
            String input = "";
            sc.nextLine();
            if (option == 1) {
                menu = false;
                System.out.println("Enter the base for both integers, integer, operand, and integer");
                input = sc.nextLine();
                System.out.println("Enter the output base between 2 and 10");
                int outputBase = sc.nextInt();
                sc.nextLine();
                String[] newArray = sorting(input);
                int inputBase = Integer.parseInt(newArray[0]);

                // Checking if both ints are positive
                int temp = Integer.parseInt(newArray[1].substring(1));
                temp = temp-temp*2;
                while(temp<0){ //CAN THEY BE ZERO
                    System.out.println("First Int is Invalid, please re-enter a valid integer");
                    newArray[1] = sc.next();
                    temp = Integer.parseInt(newArray[1]);
                }
                temp = Integer.parseInt(newArray[3].substring(1));
                temp = temp-temp*2;
                while(temp<0){
                    System.out.println("Second Int is Invalid, please re-enter a valid integer");
                    newArray[3] = sc.next();
                    temp = Integer.parseInt(newArray[3]);
                }
                
                // Checking Correct Character
                while (!((newArray[2].equals("+"))||(newArray[2].equals("-"))||(newArray[2].equals("*"))||(newArray[2].equals("/"))||(newArray[2].equals("%")))){
                    System.out.println("Invalid Character, please re-enter whole string.");
                    input = sc.nextLine();
                    newArray = sorting(input);
                }

                int word = 1;
                // Checking if input and output bases are between 2 and 10 (inclusive)
                while (!((inputBase >= 2) && (inputBase <= 10))) {
                    System.out.println("Invalid Input Base, please enter it again.");
                    inputBase = sc.nextInt();
                    newArray[0] = String.valueOf(inputBase);
                }
                while (!((outputBase >= 2) && (outputBase <= 10))) {
                    System.out.println("Invalid Output Base, please enter it again.");
                    outputBase = sc.nextInt();
                }
                System.out.println(Arrays.toString(newArray));

                // Declaring both integers
                int[] int1 = convertArray(newArray, word);
                word = 3;
                int[] int2 = convertArray(newArray, word);
                System.out.println(Arrays.toString(int1) + "\n" + Arrays.toString(int2));

                //Declaring both integers value to base 10
                int[] base10 = base10(int1, int2, Integer.parseInt(newArray[0]));// Inputs converted to base10
                System.out.println(Arrays.toString(base10));

                // Base 10 answer after operation
                int[] baseTenOutput = operations(base10, newArray);
                System.out.println(Arrays.toString(baseTenOutput));

                // Final Output
                System.out.println("Answer: " + (outputConverter(outputBase, baseTenOutput)));

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
            System.out.println("Invalid input. Please retry."); //error
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
    public static int[] base10(int[] int1, int[] int2, int base){
        int convert1 = 0;
        int convert2 = 0;
        int[] b10 = new int[2];
        for(int i = 0; i<int1.length; i++){
            convert1 += (int) (int1[i]*Math.pow(base, int1.length-i-1));
        }
        b10[0] = convert1;

        for(int k = 0; k<int2.length; k++){
            convert2 += (int) (int2[k]*Math.pow(base, int2.length-k-1));
        }
        b10[1] = convert2;

        return b10;
    }
    public static int[] operations(int[] base10, String[] input){
        int[] result = new int[2];
        int remainder = 0;
        switch(input[2]) {
            case "+":
                result[0] = base10[0]+base10[1];
                break;
            case "-":
                result[0] = base10[0]-base10[1];
                break;
            case "*":
                result[0] = base10[0]*base10[1];
                break;
            case "/":
                if(base10[0]>base10[1]){
                    result[0] = base10[0]/base10[1];
                    result[1] = base10[0]%base10[1];
                } else {
                    System.out.println("please retry");
                }
                break;
            case "%":
                result[0] = base10[0]%base10[1];
                break;
            default:
                System.out.println("something happened");
        }
        return result;
    }

    public static int outputConverter(int outputBase, int[] a){
        int initial = 201;
        int remainder = 0;
        int result = 0;
        int multiplier = 1;

        while (initial > 0) {
            remainder = initial % outputBase;
            result += remainder * multiplier;
            initial = initial / outputBase;
            multiplier = multiplier*10;
        }
        return result;
    }
}