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
            sc.nextLine();
            if(option==1){
                menu = false;
                System.out.println("Enter the base for both integers, integer, operand, and integer");
                String input = sc.nextLine();
                System.out.println(Arrays.toString(sorting(input))); //print remove to String
            } else if(option==2){
                menu = false;
            } else {
                System.out.println("Invalid Input");
            }
        } while (menu);
    }
    public static String[] sorting(String input){
        String[] parts = new String[4];
        int partIndex = 0;
        String current = "";

        for (int i = 0; i < input.length(); i++) {

            if (input.charAt(i)== ' ') {
                parts[partIndex] = current;
                current = "";
                partIndex++;

                while ((i < input.length() - 1)&& (input.charAt(i + 1) == ' ')) {
                    i++;
                }
            } else {
                current += input.charAt(i);
            }
        }
        parts[partIndex] = current;

        if (partIndex != 3) {
            System.out.println("Invalid input. Please retry."); //error
        }
        return parts;
    }
    public static void convert(){

    }
}