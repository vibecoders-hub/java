/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.parta;

/**
 *
 * @author HP
 */
import java.util.Scanner;

public class p4 {

    private static String toggleCharacters(String Str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < Str.length(); i++) {
            char ch = Str.charAt(i);
            if (Character.isUpperCase(ch)) {
                result.append(Character.toLowerCase(ch));
            } else if (Character.isLowerCase(ch)) {
                result.append(Character.toUpperCase(ch));
            }else{
                result.append(ch);
            }
        }
        return result.toString();

    }

    private static String SwapCharacters(String str) {
        StringBuilder result = new StringBuilder();
        String[] words = str.split("\\s+");
        for (String word : words) {
            if (word.length() % 2 == 0) {
                for (int i = 0; i < word.length() - 1; i += 2) {
                    char temp = word.charAt(i);
                    result.append(word.charAt(i+1));
                    result.append(temp);
                }
                result.append(" ");
            }
        }
        return result.toString().trim();
    }

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter a String: ");
        String data = scanner.nextLine();
        String toggle = toggleCharacters(data);
        System.out.println("Toggled String: " + toggle);
        String Swap = SwapCharacters(data);
        System.out.println("Swapped String: " + Swap);
    }
}
